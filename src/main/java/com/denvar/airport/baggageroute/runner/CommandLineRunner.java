package com.denvar.airport.baggageroute.runner;

import java.util.Scanner;
import org.apache.log4j.Logger;

import com.denvar.airport.baggageroute.endpoint.BaggageRouteServiceEndPoint;
import com.denvar.airport.baggageroute.endpoint.impl.BaggageRouteServiceEndPointImpl;
import com.denvar.airport.baggageroute.exception.InCorrectDataException;
import com.denvar.airport.baggageroute.exception.InCorrectDataFormatException;
import com.denvar.airport.baggageroute.service.impl.BaggageRoutingServiceImpl;

public class CommandLineRunner {
	
	private final static Logger LOGGER = Logger.getLogger(CommandLineRunner.class);

	public static void main(String[] args) {

		BaggageRouteServiceEndPoint baggageRouteServiceEndPoint = new BaggageRouteServiceEndPointImpl(
				new BaggageRoutingServiceImpl());

		try (Scanner in = new Scanner(System.in)) {
			int count = 0;
			boolean isShortestPathConstructured = false;
			while (true) {
				String line = in.nextLine().trim();

				if (line.isEmpty())
					continue;

				if (line.equals("*"))
					count++;

				switch (count) {
				case 0: {
					try {
						baggageRouteServiceEndPoint.loadVerticesAndEdges(line);
					} catch (InCorrectDataFormatException dataFormatException) {
						LOGGER.error(dataFormatException.getMessage());
					}

					break;
				}
				case 1: {
					try {
						baggageRouteServiceEndPoint.loadFlightData(line);
					} catch (InCorrectDataFormatException dataFormatException) {
						LOGGER.error(dataFormatException.getMessage());
					}
					break;
				}
				case 2: {
					try {
						if (!isShortestPathConstructured) {
							isShortestPathConstructured = true;
							baggageRouteServiceEndPoint.constructShortestPath();
						}
						System.out.println(baggageRouteServiceEndPoint.searchPathForDestination(line));
					} catch (InCorrectDataFormatException | InCorrectDataException exception) {
						LOGGER.error(exception.getMessage());
					}
					break;
				}
				case 3: {
					System.out.println("Process Ends");
					System.exit(0);
					break;
				}
				default:
					break;
				}

			}
		}

	}
}
