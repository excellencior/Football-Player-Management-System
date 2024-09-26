package MainPackage;

import Database.Player;

public class CountryProperties {
    private String playerName;
    private int playerCount;

    public CountryProperties(Player player) {
        this.playerName = player.getName();
        this.playerCount = player.getNumber();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
