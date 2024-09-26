package MainPackage;

import Database.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;


public class PlayerPropertiesHomePage {
    private String playerName;
    private Button SellButton;
    private Button ViewButton;

    public PlayerPropertiesHomePage(Player player, NetworkUtil networkUtil) {
        this.playerName = player.getName();
        this.SellButton = new Button("Sell");
        SellButton.setCursor(Cursor.HAND);
        SellButton.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle("Price Tag");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SetPrice.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ioException) {
                System.out.println("Exception in playerInfo class");
            }
            priceController controller = loader.getController();
            controller.setNetworkUtilandPlayer(networkUtil, player);

            stage.setScene(new Scene(root, 368, 208));
            stage.show();
        });

        this.ViewButton = new Button("Details");
        ViewButton.setCursor(Cursor.HAND);

        ViewButton.setOnAction(e -> {
                    TableView<ViewButton> table = new TableView<>();
                    Stage stage = new Stage();
                    Scene scene = new Scene(new Group());
                    stage.setTitle("Table View Sample");
                    stage.setWidth(590);
                    stage.setHeight(400);

                    table.setEditable(true);

                    TableColumn firstNameCol = new TableColumn("Properties");
                    firstNameCol.setMinWidth(200);
                    firstNameCol.setCellValueFactory(
                            new PropertyValueFactory<>("col1"));

                    TableColumn lastNameCol = new TableColumn("Player Description");
                    lastNameCol.setMinWidth(350);
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Button getSellButton() {
        return SellButton;
    }

    public void setSellButton(Button sellButton) {
        SellButton = sellButton;
    }

    public Button getViewButton() {
        return ViewButton;
    }

    public void setViewButton(Button viewButton) {
        ViewButton = viewButton;
    }
}
