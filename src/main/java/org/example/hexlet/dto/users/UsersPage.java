package org.example.hexlet.dto.users;

import org.example.hexlet.model.User;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsersPage {
    private List<User> users;
    private String termName;
    private Boolean usersExist;
    private String flashMessage;
}
