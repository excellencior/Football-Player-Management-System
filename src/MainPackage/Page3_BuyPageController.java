package MainPackage;

import Database.Player;
import dto.auctionedPlayersList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;

public class Page3_BuyPageController {
    private MainPage mainPage;

    public void setMainPage(MainPage homePage) {
        this.mainPage = homePage;
    }

    @FXML
    private Label clubName;
    @FXML
    private TableView tableView;
    @FXML
    private ImageView image;

    ObservableList<PlayerPropertiesBuyPage> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn<PlayerPropertiesBuyPage, String> PlayerNameColumn;

    @FXML
    private TableColumn<PlayerPropertiesBuyPage, String> priceColumn;

    @FXML
    private TableColumn<PlayerPropertiesBuyPage, Button> BuyButtonColumn;

    @FXML
    private TableColumn<PlayerPropertiesBuyPage, Button> ViewButtonColumn;

    private boolean init = true;
    private boolean isRunning = true;

    public void initialize() {
        Thread t = new Thread(this::refresh);
        t.start();
    }

    private void refresh() {
        while (isRunning) {
            Platform.runLater(() -> {
                try {
                    if (mainPage.getAucPlayers().isRefresh()) {
                        data.clear();
                        if (mainPage.getAucPlayers().isBuyButtonClicked()) {
                            for (Player player : mainPage.getAucPlayers().getAuctionedPlayers()) {
                                data.add(new PlayerPropertiesBuyPage(player, mainPage.getNetworkUtil(), mainPage.getClubName()));
                            }
                        }
                        else {
                            for (Player player : mainPage.getAucPlayers().getAuctionedPlayers()) {
                                if (!mainPage.getClubName().equalsIgnoreCase(player.getClub())) {
                                    data.add(new PlayerPropertiesBuyPage(player, mainPage.getNetworkUtil(), mainPage.getClubName()));
                                }
                            }
                        }
                        mainPage.getAucPlayers().setRefresh(false);
                        mainPage.getAucPlayers().buyButtonClicked(false);
                    }
                    tableView.refresh();
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

    private void initializeColumns() {
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        BuyButtonColumn.setCellValueFactory(new PropertyValueFactory<>("BuyButton"));
        ViewButtonColumn.setCellValueFactory(new PropertyValueFactory<>("ViewButton"));
    }

        public void load(auctionedPlayersList ob) {
        if (init) {
            initializeColumns();
            init = false;
        }

        data.clear();

        for (Player player : ob.getAuctionedPlayers()) {
            if (!ob.getCurrentClubName().equalsIgnoreCase(player.getClub())) {
                data.add(new PlayerPropertiesBuyPage(player, mainPage.getNetworkUtil(), mainPage.getClubName()));
            }
        }

        tableView.setEditable(true);
        tableView.setItems(data);
    }


    @FXML
    void backToHomePage(ActionEvent event) throws IOException {
        isRunning = false;
        mainPage.showHomePage(mainPage.getClubName());
    }

    public void init() {
        clubName.setText(mainPage.getClubName());
        Image img = new Image(MainPage.class.getResourceAsStream("Page3.jpg"));
        image.setImage(img);
    }
}
