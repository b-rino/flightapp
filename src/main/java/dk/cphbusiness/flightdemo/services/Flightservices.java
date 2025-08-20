package dk.cphbusiness.flightdemo.services;

import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
