package MainPackage;

import Database.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.NetworkUtil;
import java.io.IOException;

public class priceController {

    private NetworkUtil networkUtil;
    private Player player;
    @FXML
    private TextField price;

    @FXML
    void savePrice(ActionEvent event) throws IOException {
        String fixedPrice = price.getText();
        player.setPrice("$ " + fixedPrice);
        networkUtil.write(player);
    }

    public void setNetworkUtilandPlayer(NetworkUtil networkUtil, Player player) {
        this.networkUtil = networkUtil;
        this.player = player;
    }
}