package com.example.server.student.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SolvedacResponse {

    int count;
    List<ItemResponse> items;
}
