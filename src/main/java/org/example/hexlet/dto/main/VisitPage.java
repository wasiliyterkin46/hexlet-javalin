package org.example.hexlet.dto.main;

import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class VisitPage {
    private Boolean visited;

    public Boolean isVisited() {
        return Optional.ofNullable(visited).orElse(false);
    }
}
