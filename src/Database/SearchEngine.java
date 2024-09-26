package Database;

import java.io.*;
import dto.auctionedPlayersList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchEngine implements Serializable {
    private List<Club> clubList;

    public SearchEngine() {
        clubList = new ArrayList<>();
    }

    public void setClubList(List<Club> clubList) {
        this.clubList = clubList;
    }

    public void modifyClubs(auctionedPlayersList ob) {
        this.clubList = ob.getModifiedClubList();
    }

    // -------------------------------------------------------------------------------------

    public List<Player> searchByPlayerName(String clubName, String playerName) {
        List<Player> t = new ArrayList<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                for (Player player : club.getPlayers()) {
                    if (player.getName().equalsIgnoreCase(playerName)) {
                        t.add(player);
                    }
                }
            }
        }
        return t;
    }

    public List<Player> searchByCountryName(String clubname, String country) {
        List<Player> t = new ArrayList<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubname)) {
                for (Player player : club.getPlayers()) {
                    if (player.getCountry().equalsIgnoreCase(country)) {
                        t.add(player);
                    }
                }
            }
        }
        return t;
    }

    public List<Player> searchByPosition(String clubName, String position) {
        List<Player> t = new ArrayList<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                for (Player player : club.getPlayers()) {
                    if (player.getPosition().equalsIgnoreCase(position)) {
                        t.add(player);
                    }
                }
            }
        }
        return t;
    }

    public List<Player> searchBySalaryRange(String clubName, double salaryFrom, double salaryTo) {
        List<Player> t = new ArrayList<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                for (Player player : club.getPlayers()) {
                    if (player.getWeeklySalary() >= salaryFrom && player.getWeeklySalary() <= salaryTo) {
                        t.add(player);
                    }
                }
            }
        }
        return t;
    }

    public List<Player> searchByCountryWisePlayerCount(String clubName) {
        List<Player> t = new ArrayList<>();
        HashMap<String, Integer> countryList = new HashMap<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                for (Player player : club.getPlayers()) {
                    String countryName = player.getCountry();
                    countryList.put(countryName, countryList.containsKey(countryName) ? countryList.get(countryName) + 1 : 1);
                }
            }
        }
        for (String S : countryList.keySet()) {
            Player tempPlayer = new Player();
            tempPlayer.setName(S);
            tempPlayer.setNumber(countryList.get(S));
            t.add(tempPlayer);
        }
        return t;
    }

    //-------------------------------Club-----------------------------
    public List<Player> playerWithTheMaxSalary(String clubName) {
        List<Player> t = new ArrayList<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                for (Player player : club.getPlayers()) {
                    if (player.getWeeklySalary() == club.getMaxSalary()) {
                        t.add(player);
                    }
                }
            }
        }
        return t;
    }

    public List<Player> playerWithTheMaxAge(String clubName) {
        List<Player> t = new ArrayList<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                for (Player player : club.getPlayers()) {
                    if (player.getAge() == club.getMaxAge()) {
                        t.add(player);
                    }
                }
            }
        }
        return t;
    }

    public List<Player> playerWithTheMaxHeight(String clubName) {
        List<Player> t = new ArrayList<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                for (Player player : club.getPlayers()) {
                    if (player.getHeight() == club.getMaxHeight()) {
                        t.add(player);
                    }
                }
            }
        }
        return t;
    }

    public double totalYearlySalary(String cname) {
        double weeklyTotalSalary = 0.0;
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(cname)) {
                weeklyTotalSalary = club.getTotalSalary();
            }
        }
        weeklyTotalSalary *= 52.0; // 1 Year = 52 weeks
        return weeklyTotalSalary;
    }

    public List<Player> getAllPlayers(String clubName) {
        List<Player> players = new ArrayList<>();
        for (Club club : clubList) {
            if (club.getName().equalsIgnoreCase(clubName)) {
                players.addAll(club.getPlayers());
                break;
            }
        }
        return players;
    }
}
