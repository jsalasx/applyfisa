package com.drk;


import com.drk.dto.ApiResponseDTO;
import com.drk.dto.LengthRequestDTO;
import com.drk.dto.NumberOfTripsRequestDTO;
import com.drk.dto.TripDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

@Path("/routeLength")
public class RouteLength {

    private String end;
    private List<TripDTO> tripDTOList = new ArrayList<>();
    private List<TripDTO> tripDTOListOk = new ArrayList<>();
    private List<String> graph = Arrays.asList("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7");
    private int  numberOfStopsMax = 9;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response routeLength(LengthRequestDTO lengthRequestDTO) {
        this.tripDTOList.clear();
        this.tripDTOListOk.clear();
        this.end = lengthRequestDTO.getEnd();;
        this.getLength(lengthRequestDTO.getInit());
        List<TripDTO> tripsFinal  = this.tripDTOList.stream()
                .filter(x-> x.getInitNextTrip().charAt(0) == this.end.charAt(0)
                ).collect(Collectors.toList());

        Optional<TripDTO> tripWithMinDistance = tripsFinal.stream()
                .min(Comparator.comparingInt(TripDTO::getDistance));

        if (tripWithMinDistance.isPresent()) {
            ApiResponseDTO<Integer> apiResponse = new ApiResponseDTO();
            apiResponse.ApiResponseDTOOk(tripWithMinDistance.get().getDistance());
            return Response.ok(apiResponse).build();
        }
        ApiResponseDTO<Integer> apiResponse = new ApiResponseDTO();
        apiResponse.ApiResponseDTOOk(0);
        return Response.ok(apiResponse).build();
    }


    private void getLength(String init) {
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
            this.getLengthRecursive(trip);
        }
    }

    private void getLengthRecursive(TripDTO tripDTOParam) {
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
                this.getLengthRecursive(trip);
            }
        }
    }

}
