package MainPackage;

import Database.Player;
import Database.SearchEngine;
import dto.Alarm;
import dto.auctionedPlayersList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;

public class Page2_HomePageController {

    private List<Player> searchResult;
    private List<Player> primitivePlayerList;
    //-------------- Setting Club Name --------
    private MainPage mainPage;
    private SearchEngine searchEngine;

    public void setMainPage(MainPage mainPage) {
        this.mainPage = mainPage;
    }

    public void setSearchEngine(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    // ----------search by buttons---------
    @FXML
    private TextField searchInput;

    // ---------------- Buttons ---------------
    private boolean[] clickedButton = new boolean[4];
    private boolean isRunning = true; // ------------ Thread controller -------------
    // --------------------------------
    // -------------------- Labels ------------
    @FXML
    private Label IncorrectInput; // It is changed in the search method for incorrect input

    private String clubName;
    @FXML
    private Label labelClubName;

    @FXML
    void reloadPrimitivePlayerList(MouseEvent event) {
        searchResult = searchEngine.getAllPlayers(clubName);
        load();
    }

    public void showClubName(String clubName) {
        this.clubName = clubName; // Setting clubName
        labelClubName.setText(clubName);
        searchResult = searchEngine.getAllPlayers(clubName);
        load();
    }
    // ----------------------------------------

    // ------------------------------- Button press events -------------------
    public void searchByPlayerNameSetActive(ActionEvent actionEvent) {
        clickedButton[0] = true;
        for (int i = 0; i < 4; i++) {
            if (i != 0) clickedButton[i] = false;
        }
    }

    @FXML
    void searchByCountryNameSetActive(ActionEvent event) {
        clickedButton[1] = true;
        for (int i = 0; i < 4; i++) {
            if (i != 1) clickedButton[i] = false;
        }
    }

    @FXML
    void searchByPositionSetActive(ActionEvent event) {
        clickedButton[2] = true;
        for (int i = 0; i < 4; i++) {
            if (i != 2) clickedButton[i] = false;
        }
    }

    @FXML
    void searchBySalaryRangeSetActive(ActionEvent event) {
        clickedButton[3] = true;
        for (int i = 0; i < 4; i++) {
            if (i != 3) clickedButton[i] = false;
        }
    }

    @FXML
    void searchByCountryWisePlayerCountSetActive(ActionEvent event) throws IOException {
        searchResult = searchEngine.searchByCountryWisePlayerCount(clubName);
        if (searchResult.isEmpty()) IncorrectInput.setText("Empty Club");
        else {
            mainPage.loadCountryWisePlayerCountList(searchResult);
        }
    }

    @FXML
    void searchByMaxAgeSetActive(ActionEvent event) {
        IncorrectInput.setText(null);
        searchResult = searchEngine.playerWithTheMaxAge(clubName);
        if (searchResult.isEmpty()) IncorrectInput.setText("Empty Club");
        else {
            load();
        }
    }

    @FXML
    void searchByMaxHeightSetActive(ActionEvent event) {
        IncorrectInput.setText(null);
        searchResult = searchEngine.playerWithTheMaxHeight(clubName);
        if (searchResult.isEmpty()) IncorrectInput.setText("Empty Club");
        else {
            load();
        }
    }

    @FXML
    void searchByMaxSalarySetActive(ActionEvent event) {
        IncorrectInput.setText(null);
        searchResult = searchEngine.playerWithTheMaxSalary(clubName);
        if (searchResult.isEmpty()) IncorrectInput.setText("Empty club");
        else {
            load();
        }
    }

    @FXML
    void searchByTotalYearlySalarySetActive(ActionEvent event) {
        double totalSalary = searchEngine.totalYearlySalary(clubName);
        mainPage.viewTotalSalary(totalSalary);
    }

    @FXML
    void buyPlayers(ActionEvent event) throws IOException {
        auctionedPlayersList ob = new auctionedPlayersList();
        ob.setCurrentClubName(clubName);
        ob.setRefresh(false);
        ob.buyButtonClicked(false);
        isRunning = false;
        mainPage.getNetworkUtil().write(ob);
    }

    // -----------------------------------------------------------------------

    public void Search(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER) && clickedButton[0]) {
            String playerName = searchInput.getText();
            searchResult = searchEngine.searchByPlayerName(clubName, playerName);
            if (searchResult.isEmpty()) IncorrectInput.setText("No Player Found");
            else {
                load();
            }
        } else if (keyEvent.getCode().equals(KeyCode.ENTER) && clickedButton[1]) {
            String countryName = searchInput.getText();
            searchResult = searchEngine.searchByCountryName(clubName, countryName);
            if (searchResult.isEmpty()) IncorrectInput.setText("No Player Found");
            else {
                load();
            }
        } else if (keyEvent.getCode().equals(KeyCode.ENTER) && clickedButton[2]) {
            String position = searchInput.getText();
            searchResult = searchEngine.searchByPosition(clubName, position);
            if (searchResult.isEmpty()) IncorrectInput.setText("No Player Found");
            else {
                load();
            }
        } else if (keyEvent.getCode().equals(KeyCode.ENTER) && clickedButton[3]) {
            String[] salaryRange = searchInput.getText().split("-");
            searchResult = searchEngine.searchBySalaryRange(clubName,
                    Double.parseDouble(salaryRange[0]), Double.parseDouble(salaryRange[1]));
            if (searchResult.isEmpty()) IncorrectInput.setText("No Player Found");
            else {
                load();
            }
        }
    }

    public void clearSearch(ActionEvent actionEvent) {
        searchInput.setText(null);
        IncorrectInput.setText(null);
        searchResult.clear(); // clear the search results
    }

    @FXML
    void Logout(ActionEvent event) throws IOException {
        Alarm ob = new Alarm();
        ob.setClubName(clubName);
        mainPage.getNetworkUtil().write(ob);
        mainPage.showLoginPage();
        isRunning = false;
    }

    // ----------------------------- Table view --------------------------
    @FXML
    private TableView tableView;

    ObservableList<PlayerPropertiesHomePage> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn<PlayerPropertiesHomePage, String> PlayerNameColumn;

    @FXML
    private TableColumn<PlayerPropertiesHomePage, Button> SellButtonColumn;

    @FXML
    private TableColumn<PlayerPropertiesHomePage, Button> ViewButtonColumn;

    private boolean init = true;

    private void initializeColumns() {

        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        SellButtonColumn.setCellValueFactory(new PropertyValueFactory<>("SellButton"));
        ViewButtonColumn.setCellValueFactory(new PropertyValueFactory<>("ViewButton"));
    }

    public void load() {
        if (init) {
            initializeColumns();
            init = false;
        }

        data.clear();

        for (Player player : searchResult) {
            data.add(new PlayerPropertiesHomePage(player, mainPage.getNetworkUtil()));
        }
        searchResult.clear();

        tableView.setEditable(true);
        tableView.setItems(data);
    }

    // ----------------- Refreshing table view dynamically -------------------------

    public void initialize() {
        Thread t = new Thread(this::refresh);
        t.start();
    }

    private void refresh() {
        while (isRunning) {
            Platform.runLater(() -> {
                try {
                    if (mainPage != null && mainPage.getAucPlayers() != null) {

                        if (mainPage.getAucPlayers().isRefresh()) {
                            searchResult = searchEngine.getAllPlayers(clubName);
                            data.clear();
                            for (Player player : searchResult) {
                                data.add(new PlayerPropertiesHomePage(player, mainPage.getNetworkUtil()));
                            }
                            searchResult.clear();
                            mainPage.getAucPlayers().setRefresh(false);
                        }
                        tableView.refresh();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
