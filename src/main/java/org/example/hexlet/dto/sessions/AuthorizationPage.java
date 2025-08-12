package org.example.hexlet.dto.sessions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthorizationPage {
    private String currentUser;
}
