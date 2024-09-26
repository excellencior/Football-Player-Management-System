package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Club implements Serializable {
    private String name;
    private double maxSalary;
    private int maxAge;
    private double maxHeight;
    private List<Player> players;
    private double totalSalary;

    public Club() {
        maxSalary = 0;
        maxAge = 0;
        maxHeight = 0;
        name = "";
        totalSalary = 0;
        players = new ArrayList<>();
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setPlayer(Player p) {
        players.add(p);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public double getMaxHeight() {
        return maxHeight;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public void setAllPlayers(List<Player> players) {
        this.players = players;
    }

    public double getTotalSalary() {
        totalSalary = 0;
        for (Player player : players)  {
            totalSalary += player.getWeeklySalary();
        }
        return totalSalary;
    }

    public void refreshProperties() {
        maxHeight = 0;
        maxSalary = 0;
        maxAge = 0;
        for (Player player : players) {
            if (maxAge < player.getAge()) {
                maxAge = player.getAge();
            }
            if (maxSalary < player.getWeeklySalary()) {
                maxSalary = player.getWeeklySalary();
            }
            if (maxHeight < player.getHeight()) {
                maxHeight = player.getHeight();
            }
        }
    }
}
