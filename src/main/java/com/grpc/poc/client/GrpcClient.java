package com.grpc.poc.client;

import greet.GreetRequest;
import greet.GreetResponse;
import greet.GreetServiceGrpc;
import greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

	public static void main(String[] args) {
		System.out.println("GRPC client start");
		ManagedChannel channel = ManagedChannelBuilder
										.forAddress("localhost", 50051)
										.usePlaintext() //to bypass HTTPS
										.build();
		
		GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
		
		makeUnaryCall(greetClient);
		
		//shutdown channel
		System.out.println("GRPC client end");
		channel.isShutdown();
	}

	private static void makeUnaryCall(GreetServiceGrpc.GreetServiceBlockingStub greetClient) {
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
	}

}
