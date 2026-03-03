package com.pwc.testtask.service;

import com.pwc.testtask.repository.CountriesRepository;
import com.pwc.testtask.repository.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private final CountriesRepository countriesRepository;

    public RouteServiceImpl(final CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    @Override
    public Route getRoute(final String origin, final String destination) {
        if (origin == null || destination == null) {
            throw new RouteNotFoundException(origin, destination);
        }
        if (origin.equals(destination)) {
            return new Route(List.of(origin));
        }
        final List<Country> countries = countriesRepository.getCountries();
        final Map<String, Country> countriesMap = getCountriesMap(countries);
        if (!countriesMap.containsKey(origin) || !countriesMap.containsKey(destination)) {
            throw new RouteNotFoundException(origin, destination);
        }
        final List<String> borderCrossings = findRoute(origin, destination, countriesMap);
        return new Route(borderCrossings);
    }

    // Breadth-First Search algorithm
    private static List<String> findRoute(
            final String origin, final String destination, final Map<String, Country> countriesMap) {
        final Queue<String> queue = new LinkedList<>();
        queue.add(origin);

        final Set<String> visited = new HashSet<>();
        visited.add(origin);

        final Map<String, String> previous = new HashMap<>();
        previous.put(origin, null);

        while (!queue.isEmpty()) {
            final String currentId = queue.poll();
            if (currentId.equals(destination)) {
                break;
            }
            final Country current = countriesMap.get(currentId);
            for (final String neighborId : current.getBorders()) {
                if (!visited.contains(neighborId)) {
                    visited.add(neighborId);
                    previous.put(neighborId, currentId);
                    queue.add(neighborId);
                }
            }
        }

        // route reconstruction
        if (!previous.containsKey(destination)) {
            throw new RouteNotFoundException(origin, destination);
        }
        final List<String> route = new ArrayList<>();
        String step = destination;
        while (step != null) {
            route.add(step);
            step = previous.get(step);
        }
        Collections.reverse(route);
        return route;
    }

    public static Map<String, Country> getCountriesMap(final List<Country> countries) {
        return countries.stream().collect(Collectors.toMap(Country::getId, country -> country));
    }
}
