package dk.cphbusiness.flightdemo.services;

import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightServices {

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

    public static List<FlightInfoDTO> flightsDepartingBefore(List<FlightInfoDTO> flightList, LocalTime departureTime) {
        return flightList.stream().filter(f -> f.getDeparture().toLocalTime().isBefore(departureTime)).collect(Collectors.toList());
    }

    public static Map<String, Double> getAverageFlightTimeForAll(List<FlightInfoDTO> flightList) {
        return flightList.stream().filter(f -> f.getAirline() != null).collect(Collectors.groupingBy(FlightInfoDTO::getAirline, Collectors.averagingDouble(f -> f.getDuration().toMinutes())));
    }

    public static List<FlightInfoDTO> flightsSortedByArrival(List<FlightInfoDTO> flightList) {
        return flightList.stream().filter(f -> f.getArrival() != null).sorted(Comparator.comparing(FlightInfoDTO::getArrival)).toList();
    }

    public static Map<String, Double> totalFlightTimeForAll(List<FlightInfoDTO> flightList) {
        return flightList.stream().filter(f -> f.getAirline() != null).collect(Collectors.groupingBy(FlightInfoDTO::getAirline, Collectors.summingDouble(f -> f.getDuration().toMinutes())));
    }

    public static List<FlightInfoDTO> flightsSortedByDuration(List<FlightInfoDTO> flightList) {
        return flightList.stream().filter(f -> f.getDuration() != null).sorted(Comparator.comparing(FlightInfoDTO::getDuration)).toList();
    }
}
