package com.drk;

import com.drk.dto.DifferentRouterRequestDTO;
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
        // Configura requestDTO con datos de prueba
        requestDTO.setEnd("C");
        requestDTO.setInit("C");
        Response response = differentRoutes.diferentRoutes(requestDTO);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        // Aquí puedes añadir más aserciones para verificar el cuerpo de la respuesta
    }

}