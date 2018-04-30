Airport Baggage Routing Service.

Implementation:

There are 2 different services,
1.RoutingService - This service constructs shortest path map for given Graph.
Implemented class - BaggageRoutingServiceImpl.java (This class is a thread safe class).

2.BaggageRouteServiceEndPoint - This service parses the given string and invokes RoutingService for constructs and search the shortest path.
Implemented class - BaggageRouteServiceEndPointImpl.java (This class is not a thread safe class).

Instructions for Execution:
1. In the root folder, mvn clean install
2. cd target
3. java -cp baggageroute-0.0.1-jar-with-dependencies.jar com.denvar.airport.baggageroute.runner.CommandLineRunner
4. Now, inputs can be sent to the java process, * is used to move from one segment of input to another segment like first set of input, will be list of vertices and edge and give * then give second set of input which is list of flight data. 

Example,

Please refer attached image input_params.png for more information on passing input to the process.
