# gRPC
gRPC POC using Java

* What is gRPC? *

Google Remote Procedure Call. Open source framework by Google for remote procedure calls based on Protocol Buffers. 

Request, response messages and the api definition are created in a single .proto file, and shared across to the client.

RPC makes it look like as if you are directly calling a function on the server, whereas the function gets called over the network.

*Advantages of using protocol buffers*

1. Protocol buffers are language agnostic. Hence, the client and server can be on different tech stack, but can easily integrate with each other using protocol buffers.
2. Parsing JSON is CPU intensive as it is human readable format, whereas protocol buffers (binay) are close to machine language.
3. gRPC is HTTP/2 based, hence responses arrive much faster than traditional HTTP/2.
4. Being based on HTTP/2, also makes it secure.
5. HTTP/2 also makes streaming possible with gRPC request/response.
6. gRPC support scalability as : servers are asynchronous by default, clients can choose to be asynchronous, clients can perform client-side load balancing.
7. 25 times faster than REST. Reasons: TCP connections are reused, headers are compressed, binary payload by default.

*Types of gRPC APIs* :

1. Unary: traditional request-response
2. Server Streaming: Client sends request, and server sends back multiple responses.
3. Client Streaming: Client sends multiple requests, and server sends back single response on receiving final request fragment.
4. Bi-Directional Streaming: Both client and server send and receive streams of data.
--------------------------------------------------------------------------------------------------------------------
##POC

grpc-java setup: https://github.com/grpc/grpc-java

Important files to checkout in this POC:
1. greet.proto
2. GreetServiceImpl.java
3. GrpcServer.java
4. GrpcClient.java

To start the POC, run below:

```
mvn clean install   //this will generate grpc files from the given proto file for messages and services.
Start GrpcServer.java
Start GrpcClient.java // RPC onto the server
```
When you build the proto files, the request/response and the services java classes get generated.
We can then write detailed service implementation extending these classes (see GreetingServiceImpl.java).
Then we can override the methods of base impl to give the code.



 





