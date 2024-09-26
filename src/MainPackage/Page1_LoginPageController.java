package MainPackage;

import Database.SearchEngine;
import dto.LogInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Page1_LoginPageController {
    @FXML
    public TextField Password;
    @FXML
    public ImageView image;
    @FXML
    private TextField Username;

    private SearchEngine searchEngine;

    public void setSearchEngine(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    private MainPage mainPage;

    void setMainPage(MainPage mainPage) {
        this.mainPage = mainPage;
    }


    public void login(ActionEvent actionEvent) throws IOException {
        String username = Username.getText();
        String password = Password.getText();

        if (username.length() != 0 && password.length() != 0) {
            LogInfo logInfo = new LogInfo();
            logInfo.setUserName(username);
            logInfo.setPassword(password);
            mainPage.getNetworkUtil().write(logInfo);
        }
    }

    public void Reset(ActionEvent actionEvent) {
        Username.setText("");
        Password.setText("");
    }

    @FXML
    void exit(ActionEvent event) {
        mainPage.getStage().close();
    }

    // ------------------------------------------------------------------------------------------------------------------
    public void init() {
        Image img = new Image(MainPage.class.getResourceAsStream("FrontPage.jpg"));
        image.setImage(img);
    }
}
