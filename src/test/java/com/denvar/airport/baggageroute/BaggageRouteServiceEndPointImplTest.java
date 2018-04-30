package com.denvar.airport.baggageroute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import com.denvar.airport.baggageroute.datamodel.Edge;
import com.denvar.airport.baggageroute.datamodel.FlightData;
import com.denvar.airport.baggageroute.datamodel.Vertice;
import com.denvar.airport.baggageroute.endpoint.impl.BaggageRouteServiceEndPointImpl;
import com.denvar.airport.baggageroute.exception.InCorrectDataFormatException;
import com.denvar.airport.baggageroute.service.impl.BaggageRoutingServiceImpl;

@RunWith(JUnit4ClassRunner.class)
public class BaggageRouteServiceEndPointImplTest {

	private BaggageRouteServiceEndPointImpl baggageRouteServiceEndPoint;

	@Before
	public void setProperties() {
		baggageRouteServiceEndPoint = new BaggageRouteServiceEndPointImpl(new BaggageRoutingServiceImpl());
	}
	
	@Test
	public void testLoadVerticesAndEdgesWithCorrectFormattedString() throws InCorrectDataFormatException {
		baggageRouteServiceEndPoint.loadVerticesAndEdges("A5 A10 4");
		
		Assert.assertEquals(new Vertice("A5"), baggageRouteServiceEndPoint.getVerticesMap().get("A5"));
		Assert.assertEquals(new Vertice("A10"), baggageRouteServiceEndPoint.getVerticesMap().get("A10"));
		Assert.assertTrue(baggageRouteServiceEndPoint.getEdges().contains(new Edge(new Vertice("A5"), new Vertice("A10"), 4)));
	}
	
	@Test(expected = InCorrectDataFormatException.class)
	public void testLoadVerticesAndEdgesWithInCorrectFormattedString() throws InCorrectDataFormatException {
		baggageRouteServiceEndPoint.loadVerticesAndEdges("A5 A10 4 90");
	}
	
	@Test
	public void testLoadFlightDataWithCorrectFormattedString() throws InCorrectDataFormatException {
		baggageRouteServiceEndPoint.loadFlightData("UA10 A1 MIA 08:00");
		
		Assert.assertEquals(new FlightData("UA10" , "A1", "08:00" , "MIA"), baggageRouteServiceEndPoint.getFlightDataMap().get("UA10"));
	}
	
	@Test(expected = InCorrectDataFormatException.class)
	public void testLoadFlightDataWithInCorrectFormattedString() throws InCorrectDataFormatException {
		baggageRouteServiceEndPoint.loadFlightData("A5 A10 4 90 12");
	}
	
	
}
