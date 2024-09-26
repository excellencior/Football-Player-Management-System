package dto;

import Database.Club;
import Database.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class auctionedPlayersList implements Serializable {
    private List<Player> auctionedPlayers;
    private boolean refresh;
    private boolean buyButtonClicked;
    private String currentClubName;
    private List<Club> clubList;

    public auctionedPlayersList() {
        auctionedPlayers = new ArrayList<>();
        buyButtonClicked = false;
    }

    public String getCurrentClubName() {
        return currentClubName;
    }

    public void setCurrentClubName(String currentClubName) {
        this.currentClubName = currentClubName;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public List<Player> getAuctionedPlayers() {
        return auctionedPlayers;
    }

    public void setAuctionedPlayers(List<Player> auctionedPlayers) {
        this.auctionedPlayers = auctionedPlayers;
    }

    public void buyButtonClicked(boolean b) {
        this.buyButtonClicked = b;
    }
    public boolean isBuyButtonClicked() {
        return buyButtonClicked;
    }

    public void setModifiedClubList(List<Club> clubList) {
        this.clubList = clubList;
    }
    public List<Club> getModifiedClubList() {
        return clubList;
    }
}
