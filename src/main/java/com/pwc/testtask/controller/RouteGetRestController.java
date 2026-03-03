package com.pwc.testtask.controller;

import com.pwc.testtask.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "${routeservice.apiPath:routing}", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class RouteGetRestController implements ErrorController {

    private final RouteService routeService;

    public RouteGetRestController(final RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(path = "/{origin}/{destination}")
    public List<String> calculateRoute(
            @PathVariable final String origin,
            @PathVariable final String destination) {
        log.debug("origin = {}, destination = {}", origin, destination);
        return routeService.getRoute(origin, destination).getBorderCrossings();
    }
}
