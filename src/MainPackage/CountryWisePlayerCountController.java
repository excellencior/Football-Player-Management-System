package MainPackage;

import Database.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CountryWisePlayerCountController {
    @FXML
    private TableView tableView;

    ObservableList<CountryProperties> data = FXCollections.observableArrayList();

    @FXML
    private TableColumn<CountryProperties, String> PlayerNameColumn;

    @FXML
    private TableColumn<CountryProperties, String> PlayerCountColumn;

    private boolean init = true;

    private void initializeColumns() {

//        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        PlayerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        PlayerCountColumn.setCellValueFactory(new PropertyValueFactory<>("playerCount"));

//        tableView.getColumns().addAll(PlayerNameColumn, SellButtonColumn, ViewButtonColumn);
    }

    public void load(List<Player> searchResult) {
        if (init) {
            initializeColumns();
            init = false;
        }
//        initializeColumns();

        data.clear();

        for (Player player : searchResult) {
            data.add(new CountryProperties(player));
        }
        searchResult.clear();

        tableView.setEditable(true);
        tableView.setItems(data);
    }
}
