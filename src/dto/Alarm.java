package dto;

import java.io.Serializable;

public class Alarm implements Serializable {
    private String clubName;

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
