package com.denvar.airport.baggageroute.datamodel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Graph {

	private List<Vertice> vertices;
	private List<Edge> edges;

	public Graph(List<Vertice> vertices, List<Edge> edges) {
		super();
		this.vertices = vertices;
		this.edges = edges;
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public Map<Vertice, List<Edge>> getAdjacancyMap() {
		Map<Vertice, List<Edge>> reverseAdjacencyMap = new HashMap<>();
		Map<Vertice, List<Edge>> adjacencyMap = new HashMap<>();
		
		if(vertices == null || vertices.isEmpty() || edges == null || edges.isEmpty())
			return adjacencyMap;
		
		adjacencyMap =
				edges
			        .stream()
			        .collect(
			            Collectors.groupingBy(
			                Edge::getSource));
		
		reverseAdjacencyMap =
				edges
			        .stream()
			        .collect(
			            Collectors.groupingBy(
			                Edge::getDestination));
		
		
		
		for(Map.Entry<Vertice, List<Edge>>  entry :adjacencyMap.entrySet())
		{
			List<Edge> reverseEdges = reverseAdjacencyMap.get(entry.getKey());
			if(reverseEdges != null  && !reverseEdges.isEmpty() && entry.getValue() != null)
			{
				List<Edge> updatedEdges = reverseEdges.stream().map(oldEdge -> new Edge(oldEdge.getDestination(), oldEdge.getSource(), oldEdge.getWeight())).collect(Collectors.toList());
				entry.getValue().addAll(updatedEdges);
			}
		}
		
		return adjacencyMap;
	}

}
