package com.grpc.poc.client;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import greet.GreetRequest;
import greet.GreetResponse;
import greet.GreetServiceGrpc;
import greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcClient {

	public static void main(String[] args) {
		System.out.println("GRPC client start");
		ManagedChannel channel = ManagedChannelBuilder
										.forAddress("localhost", 50051)
										.usePlaintext() //to bypass HTTPS, don't do this in prod
										.build();
		
		makeUnaryCall(channel);
		makeServerStreamingCall(channel);
		makeBiDirectionalCall(channel);
		
		//shutdown channel
		channel.isShutdown();
		System.out.println("GRPC client end");
	}

	private static void makeUnaryCall(ManagedChannel channel) {
		System.out.println("Unary start >>>>>>");
		GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
		Greeting greeting = Greeting.newBuilder()
				.setFirstName("Ramit")
				.setLastName("Sharma")
				.build();
		
		GreetRequest greetRequest = GreetRequest.newBuilder()
				.setGreeting(greeting)
				.build();
		
		//call the  backend api
		GreetResponse greetResponse = greetClient.greet(greetRequest);
		
		System.out.println(greetResponse.getResult());
		System.out.println("Unary completed <<<<<<");
	}
	
	private static void makeServerStreamingCall(ManagedChannel channel) {
		System.out.println("Server streaming started >>>>>>");
		GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
		Greeting greeting = Greeting.newBuilder()
				.setFirstName("Ramit")
				.setLastName("Sharma")
				.build();
		
		GreetRequest greetRequest = GreetRequest.newBuilder()
				.setGreeting(greeting)
				.build();
		
		//stream the responses in a blocking manner
		greetClient.greetManytimes(greetRequest)
				.forEachRemaining(greetResponse -> {
					System.out.println(greetResponse.getResult());
				});
		System.out.println("Server streaming completed <<<<<<");
	}

	private static void makeBiDirectionalCall(ManagedChannel channel) {
		System.out.println("Bidirectional streaming started >>>>>>");
		GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);
		CountDownLatch latch = new CountDownLatch(1);
		StreamObserver<GreetRequest> requestObserver = asyncClient.greetEveryone(new StreamObserver<GreetResponse>() {

			@Override
			public void onNext(GreetResponse response) {
				//handle multiple responses from server
				System.out.println("Reponse from server: "+ response.getResult());				
			}

			@Override
			public void onError(Throwable t) {
				latch.countDown();
			}

			@Override
			public void onCompleted() {
				System.out.println("Server is done sending data");
				latch.countDown();
			}
		});
		
		//send multiple requests to server
		Arrays.asList("Ramit", "Sharma", "Kohli", "Rohit", "KL").forEach(name -> {
			requestObserver.onNext(GreetRequest.newBuilder().setGreeting(
										Greeting.newBuilder().setFirstName(name))
									.build());
		});
		
		requestObserver.onCompleted();
		
		try {
			latch.await(3, TimeUnit.SECONDS);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Bidirectional streaming completed <<<<<<");
	}

}
