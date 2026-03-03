package com.pwc.testtask.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

@Repository
@Slf4j
public class CountriesDataRetriever implements CountriesRepository {

    @Value("${routeservice.dataLink:https://raw.githubusercontent.com/mledoze/countries/master/countries.json}")
    private String dataLink;

    private final RestTemplate restTemplate;

    public CountriesDataRetriever(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Country> getCountries() {
        final ResponseEntity<String> response = restTemplate.getForEntity(dataLink, String.class);
        final String body = response.getBody();
        log.trace("body = {}", body);
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<Country> countries = objectMapper.readValue(body, new TypeReference<List<Country>>() {});
        log.debug("countries.size = {}", countries.size());
        log.trace("countries = {}", countries);
        return Collections.unmodifiableList(countries);
    }
}
