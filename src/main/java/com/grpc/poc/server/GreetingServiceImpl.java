package com.grpc.poc.server;

import greet.GreetRequest;
import greet.GreetResponse;
import greet.GreetServiceGrpc.GreetServiceImplBase;
import greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetingServiceImpl extends GreetServiceImplBase{

	@Override
	//Unary
	public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
		// Fetch values from request object
		Greeting greeting = request.getGreeting();
		String firstName = greeting.getFirstName();
		String result = "Hello " + firstName;
		// Set the response for sending back to the client
		GreetResponse response = GreetResponse.newBuilder().setResult(result).build();
		responseObserver.onNext(response);
		// complete the response
		responseObserver.onCompleted();
	}
	
	@Override
	//Server streaming
	public void greetManytimes(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
		Greeting greeting = request.getGreeting();
		String firstName = greeting.getFirstName();
		
		try {
			for(int i=1; i<= 10; i++) {
				String result = "Hello " + firstName + " " +i; 
				GreetResponse response = GreetResponse.newBuilder().setResult(result).build();
				responseObserver.onNext(response);
				Thread.sleep(1000L);
			}
		}catch(InterruptedException e) {
			System.err.println(e.getMessage());
		}finally {
			responseObserver.onCompleted();
		}
	}
	
	@Override
	//client streaming, note that return type is not void
	public StreamObserver<GreetRequest> longGreet(StreamObserver<GreetResponse> responseObserver) {
		StreamObserver<GreetRequest> requestObserver = new StreamObserver<GreetRequest>() {
			
			String result = "Hello ";
			
			@Override
			public void onNext(GreetRequest request) {
				result += request.getGreeting().getFirstName() +" ";
			}

			@Override
			public void onError(Throwable t) {
			}

			@Override
			public void onCompleted() {
				//client is done
				responseObserver.onNext(GreetResponse.newBuilder().setResult(result).build());
				responseObserver.onCompleted();
			}
		};
		return requestObserver;
	}
	
	@Override
	//Bi-directional streaming : note that return type is not void
	public StreamObserver<GreetRequest> greetEveryone(StreamObserver<GreetResponse> responseObserver) {
		StreamObserver<GreetRequest> requestObserver = new StreamObserver<GreetRequest>() {

			@Override
			public void onNext(GreetRequest request) {
				String result = "Hello " + request.getGreeting().getFirstName();
				// Set the response for sending back to the client
				GreetResponse response = GreetResponse.newBuilder().setResult(result).build();
				responseObserver.onNext(response);
			}

			@Override
			public void onError(Throwable t) {
				
			}

			@Override
			public void onCompleted() {
				responseObserver.onCompleted();
			}
		};
		return requestObserver;
	}
}
