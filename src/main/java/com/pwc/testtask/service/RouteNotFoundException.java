package com.pwc.testtask.service;

import com.pwc.testtask.common.RouteException;

public class RouteNotFoundException extends RouteException {

    final String origin;
    final String destination;

    public RouteNotFoundException(final String origin, final String destination) {
        super("Route not found from '" + origin + "' to '" + destination + "'");
        this.origin = origin;
        this.destination = destination;
    }
}
