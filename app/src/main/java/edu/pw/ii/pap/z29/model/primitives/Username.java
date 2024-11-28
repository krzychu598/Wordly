package edu.pw.ii.pap.z29.model.primitives;

import lombok.Data;
import lombok.NonNull;


@Data
public class Username {
    @NonNull private String username;

    public Username(@NonNull String username) {
        /* TODO:
         * check whether the username has correct lenght,
         * isn't transphobic etc. */
        this.username = username;
    }
}
