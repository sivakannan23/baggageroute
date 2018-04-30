package com.denvar.airport.baggageroute.service;

import java.util.List;
import java.util.Map;

import com.denvar.airport.baggageroute.datamodel.Edge;
import com.denvar.airport.baggageroute.datamodel.Graph;
import com.denvar.airport.baggageroute.datamodel.Path;
import com.denvar.airport.baggageroute.datamodel.Vertice;

public interface RoutingService {

	/**
	 * This method constructs shortest path map for all the vertices in the given
	 * graph.
	 * 
	 * @param graph
	 * @return Map of shortest path for all vertices
	 */
	public  Map<Vertice, Map<Vertice, Path>> constructShortestPathForAllVertices(Graph graph);
	
	/**
	 *  This method constructs shortest path map for the particular vertice in the
	 *  given graph.
	 * 
	 * @param vertice
	 * @param adjacencyMap
	 * @param allVertices
	 * @param shortestPathsForAllVerticesMap
	 */
	public void constructShortestPath(Vertice vertice, Map<Vertice, List<Edge>> adjacencyMap,
			List<Vertice> allVertices, Map<Vertice, Map<Vertice, Path>> shortestPathsForAllVerticesMap) ;
}
