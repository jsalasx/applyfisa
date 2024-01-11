package com.drk;

import com.drk.dto.ApiResponseDTO;
import com.drk.dto.DifferentRouterRequestDTO;
import com.drk.dto.LengthRequestDTO;
import com.drk.dto.TripDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

@Path("/differentRoutes")
public class DifferentRoutes {

    private String end;
    private List<TripDTO> tripDTOList = new ArrayList<>();
    private List<TripDTO> tripDTOListOk = new ArrayList<>();
    private List<String> graph = Arrays.asList("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7");
    private int  numberOfStopsMax = 20;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response diferentRoutes(DifferentRouterRequestDTO differentRouterRequestDTO) {
        this.tripDTOList.clear();
        this.tripDTOListOk.clear();
        this.end = differentRouterRequestDTO.getEnd();;
        this.getDifferentsRoutes(differentRouterRequestDTO.getInit());
        List<TripDTO> tripsFinal  = this.tripDTOList.stream()
                .filter(x-> x.getInitNextTrip().charAt(0) == this.end.charAt(0) &&
                        x.getDistance() < 30
                ).collect(Collectors.toList());

        for(TripDTO trip : tripsFinal) {
            System.out.println(trip.toString());
        }
        ApiResponseDTO<Integer> apiResponse = new ApiResponseDTO();
        apiResponse.ApiResponseDTOOk(tripsFinal.size());
        return Response.ok(apiResponse).build();
    }


    private void getDifferentsRoutes(String init) {
        List<String> tripsInit = graph.stream().filter(x -> x.charAt(0) == init.charAt(0)).collect(Collectors.toList());
        for (int j = 0; j < tripsInit.size(); j++) {
            TripDTO tripDTO = new TripDTO();
            tripDTO.setIdTrip(String.valueOf(j));
            tripDTO.setNumberOfTrip(1);
            tripDTO.setTrip(tripsInit.get(j));
            tripDTO.setInitNextTrip(String.valueOf(tripsInit.get(j).charAt(1)));
            tripDTO.setDistance(Integer.parseInt(String.valueOf(tripsInit.get(j).charAt(2))));
            tripDTOList.add(tripDTO);
            tripDTOListOk.add(tripDTO);
        }
        for(TripDTO trip : tripDTOListOk) {
            this.getDifferentsRoutesRecursive(trip);
        }
    }

    private void getDifferentsRoutesRecursive(TripDTO tripDTOParam) {
        List<String> trips = graph.stream().filter(x -> x.charAt(0) == tripDTOParam.getInitNextTrip().charAt(0)).collect(Collectors.toList());
        List<TripDTO> tripDTOListAux = new ArrayList<>();
        for (int j = 0; j < trips.size(); j++) {
            TripDTO tripDTO = new TripDTO();
            tripDTO.setIdTrip(tripDTOParam.getIdTrip().concat(String.valueOf(j)));
            tripDTO.setNumberOfTrip(tripDTOParam.getNumberOfTrip()+1);
            tripDTO.setTrip(tripDTOParam.getTrip().concat(trips.get(j)));
            tripDTO.setInitNextTrip(String.valueOf(trips.get(j).charAt(1)));
            tripDTO.setDistance(Integer.parseInt(String.valueOf(trips.get(j).charAt(2))) + tripDTOParam.getDistance());
            tripDTOList.add(tripDTO);
            tripDTOListAux.add(tripDTO);
        }
        for(TripDTO trip : tripDTOListAux) {
            if(trip.getNumberOfTrip() < this.numberOfStopsMax) {
                this.getDifferentsRoutesRecursive(trip);
            }
        }
    }
}
