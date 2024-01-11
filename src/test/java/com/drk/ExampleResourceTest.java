package com.drk;

import com.drk.dto.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class ExampleResourceTest {
    @Test
    void testDiferentRoutes() {
        DifferentRoutes differentRoutes = new DifferentRoutes();
        DifferentRouterRequestDTO requestDTO = new DifferentRouterRequestDTO();
        requestDTO.setEnd("C");
        requestDTO.setInit("C");
        Response response = differentRoutes.diferentRoutes(requestDTO);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        ApiResponseDTO apiResponseDTO = response.readEntity(ApiResponseDTO.class);

        assertEquals(apiResponseDTO.getData(),7);

    }

    @Test
    void testDistance() {
        CalculateDistance calculateDistance = new CalculateDistance();
        DistanceRequestDTO requestDTO = new DistanceRequestDTO();
        requestDTO.setInput("A-E-B-C-D");

        Response response = calculateDistance.calculateDistance(requestDTO);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        ApiResponseDTO apiResponseDTO = response.readEntity(ApiResponseDTO.class);

        assertEquals(apiResponseDTO.getData(),22);

    }

    @Test
    void testDistanceFail() {
        CalculateDistance calculateDistance = new CalculateDistance();
        DistanceRequestDTO requestDTO = new DistanceRequestDTO();
        requestDTO.setInput("A-E-D");

        Response response = calculateDistance.calculateDistance(requestDTO);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        ApiResponseDTO apiResponseDTO = response.readEntity(ApiResponseDTO.class);

        assertEquals(apiResponseDTO.getMsg(),"Error: ruta no encontrada");

    }

    @Test
    void testNumberOfTrips() {
        NumberOfTrips numberOfTrips = new NumberOfTrips();
        NumberOfTripsRequestDTO requestDTO = new NumberOfTripsRequestDTO();
        requestDTO.setInit("C");
        requestDTO.setEnd("C");
        requestDTO.setMinStops(1);
        requestDTO.setMaxStops(3);
        Response response = numberOfTrips.numberOfTrips(requestDTO);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        ApiResponseDTO apiResponseDTO = response.readEntity(ApiResponseDTO.class);

        assertEquals(apiResponseDTO.getData(),2);

    }


    @Test
    void testRouteLength() {
        RouteLength routeLength = new RouteLength();
        LengthRequestDTO requestDTO = new LengthRequestDTO();
        requestDTO.setInit("B");
        requestDTO.setEnd("B");
        Response response = routeLength.routeLength(requestDTO);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        ApiResponseDTO apiResponseDTO = response.readEntity(ApiResponseDTO.class);

        assertEquals(apiResponseDTO.getData(),9);

    }
}