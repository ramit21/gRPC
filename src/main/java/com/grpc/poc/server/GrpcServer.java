package com.grpc.poc.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("GRPC server started");
		Server server = ServerBuilder.forPort(50051)
					.addService(new GreetingServiceImpl())	//Add your services here to make them available to client
					.build();
		server.start();
		Runtime.getRuntime().addShutdownHook(new Thread( () -> {
			server.shutdown();
		}));
		server.awaitTermination();
	}
}
