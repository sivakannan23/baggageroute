package com.denvar.airport.baggageroute.endpoint;

import com.denvar.airport.baggageroute.exception.InCorrectDataException;
import com.denvar.airport.baggageroute.exception.InCorrectDataFormatException;

public interface BaggageRouteServiceEndPoint {
	
	/**
	 * This method parses given string and load respective vertices map and edges list
	 * @param str
	 * @throws InCorrectDataFormatException
	 */
	public void loadVerticesAndEdges(String str) throws InCorrectDataFormatException;
	
	/**
	 * 
	 * @param str
	 * @throws InCorrectDataFormatException
	 */
	public void loadFlightData(String str) throws InCorrectDataFormatException;
	
	/**
	 * This method invokes shortest path construct service
	 */
	public void constructShortestPath() ;
	
	/**
	 * This method is used for parsing the given string formatted data and invoking
	 * shortest path map for the respective source and destination.
	 * 
	 * @param str
	 * @return
	 * @throws InCorrectDataFormatException
	 * @throws InCorrectDataException
	 */
	public String searchPathForDestination(String str) throws InCorrectDataFormatException, InCorrectDataException;
}
