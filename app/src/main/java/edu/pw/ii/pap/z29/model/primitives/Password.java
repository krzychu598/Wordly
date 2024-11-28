package edu.pw.ii.pap.z29.model.primitives;

import lombok.Data;
import lombok.NonNull;


@Data
public class Password {
    @NonNull private String password;

    public Password(@NonNull String password) {
        /* TODO:
         * check whether the password contains a big letter,
         * a special character etc. */
        this.password = password;
    }
}
