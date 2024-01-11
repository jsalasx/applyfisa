package com.drk;

import com.drk.dto.ApiResponseDTO;
import com.drk.dto.DistanceRequestDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Path("/calculateDistance")
public class CalculateDistance {

    private List<String> graph = Arrays.asList("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7");;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response calculateDistance(DistanceRequestDTO distanceRequestDTO)
    {
        String [] inputSplit = distanceRequestDTO.getInput().split("-");
//        System.out.println(inputSplit[0]);
//        System.out.println(inputSplit[1]);
//        System.out.println(inputSplit[2]);
//        System.out.println(inputSplit.length);
        int sumaDistancias = 0;
        for (int i = 0; i < inputSplit.length-1; i++) {
            try {
                sumaDistancias = sumaDistancias + getDistance(inputSplit[i],inputSplit[i+1]);
            } catch (Exception e) {
                ApiResponseDTO<Integer> apiResponse = new ApiResponseDTO();
                apiResponse.ApiResponseError(e.getMessage());
                return Response.ok(apiResponse).build();
            }
        }
        ApiResponseDTO<Integer> apiResponse = new ApiResponseDTO();
        apiResponse.ApiResponseDTOOk(sumaDistancias);
        return Response.ok(apiResponse).build();
    }

    private int getDistance(String init,String end) throws Exception {
        List<String> distance = this.graph.stream().filter(x -> x.charAt(0) == (init.charAt(0)) && x.charAt(1) == (end.charAt(0)))
                .map(x -> x.substring(2))
                .collect(Collectors.toList());
        if (distance.isEmpty()) {
            throw new Exception("Error: ruta no encontrada");
        } else {
            return Integer.parseInt(distance.get(0));
        }
    }
}
