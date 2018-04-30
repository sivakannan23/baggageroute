package com.denvar.airport.baggageroute.endpoint.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.denvar.airport.baggageroute.datamodel.Edge;
import com.denvar.airport.baggageroute.datamodel.FlightData;
import com.denvar.airport.baggageroute.datamodel.Graph;
import com.denvar.airport.baggageroute.datamodel.Path;
import com.denvar.airport.baggageroute.datamodel.Vertice;
import com.denvar.airport.baggageroute.endpoint.BaggageRouteServiceEndPoint;
import com.denvar.airport.baggageroute.exception.InCorrectDataException;
import com.denvar.airport.baggageroute.exception.InCorrectDataFormatException;
import com.denvar.airport.baggageroute.service.RoutingService;

/**
 * @author sivakkannanmuthukumar
 * 
 *         This class is not Thread safe Methods in this class are used to
 *         transform and process inbound string data for vertices, edges and
 *         search shortest path for destination.
 * 
 */
public class BaggageRouteServiceEndPointImpl implements BaggageRouteServiceEndPoint {

	private final static Logger LOGGER = Logger.getLogger(BaggageRouteServiceEndPointImpl.class);
	private RoutingService routingService;

	private Map<String, Vertice> verticesMap = new HashMap<>();
	private List<Edge> edges = new ArrayList<>();
	private Map<String, FlightData> flightDataMap = new HashMap<>();
	private Map<Vertice, Map<Vertice, Path>> shortestPathForAllVertices;

	private static final String ARRIVAL_NODE = "ARRIVAL";
	private static final String BAGGAGE_CLAIM = "BaggageClaim";

	public BaggageRouteServiceEndPointImpl(RoutingService routingService) {
		super();
		this.routingService = routingService;
	}

	public Map<String, Vertice> getVerticesMap() {
		return verticesMap;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public Map<String, FlightData> getFlightDataMap() {
		return flightDataMap;
	}

	public void loadVerticesAndEdges(String str) throws InCorrectDataFormatException {
		String[] strs = str.split(" ");
		int arrLength = strs.length;
		if (arrLength != 3) {
			throw new InCorrectDataFormatException("Wrong formatted vertice & edge data");
		}

		checkOrCreateVertice(strs[0]);
		checkOrCreateVertice(strs[1]);
		edges.add(new Edge(verticesMap.get(strs[0]), verticesMap.get(strs[1]), Integer.valueOf(strs[2])));
	}

	public void loadFlightData(String str) throws InCorrectDataFormatException {
		String[] strs = str.split(" ");
		int arrLength = strs.length;
		if (arrLength != 4) {
			throw new InCorrectDataFormatException("Wrong formatted flight data");
		}
		flightDataMap.put(strs[0], new FlightData(strs[0], strs[1], strs[3], strs[2]));
	}

	private void checkOrCreateVertice(String id) {
		if (verticesMap.get(id) == null) {
			verticesMap.put(id, new Vertice(id));
		}
	}

	public void constructShortestPath() {
		LOGGER.info("Constructing shorest path starts");
		this.shortestPathForAllVertices = routingService
				.constructShortestPathForAllVertices(new Graph(new ArrayList<>(verticesMap.values()), edges));
		LOGGER.info("Constructing shorest path ends");
	}

	public String searchPathForDestination(String str) throws InCorrectDataFormatException, InCorrectDataException {
		LOGGER.info("Invoking method for path search for given destination ");
		String[] strs = str.split(" ");
		int arrLength = strs.length;
		if (arrLength != 3) {
			throw new InCorrectDataFormatException("Wrong formatted baggage data");
		}

		String bagId = strs[0];
		String source = strs[1];
		FlightData flightData = flightDataMap.get(strs[2]);
		Vertice destinationVertice = null;

		if (flightData == null) {
			if (ARRIVAL_NODE.equals(strs[2]))
				strs[2] = BAGGAGE_CLAIM;
			destinationVertice = verticesMap.get(strs[2]);
			if (destinationVertice == null)
				throw new InCorrectDataException("Incorrect destination");
		} else {
			destinationVertice = new Vertice(flightData.getGate());
		}

		Map<Vertice, Path> shortestPathMap = shortestPathForAllVertices.get(new Vertice(source));

		if (shortestPathMap == null)
			return "";

		Path path = shortestPathMap.get(destinationVertice);

		if (path == null)
			return "";

		return bagId + " " + path.toString();
	}

}
