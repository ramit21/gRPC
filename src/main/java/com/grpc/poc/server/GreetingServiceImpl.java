package com.grpc.poc.server;

import greet.GreetRequest;
import greet.GreetResponse;
import greet.GreetServiceGrpc.GreetServiceImplBase;
import greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetingServiceImpl extends GreetServiceImplBase{

	@Override
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
}
