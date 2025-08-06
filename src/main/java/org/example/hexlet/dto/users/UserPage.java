package org.example.hexlet.dto.users;

import org.example.hexlet.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserPage {
    private User user;
}
