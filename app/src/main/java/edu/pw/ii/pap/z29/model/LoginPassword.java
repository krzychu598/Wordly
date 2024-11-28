package edu.pw.ii.pap.z29.model;

import lombok.Data;
import lombok.NonNull;


@Data
public class LoginPassword {
    private int userId;
    @NonNull private Password password;

    public LoginPassword(int userId, @NonNull Password password) {
        this.userId = userId;
        this.password = password;
    }
}
