package com.denvar.airport.baggageroute.service.impl;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.denvar.airport.baggageroute.datamodel.Edge;
import com.denvar.airport.baggageroute.datamodel.Graph;
import com.denvar.airport.baggageroute.datamodel.Path;
import com.denvar.airport.baggageroute.datamodel.Vertice;
import com.denvar.airport.baggageroute.service.RoutingService;

/**
 * @author sivakkannanmuthukumar
 * 
 *         This class is Thread safe. Methods in this class helps for
 *         constructing shortest path for given Graph.
 * 
 */
public class BaggageRoutingServiceImpl implements RoutingService {

	private static final int THREAD_COUNT = 5;

	private final static Logger LOGGER = Logger.getLogger(BaggageRoutingServiceImpl.class);

	@Override
	public Map<Vertice, Map<Vertice, Path>> constructShortestPathForAllVertices(Graph graph) {
		Map<Vertice, Map<Vertice, Path>> shortestPathsForAllVerticesMap = new ConcurrentHashMap<>();
		Map<Vertice, List<Edge>> adjacencyMap = Collections.unmodifiableMap(graph.getAdjacancyMap());
		List<Vertice> verticeList = Collections.unmodifiableList(graph.getVertices());
		Executor executor = Executors.newFixedThreadPool(THREAD_COUNT);
		List<Future> shortestPathProcessFutureList = new ArrayList<>();

		for (Vertice vertice : verticeList) {
			Runnable runnable = () -> constructShortestPath(vertice, adjacencyMap, verticeList,
					shortestPathsForAllVerticesMap);
			shortestPathProcessFutureList.add(CompletableFuture.runAsync(runnable, executor));
		}

		shortestPathProcessFutureList.stream().forEach(future -> {
			try {
				future.get();
			} catch (InterruptedException interruptedException) {
				LOGGER.error(interruptedException.getMessage());
			} catch (ExecutionException executionException) {
				LOGGER.error(executionException.getMessage());
			}
		});

		return shortestPathsForAllVerticesMap;
	}

	@Override
	public void constructShortestPath(Vertice vertice, Map<Vertice, List<Edge>> adjacencyMap, List<Vertice> allVertices,
			Map<Vertice, Map<Vertice, Path>> shortestPathsForAllVerticesMap) {
		int totalVerticesCount = allVertices.size();
		Map<Vertice, Path> shortestPathMap = new HashMap<>(totalVerticesCount);
		Map<Vertice, Boolean> verficationMap = allVertices.stream().collect(Collectors.toMap(x -> x, x -> false));

		List<Vertice> vertices = new ArrayList<>(totalVerticesCount);
		vertices.add(vertice);

		loadShortestPathMap(new Path(0, vertices), adjacencyMap, vertice, verficationMap, shortestPathMap);

		shortestPathsForAllVerticesMap.put(vertice, shortestPathMap);

	}

	private void loadShortestPathMap(Path existingPath, Map<Vertice, List<Edge>> adjacencyMap, Vertice vertice,
			Map<Vertice, Boolean> verficationMap, Map<Vertice, Path> shortestPathMap) {
		if (verficationMap.get(vertice))
			return;

		verficationMap.put(vertice, true);
		List<Edge> edges = adjacencyMap.get(vertice);

		if (edges == null || edges.isEmpty())
			return;

		for (Edge edge : edges) {
			Path targetPath = constructPath(existingPath, edge);
			setShortestDestination(shortestPathMap, targetPath, edge.getDestination());
			loadShortestPathMap(shortestPathMap.get(edge.getDestination()), adjacencyMap, edge.getDestination(),
					verficationMap, shortestPathMap);
		}

	}

	private Path constructPath(Path existingPath, Edge targetEdge) {
		List<Vertice> verticesList = new ArrayList<>();
		for (Vertice vertice : existingPath.getVertices())
			verticesList.add(vertice);

		Path path = new Path(existingPath.getTotalWeight(), verticesList);

		path.setTotalWeight(path.getTotalWeight() + targetEdge.getWeight());
		path.getVertices().add(targetEdge.getDestination());

		return path;
	}

	private void setShortestDestination(Map<Vertice, Path> shortestPathMap, Path targetPath, Vertice targerVertice) {
		Path currentPath = shortestPathMap.get(targerVertice);
		if (currentPath == null || currentPath.getTotalWeight() > targetPath.getTotalWeight()) {
			shortestPathMap.put(targerVertice, targetPath);
		}
	}
}
