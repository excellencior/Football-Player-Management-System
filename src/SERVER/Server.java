package SERVER;

import Database.Club;
import Database.Player;
import Database.SearchEngine;
import util.NetworkUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private List<Player> playerList;
    private List<Club> clubList;

    private SearchEngine searchEngine;
    private HashMap<String, NetworkUtil> loggedInClubs;
    private List<Player> auctionedPlayers;
    private HashMap<String, String> userMap;
    private HashMap<String, Boolean> checkStatus;
    // ------------------------------------------------


    private ServerSocket serverSocket;

    Server() {
        try {
            playerList = new ArrayList<>();
            clubList = new ArrayList<>();
            startEngine();

            //-- Loading the user Map --
            userMap = new HashMap<>();
            checkStatus = new HashMap<>();
            for (Club club : clubList) {
                userMap.put(club.getName(), "admin");
                checkStatus.put(club.getName(), false); // initially all the clubs are offline
//                System.out.println(club.getName() + " " + userMap.get(club.getName()));
            }
            // ------------------------

            loggedInClubs = new HashMap<>();
            auctionedPlayers = new ArrayList<>();
            searchEngine = new SearchEngine(); // class search engine
            searchEngine.setClubList(clubList);

            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connects ...... ");
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        networkUtil.write(searchEngine);
        new Z_ReadThreadServer(checkStatus, userMap, loggedInClubs, auctionedPlayers, networkUtil, clubList);
    }

    public static void main(String[] args) {
        new Server();
    }

    public void startEngine() throws Exception {
        BufferedReader info = new BufferedReader(new FileReader("players.txt"));
        while (true) {
            String line = info.readLine();
            if (line == null) break;
            String[] playersInfo = line.split(",");
            Player p = new Player();
            p.setName(playersInfo[0]);
            p.setCountry(playersInfo[1]);
            p.setAge(Integer.parseInt(playersInfo[2]));
            p.setHeight(Double.parseDouble(playersInfo[3]));
            p.setClub(playersInfo[4]);
            p.setPosition(playersInfo[5]);
            p.setNumber(Integer.parseInt(playersInfo[6]));
            p.setWeeklySalary(Double.parseDouble(playersInfo[7]));
            playerList.add(p);
            // After adding this player the work is with managing club
            addPlayerToClub(p);
        }
        info.close();
    }

    public void stopEngine() throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter("players.txt"));
        for (Player p : playerList) {
            writer.write(p.getName() + "," + p.getCountry() + "," + p.getAge() + "," + p.getHeight() + "," +
                    p.getClub() + "," + p.getPosition() + "," + p.getNumber() + "," + p.getWeeklySalary());
            writer.write("\n");
        }
        writer.close();
    }

    public void addPlayerToClub(Player p) {
        boolean newClub = true;
        if (!clubList.isEmpty()) {
            for (Club club : clubList) {
                if (club.getName().equalsIgnoreCase(p.getClub())) {
                    club.setPlayer(p);
                    if (p.getWeeklySalary() > club.getMaxSalary()) club.setMaxSalary(p.getWeeklySalary());
                    if (p.getAge() > club.getMaxAge()) club.setMaxAge(p.getAge());
                    if (p.getHeight() > club.getMaxHeight()) club.setMaxHeight(p.getHeight());
                    newClub = false;
                }
            }
        }
        if (newClub) {
            Club ob = new Club();
            ob.setName(p.getClub());
            ob.setMaxSalary(p.getWeeklySalary());
            ob.setMaxHeight(p.getHeight());
            ob.setMaxAge(p.getAge());
            ob.setPlayer(p);
            clubList.add(ob);
        }
    }
}
