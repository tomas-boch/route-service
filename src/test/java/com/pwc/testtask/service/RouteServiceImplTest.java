package com.pwc.testtask.service;

import com.pwc.testtask.repository.CountriesRepository;
import com.pwc.testtask.repository.Country;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class RouteServiceImplTest {

    @Mock
    private CountriesRepository countriesRepository;

    @InjectMocks
    private RouteServiceImpl underTest;

    @Test
    void getRoute_null() {
        final var ex = assertThrows(RouteNotFoundException.class, () -> underTest.getRoute(null, null));

        assertEquals("Route not found from 'null' to 'null'", ex.getMessage());
    }

    @Test
    void getRoute_sameValue() {
        final Route result = underTest.getRoute("ABC", "ABC");

        assertEquals(new Route(List.of("ABC")), result);
    }

    @Test
    void getRoute_noRoute() {
        final String origin = "ABC";
        final String destination = "DEF";
        when(countriesRepository.getCountries()).thenReturn(prepareCountriesWithIslands());

        final var ex = assertThrows(RouteNotFoundException.class, () -> underTest.getRoute(origin, destination));

        assertEquals("Route not found from '" + origin + "' to '" + destination + "'", ex.getMessage());
    }

    @Test
    void getRoute_success() {
        final String origin = "ABC";
        final String destination = "MNO";
        when(countriesRepository.getCountries()).thenReturn(prepareCountries());

        final Route result = underTest.getRoute(origin, destination);

        assertEquals(new Route(List.of("ABC", "DEF", "JKL", "MNO")), result);
    }

    private static List<Country> prepareCountriesWithIslands() {
        final List<Country> countries = new ArrayList<>();
        countries.add(new Country("ABC", Collections.emptyList()));
        countries.add(new Country("DEF", Collections.emptyList()));
        countries.add(new Country("GHI", List.of("JKL")));
        countries.add(new Country("JKL", List.of("GHI")));
        return countries;
    }

    /*
    ABC --- DEF --- GHI
             |----- JKL --- MNO
     */
    private static List<Country> prepareCountries() {
        final List<Country> countries = new ArrayList<>();
        countries.add(new Country("ABC", List.of("DEF")));
        countries.add(new Country("DEF", List.of("ABC", "GHI", "JKL")));
        countries.add(new Country("GHI", List.of("DEF")));
        countries.add(new Country("JKL", List.of("DEF", "MNO")));
        countries.add(new Country("MNO", List.of("JKL")));
        return countries;
    }
}
