package com.denvar.airport.baggageroute.datamodel;
import java.util.List;
import java.util.stream.Collectors;

public class Path {

	private int totalWeight;
	private List<Vertice> vertices;

	public Path(int totalWeight, List<Vertice> vertices) {
		super();
		this.totalWeight = totalWeight;
		this.vertices = vertices;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertice> vertices) {
		this.vertices = vertices;
	}

	@Override
	public String toString() {
		String pathVerticesStr = vertices.stream().map(vertice -> vertice.getId()).collect(Collectors.joining(" "));
		
		return "Path [totalWeight=" + totalWeight + ", vertices=" + pathVerticesStr + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + totalWeight;
		result = prime * result + ((vertices == null) ? 0 : vertices.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (totalWeight != other.totalWeight)
			return false;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}
	
}
