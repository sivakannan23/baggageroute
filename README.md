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
4. Now, inputs can be sent to the java process, * is used to move from one stage of input to another stage like first set of input, will be list of vertices and edge and give * then second set of input will be, list of flight data like this. 

Example,

Concourse_A_Ticketing A5 5
A5 BaggageClaim 5
A5 A10 4
A5 A1 6
A1 A2 1
A2 A3 1
A3 A4 1
A10 A9 1
A9 A8 1
A8 A7 1
A7 A6 1
*
UA10 A1 MIA 08:00
UA11 A1 LAX 09:00
UA12 A1 JFK 09:45
UA13 A2 JFK 08:30
UA14 A2 JFK 09:45
UA15 A2 JFK 10:00
UA16 A3 JFK 09:00
UA17 A4 MHT 09:15
UA18 A5 LAX 10:15
*
0001 Concourse_A_Ticketing UA12
0001 Path [totalWeight=11, vertices=Concourse_A_Ticketing A5 A1]
0002 A5 UA17
0002 Path [totalWeight=9, vertices=A5 A1 A2 A3 A4]
0005 A7 ARRIVAL
0005 Path [totalWeight=12, vertices=A7 A8 A9 A10 A5 BaggageClaim]
0006 A7 BaggageClaim
0006 Path [totalWeight=12, vertices=A7 A8 A9 A10 A5 BaggageClaim]
0004 A8 UA18
0004 Path [totalWeight=6, vertices=A8 A9 A10 A5]
*
Process Ends
sivakkannansAir:target $ 






