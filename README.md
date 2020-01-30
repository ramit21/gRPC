# gRPC
gRPC POC using Java

**What is gRPC?**

Google's gRPC Remote Procedure Call. Open source framework by Google for remote procedure calls based on Protocol Buffers. 

Request, response messages and the api definition are created in a single .proto file, and shared across to the client.

RPC makes it look like as if you are directly calling a function on the server, whereas the function gets called over the network.

**Advantages of using gRPC**

1. Protocol buffers are language agnostic. Hence, the client and server can be on different tech stack, but can easily integrate with each other using protocol buffers.
2. Parsing JSON is CPU intensive as it is human readable format, whereas protocol buffers (serialized binary data) are close to machine language.
3. gRPC is HTTP/2 based, hence responses arrive much faster than traditional HTTP/2.
4. Being based on HTTP/2, also makes it secure.
5. HTTP/2 also makes streaming possible with gRPC request/response.
6. gRPC support scalability as : servers are asynchronous by default, clients can choose to be asynchronous, clients can perform client-side load balancing.
7. 25 times faster than REST. Reasons: TCP connections are reused, headers are compressed, binary payload by default.

**Types of gRPC APIs**:

1. <i>Unary</i>: traditional request-response
2. <i>Server Streaming</i>: Client sends request, and server sends back multiple responses.
3. <i>Client Streaming</i>: Client sends multiple requests, and server sends back single response on receiving final request fragment.
4. <i>Bi-Directional Streaming</i>: Both client and server send and receive data asynchronously. No. of requests and the no. of responses may not match. Good for chat applications.

**Running this POC**:

grpc-java setup: https://github.com/grpc/grpc-java

To start the POC, run below:

```
mvn clean install   //maven will generate java files from the given proto messages and services.
Start GrpcServer.java
Start GrpcClient.java // RPC onto the server
```

Important files in this POC:
1. <i>greet.proto</i>: Schema for request/response and services.
2. <i>GreetServiceImpl.java</i>: API definition
3. <i>GrpcServer.java</i>: Server
4. <i>GrpcClient.java</i>: Client

When you build the proto files, the request/response and the services java classes get generated.
We can then write detailed service implementation extending these classes (see GreetingServiceImpl.java), and override the methods of base impl to write our api code.

Deadlines are used at client side to cancel the call if response is not received in given time.

Unlike so many error codes for HTTP which are confusing and not properly used by the developers, the error codes in gRPC are limited (link below for error codes).

You can access the headers at the client side and the server side using client and server interceptors respectively. (link below)

**Evans CLI and Reflections**

If server is under development, and client has not been provided with the final proto file, how can the client still access what all services are being developed on server? For this, use the Evans cli on client side, and enable proto reflection service on the server side.

**Conclusion**:

Low network latency and the fact that Google protocol buffers can be used across different programming platforms make gRPC a very good alternative for building microservices. While REST is verb oriented, gRPC advocates API based approach. 

gRPC is really good for microservice to microservice communication. But REST is still the de-facto option for web applications. Even though a plugin has now been introduced for creating gRPC client on the browser (link below), the plaintext JSON response of REST Apis still win the race as it is javascript friendly.



**References and further reads**:

1. https://www.udemy.com/course/grpc-java/
2. https://dzone.com/articles/learning-about-the-headers-used-for-grpc-over-http
3. https://grpc.io/blog/loadbalancing/
4. https://grpc.io/blog/state-of-grpc-web/
5. https://grpc.io/blog/a_short_introduction_to_channelz/
6. https://grpc.io/blog/deadlines/
7. https://grpc.io/docs/guides/error/
8. https://github.com/grpc/grpc-java/tree/master/examples/src/main/java/io/grpc/examples/header
9. https://www.linkedin.com/pulse/why-rest-when-you-can-grpc-ramit-sharma/
