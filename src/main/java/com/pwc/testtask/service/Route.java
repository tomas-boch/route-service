package com.pwc.testtask.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Route {

    private List<String> borderCrossings;

}
