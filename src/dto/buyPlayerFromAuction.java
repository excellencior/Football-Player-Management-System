package dto;

import Database.Player;

import java.io.Serializable;

public class buyPlayerFromAuction implements Serializable {
    private String clubName;
    private Player player;
    private boolean buyButtonClicked;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isBuyButtonClicked() {
        return buyButtonClicked;
    }

    public void setBuyButtonClicked(boolean buyButtonClicked) {
        this.buyButtonClicked = buyButtonClicked;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
