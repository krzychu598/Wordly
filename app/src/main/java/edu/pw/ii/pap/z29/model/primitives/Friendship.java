package edu.pw.ii.pap.z29.model.primitives;

import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder=true)
public class Friendship {
    private int inviting;
    private int invited;
    private boolean pending;

    public Friendship(int inviting, int invited, boolean pending) {
        this.inviting = inviting;
        this.invited = invited;
        this.pending = pending;
    }
}
