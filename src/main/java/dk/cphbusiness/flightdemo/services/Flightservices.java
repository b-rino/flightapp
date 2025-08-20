package dk.cphbusiness.flightdemo.services;

import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.util.List;

public class Flightservices {

    public static double getTotalTimeByAirline(List<FlightInfoDTO> flightList, String airline){

        return flightList.stream().
                filter(f -> f.getAirline()!=null).
                filter(f -> f.getAirline().equalsIgnoreCase(airline)).
                mapToDouble(f -> f.getDuration().toMinutes()).sum();
    }
}
