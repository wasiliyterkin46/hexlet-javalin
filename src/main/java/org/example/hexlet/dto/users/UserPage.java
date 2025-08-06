package org.example.hexlet.dto.users;

import org.example.hexlet.model.User;

import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserPage {
    @NonNull
    @Setter(AccessLevel.NONE)
    private User user;
    private String messageOnErrorSaveUser = "";
    private Boolean isErrorDataRegistration = false;
}
