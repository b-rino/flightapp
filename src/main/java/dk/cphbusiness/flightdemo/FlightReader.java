package dk.cphbusiness.flightdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;
import dk.cphbusiness.flightdemo.services.FlightServices;
import dk.cphbusiness.utils.Utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class FlightReader {

    public static void main(String[] args) {
        try {
            //pre-made
            List<FlightDTO> flightList = getFlightsFromFile("flights.json");
            List<FlightInfoDTO> flightInfoDTOList = getFlightInfoDetails(flightList);
            //flightInfoDTOList.forEach(System.out::println);

            //1. opgave
            System.out.println(FlightServices.getTotalTimeByAirline(flightInfoDTOList, "LUFTHANSA"));

            //2. opgave
            System.out.println((FlightServices.getAverageFlightTimeByAirline(flightInfoDTOList, "LUFTHANSA")));

            //3. opgave
            List<FlightInfoDTO> listBetween = FlightServices.flightBetweenAirports(flightInfoDTOList, "Abu Dhabi International", "Heathrow");
            //listBetween.forEach(f -> System.out.println(f.toString()));

            //4. opgave
            LocalTime departureTime = LocalTime.of(1,0);
            List<FlightInfoDTO> earlyFlights = FlightServices.flightsDepartingBefore(flightInfoDTOList, departureTime);
            //earlyFlights.forEach(System.out::println);

            //5. opgave
            Map<String, Double> airlineAvg = FlightServices.getAverageFlightTimeForAll(flightInfoDTOList);
            airlineAvg.forEach((key, value) -> System.out.printf("Average flightime for %s: %.2f minutes%n", key, value));

            //6. opgave
            List<FlightInfoDTO> byArrivalTime = FlightServices.flightsSortedByArrival(flightInfoDTOList);
            //byArrivalTime.forEach(System.out::println);

            //7. opgave
            Map<String, Double> totalPerAirline = FlightServices.totalFlightTimeForAll(flightInfoDTOList);
            totalPerAirline.forEach((key, value) -> System.out.printf("Total flightime for %s: %.2f minutes%n", key, value));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<FlightDTO> getFlightsFromFile(String filename) throws IOException {

        ObjectMapper objectMapper = Utils.getObjectMapper();

        // Deserialize JSON from a file into FlightDTO[]
        FlightDTO[] flightsArray = objectMapper.readValue(Paths.get("flights.json").toFile(), FlightDTO[].class);

        // Convert to a list
        List<FlightDTO> flightsList = List.of(flightsArray);
        return flightsList;
    }

    public static List<FlightInfoDTO> getFlightInfoDetails(List<FlightDTO> flightList) {
        List<FlightInfoDTO> flightInfoList = flightList.stream()
                .map(flight -> {
                    LocalDateTime departure = flight.getDeparture().getScheduled();
                    LocalDateTime arrival = flight.getArrival().getScheduled();
                    Duration duration = Duration.between(departure, arrival);
                    FlightInfoDTO flightInfo =
                            FlightInfoDTO.builder()
                                    .name(flight.getFlight().getNumber())
                                    .iata(flight.getFlight().getIata())
                                    .airline(flight.getAirline().getName())
                                    .duration(duration)
                                    .departure(departure)
                                    .arrival(arrival)
                                    .origin(flight.getDeparture().getAirport())
                                    .destination(flight.getArrival().getAirport())
                                    .build();

                    return flightInfo;
                })
                .toList();
        return flightInfoList;
    }

}
