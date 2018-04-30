package com.denvar.airport.baggageroute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.denvar.airport.baggageroute.datamodel.Edge;
import com.denvar.airport.baggageroute.datamodel.Graph;
import com.denvar.airport.baggageroute.datamodel.Path;
import com.denvar.airport.baggageroute.datamodel.Vertice;
import com.denvar.airport.baggageroute.service.RoutingService;
import com.denvar.airport.baggageroute.service.impl.BaggageRoutingServiceImpl;

import org.junit.Test;

@RunWith(JUnit4ClassRunner.class)
public class BaggageRoutingServiceImplTest
{
	private RoutingService routingService;
	
	private Graph graph;
	
	private Map<Vertice, List<Edge>> adjacentMap;

	@Before
	public void setProperties() {
		routingService = new BaggageRoutingServiceImpl();
		graph = new Graph(createMockVertices(), createMockEdges());
		adjacentMap = graph.getAdjacancyMap();
	}
	
	@Test
	public void testShortestPathForSingleVerticeWithForwardPath() {
		Map<Vertice, Map<Vertice, Path>> shortestPathMap = new HashMap<>();
		routingService.constructShortestPath(new Vertice("A5"), adjacentMap, graph.getVertices() , shortestPathMap);
		
		Assert.assertEquals(createMockSampleForwardPath(), shortestPathMap.get(new Vertice("A5")).get(new Vertice("A4")));
	}
	
	@Test
	public void testShortestPathForSingleVerticeWithReversePath() {
		Map<Vertice, Map<Vertice, Path>> shortestPathMap = new HashMap<>();
		routingService.constructShortestPath(new Vertice("A8"), adjacentMap, graph.getVertices() , shortestPathMap);

		Assert.assertEquals(createMockSampleReversePath(), shortestPathMap.get(new Vertice("A8")).get(new Vertice("A5")));
	}
	

	@Test
	public void testShortestPathForAllVerticeWithBothForwardAndReversePaths() {
		Map<Vertice, Map<Vertice, Path>> shortestPathMap = routingService.constructShortestPathForAllVertices(graph);

		Assert.assertEquals(createMockSampleReversePath(), shortestPathMap.get(new Vertice("A8")).get(new Vertice("A5")));
		Assert.assertEquals(createMockSampleForwardPath(), shortestPathMap.get(new Vertice("A5")).get(new Vertice("A4")));
		Assert.assertEquals(createMockBothForwardPath2(), shortestPathMap.get(new Vertice("Concourse_A_Ticketing")).get(new Vertice("A1")));
	}
	
	private Path createMockSampleForwardPath()
	{
		List<Vertice> resultVertices = new ArrayList<>();
		resultVertices.add(new Vertice("A5"));
		resultVertices.add(new Vertice("A1"));
		resultVertices.add(new Vertice("A2"));
		resultVertices.add(new Vertice("A3"));
		resultVertices.add(new Vertice("A4"));
		return new Path(9, resultVertices);
	}
	
	private Path createMockSampleReversePath()
	{
		List<Vertice> resultVertices = new ArrayList<>();
		resultVertices.add(new Vertice("A8"));
		resultVertices.add(new Vertice("A9"));
		resultVertices.add(new Vertice("A10"));
		resultVertices.add(new Vertice("A5"));
		return new Path(6, resultVertices);
	}
	
	private Path createMockBothForwardPath2()
	{
		List<Vertice> resultVertices = new ArrayList<>();
		resultVertices.add(new Vertice("Concourse_A_Ticketing"));
		resultVertices.add(new Vertice("A5"));
		resultVertices.add(new Vertice("A1"));
		return new Path(11, resultVertices);
	}
	private List<Vertice> createMockVertices()
	{
		List<Vertice> vertices = new ArrayList<>();
		vertices.add(new Vertice("A5"));
		vertices.add(new Vertice("A10"));
		vertices.add(new Vertice("A1"));
		vertices.add(new Vertice("A2"));
		vertices.add(new Vertice("A3"));
		vertices.add(new Vertice("A4"));
		
		vertices.add(new Vertice("Concourse_A_Ticketing"));
		vertices.add(new Vertice("BaggageClaim"));
		vertices.add(new Vertice("A9"));
		vertices.add(new Vertice("A8"));
		vertices.add(new Vertice("A7"));
		vertices.add(new Vertice("A6"));
		return vertices;
		
	}
	
	private List<Edge> createMockEdges()
	{
		List<Edge> edges = new ArrayList<>();
		edges.add(new Edge(new Vertice("Concourse_A_Ticketing"), new Vertice("A5"), 5));
		
		edges.add(new Edge(new Vertice("A5"), new Vertice("BaggageClaim"), 5));
		edges.add(new Edge(new Vertice("A5"), new Vertice("A10"), 4));
		edges.add(new Edge(new Vertice("A5"), new Vertice("A1"), 6));
		edges.add(new Edge(new Vertice("A1"), new Vertice("A2"), 1));
		edges.add(new Edge(new Vertice("A2"), new Vertice("A3"), 1));
		edges.add(new Edge(new Vertice("A3"), new Vertice("A4"), 1));
		edges.add(new Edge(new Vertice("A10"), new Vertice("A9"), 1));
		edges.add(new Edge(new Vertice("A9"), new Vertice("A8"), 1));
		edges.add(new Edge(new Vertice("A8"), new Vertice("A7"), 1));
		edges.add(new Edge(new Vertice("A7"), new Vertice("A6"), 1));
		
		return edges;
		
		
	}
}
