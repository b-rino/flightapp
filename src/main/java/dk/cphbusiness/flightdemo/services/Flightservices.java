package dk.cphbusiness.flightdemo.services;

import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Flightservices {

    public static double getTotalTimeByAirline(List<FlightInfoDTO> flightList, String airline){

        return flightList.stream().
                filter(f -> f.getAirline()!=null).
                filter(f -> f.getAirline().equalsIgnoreCase(airline)).
                mapToDouble(f -> f.getDuration().toMinutes()).sum();
    }

    public static Map<String, Double> getAverageFlightTimeByAirline(List<FlightInfoDTO> flightList, String airline) {

        Double avgTime = flightList.stream().filter(f -> f.getAirline() != null).filter(f -> f.getAirline().equalsIgnoreCase(airline)).mapToDouble(f -> f.getDuration().toMinutes()).average().orElse(0.0);

        Map<String, Double> averageFlightTime = new HashMap<>();
        averageFlightTime.put(airline, avgTime);
        return averageFlightTime;
    }

    public static List<FlightInfoDTO> flightBetweenAirports(List<FlightInfoDTO> flightList, String origin, String destination) {
        return flightList.stream().filter(f -> f.getOrigin() != null && f.getDestination() != null).filter(flight -> flight.getOrigin().equals(origin) && flight.getDestination().equals(destination)).collect(Collectors.toList());
    }


}
