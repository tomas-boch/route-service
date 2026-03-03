package com.pwc.testtask.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Country {

    @JsonProperty("cca3")
    private final String id;
    private final List<String> borders;

}
