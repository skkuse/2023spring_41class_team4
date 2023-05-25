package com.example.server.student.problem.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SolvedacResponse {

    int count;
    List<ItemResponse> items;
}
