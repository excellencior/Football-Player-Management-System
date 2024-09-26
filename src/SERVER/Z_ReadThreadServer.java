package SERVER;

import Database.Club;
import Database.Player;
import Database.SearchEngine;
import dto.Alarm;
import dto.LogInfo;
import dto.auctionedPlayersList;
import dto.buyPlayerFromAuction;
import util.NetworkUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Z_ReadThreadServer implements Runnable {
    private final Thread thr;
    private final NetworkUtil networkUtil;
    private final HashMap<String, NetworkUtil> loggedInClubs;
    private final HashMap<String, String> userMap;
    private HashMap<String, Boolean> checkStatus;
    private List<Player> auctionedPlayers;
    private String clubName;
    private List<Club> clubList;

    public Z_ReadThreadServer(HashMap<String, Boolean> checkStatus, HashMap<String, String> userMap,
                              HashMap<String, NetworkUtil> loggedInClubs,
                              List<Player> auctionedPlayers, NetworkUtil networkUtil, List<Club> clubList) {
        this.checkStatus = checkStatus;
        this.userMap = userMap;

        this.loggedInClubs = loggedInClubs;
        this.auctionedPlayers = auctionedPlayers;
        this.clubList = clubList;

        this.networkUtil = networkUtil;

        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if (o instanceof Player) {
                        Player p = (Player) o;
                        boolean unique = true;
                        for (Player player:auctionedPlayers) {
                            if (player.getName().equalsIgnoreCase(p.getName())) {
                                unique = false;
                            }
                        }
                        if (unique) {
                            auctionedPlayers.add(p);
                        }

                        auctionedPlayersList ob = new auctionedPlayersList();
                        ob.setRefresh(true);
                        ob.setAuctionedPlayers(auctionedPlayers);
                        for (String S : loggedInClubs.keySet()) {
                            loggedInClubs.get(S).write(ob);
                        }

                    }

                    if (o instanceof LogInfo) {
                        LogInfo logInfo = (LogInfo) o;
                        if (checkStatus.get(logInfo.getUserName()) == false) {
                            this.clubName = logInfo.getUserName();
                            String password = userMap.get(logInfo.getUserName());
                            logInfo.setStatus(logInfo.getPassword().equalsIgnoreCase(password));
                            if (logInfo.getStatus()) {
                                loggedInClubs.put(logInfo.getUserName(), networkUtil);
                                checkStatus.put(logInfo.getUserName(), true);
                            }
                            networkUtil.write(logInfo);
                        }
                        else {
                            Alarm ob = new Alarm();
                            networkUtil.write(ob);
                        }
                    }

                    if (o instanceof Alarm) {
                        Alarm ob = (Alarm) o;
                        checkStatus.put(ob.getClubName(), false);
                    }

                    if (o instanceof auctionedPlayersList) {
                        auctionedPlayersList ob = (auctionedPlayersList) o;
                        ob.setAuctionedPlayers(auctionedPlayers);
                        ob.setRefresh(false);
                        ob.buyButtonClicked(false);
                        networkUtil.write(ob);
                    }

                    if (o instanceof buyPlayerFromAuction) {
                        buyPlayerFromAuction ob = (buyPlayerFromAuction) o;
                        int idx = 0;
                        for (Player player:auctionedPlayers) {
                            if (player.getName().equalsIgnoreCase(ob.getPlayer().getName())){
                                break;
                            }
                            idx++;
                        }
                        auctionedPlayers.remove(idx);
                        idx = 0;
                        System.out.println(ob.getPlayer().getName());
                        for (Club club : clubList) {
                            if (club.getName().equalsIgnoreCase(ob.getPlayer().getClub())) {
                                for (Player player : club.getPlayers()) {
                                    if (player.getName().equalsIgnoreCase(ob.getPlayer().getName())){
                                        club.getPlayers().remove(idx);
                                        club.refreshProperties();
                                        break;
                                    }
                                    idx++;
                                }
                            }
                        }
                        for (Club club : clubList) {
                            if (club.getName().equalsIgnoreCase(ob.getClubName())) {
                                ob.getPlayer().setClub(club.getName());
                                club.getPlayers().add(ob.getPlayer());
                                club.refreshProperties();
                                break;
                            }
                        }

                        auctionedPlayersList aucPlayer = new auctionedPlayersList();
                        aucPlayer.setAuctionedPlayers(auctionedPlayers);
                        aucPlayer.setModifiedClubList(clubList);
                        aucPlayer.setRefresh(true);
                        aucPlayer.buyButtonClicked(true);

                        for (String S : loggedInClubs.keySet()) {
                            loggedInClubs.get(S).write(aucPlayer);
                        }
                         // for starting thread in every club
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



