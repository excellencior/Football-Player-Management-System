package MainPackage;

import Database.Player;
import dto.buyPlayerFromAuction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;


public class PlayerPropertiesBuyPage {
    private String playerName;
    private String price;
    private Button BuyButton;
    private Button ViewButton;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Button getBuyButton() {
        return BuyButton;
    }

    public void setBuyButton(Button buyButton) {
        BuyButton = buyButton;
    }

    public Button getViewButton() {
        return ViewButton;
    }

    public void setViewButton(Button viewButton) {
        ViewButton = viewButton;
    }

    public PlayerPropertiesBuyPage(Player player, NetworkUtil networkUtil, String clubName) {
        this.playerName = player.getName();
        this.price = player.getPrice();
        this.BuyButton = new Button("Buy");
        BuyButton.setCursor(Cursor.HAND);
        BuyButton.setOnAction(k -> {
            buyPlayerFromAuction ob = new buyPlayerFromAuction();
            ob.setClubName(clubName);
            ob.setPlayer(player);
            ob.setBuyButtonClicked(true);
            try {
                networkUtil.write(ob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.ViewButton = new Button("Details");
        ViewButton.setCursor(Cursor.HAND);

        ViewButton.setOnAction(e -> {
                    TableView<ViewButton> table = new TableView<>();
                    Stage stage = new Stage();
                    Scene scene = new Scene(new Group());
                    stage.setTitle("Table View Sample");
                    stage.setWidth(440);
                    stage.setHeight(400);

                    table.setEditable(true);

                    TableColumn firstNameCol = new TableColumn("Properties");
                    firstNameCol.setMinWidth(200);
                    firstNameCol.setCellValueFactory(
                            new PropertyValueFactory<>("col1"));

                    TableColumn lastNameCol = new TableColumn("Player Description");
                    lastNameCol.setMinWidth(200);
                    lastNameCol.setCellValueFactory(
                            new PropertyValueFactory<>("col2"));

                    ObservableList data = FXCollections.observableArrayList(new ViewButton("Name", player.getName()),
                            new ViewButton("Country", player.getCountry()),
                            new ViewButton("Age", String.valueOf(player.getAge())),
                            new ViewButton("Height", String.valueOf(player.getHeight())),
                            new ViewButton("Club", String.valueOf(player.getClub())),
                            new ViewButton("Position", String.valueOf(player.getPosition())),
                            new ViewButton("Number", String.valueOf(player.getNumber())),
                            new ViewButton("Weekly Salary", String.valueOf(player.getWeeklySalary()))
                    );
                    table.setItems(data);
                    table.getColumns().addAll(firstNameCol, lastNameCol);

                    final VBox vbox = new VBox();
                    vbox.setSpacing(5);
                    vbox.setPadding(new Insets(10, 0, 0, 10));
                    vbox.getChildren().addAll(table);

                    ((Group) scene.getRoot()).getChildren().addAll(vbox);
                    scene.getStylesheets().add(getClass().getResource("Table.css").toExternalForm());


                    stage.setScene(scene);
                    stage.show();
                }
        );
    }

}
