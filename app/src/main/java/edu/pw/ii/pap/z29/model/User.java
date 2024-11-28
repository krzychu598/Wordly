package edu.pw.ii.pap.z29.model;

import lombok.Data;
import lombok.NonNull;


@Data
public class User {
    private int userId;
    @NonNull private Username username;

    public User(@NonNull Username username) {
        this.userId = -1;
        this.username = username;
    }

    public User(int userId, @NonNull Username username) {
        this.userId = userId;
        this.username = username;
    }
}
