package MainPackage;

import Database.Player;
import Database.SearchEngine;
import dto.Alarm;
import dto.LogInfo;
import dto.auctionedPlayersList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.NetworkUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class MainPage extends Application {

    private NetworkUtil networkUtil;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    // ---------- login info -------------
    private LogInfo logInfo;

    public LogInfo getLogInfo() {
        return logInfo;
    }
    // ------------------------------------

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    private SearchEngine searchEngine;

    private String clubName;

    public String getClubName() {
        return clubName;
    }

    // ------------ List for buy player page -------------
    private auctionedPlayersList ob;
    public void setListBuyPlayerPage(auctionedPlayersList ob) {
        this.ob = ob;
    }
    public auctionedPlayersList getAucPlayers() {
        return ob;
    }
    // ----------------------------------------------------------------

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        // ----------- Connect to server ---------------------
        connectToServer();
        showLoginPage();
        stage.setOnCloseRequest(e -> {
            Alarm ob = new Alarm();
            ob.setClubName(clubName);
            try {
                networkUtil.write(ob);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    // ------------------------------------------------------------------------------------------------------

    public void showLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Page1_LoginPage.fxml"));
        Parent root = loader.load();

        Page1_LoginPageController controller = loader.getController();
        // --------------------------------------------------------
        controller.init();
        controller.setMainPage(this);
        controller.setSearchEngine(searchEngine);

        stage.setTitle("My Login Page");
        stage.setScene(new Scene(root, 948, 543));
        stage.show();
    }

    public void showHomePage(String clubName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Page2_HomePage.fxml"));
        Parent root = loader.load();

        Page2_HomePageController controller = loader.getController();
        controller.setMainPage(this);
        controller.setSearchEngine(searchEngine);
        controller.showClubName(clubName);
        this.clubName = clubName;

        stage.setTitle("Home Page");
        stage.setScene(new Scene(root, 948, 543));
        stage.show();
    }

    // to keep the loaderShowPlayerPage constant
    public void showBuyPlayerPage(auctionedPlayersList ob) throws IOException {
        FXMLLoader loaderShowPlayerPage = new FXMLLoader();
        loaderShowPlayerPage.setLocation(getClass().getResource("Page3_BuyPlayerPage.fxml"));
        Parent root = loaderShowPlayerPage.load();

        Page3_BuyPageController controller = loaderShowPlayerPage.getController();
        controller.setMainPage(this);
        controller.init();
        controller.load(ob);
//        controllerReference = controller.getReference(); // saving the previous controller reference

        stage.setTitle("Buy Players");
        stage.setScene(new Scene(root, 948, 543));
        stage.show();
    }

    public void loadCountryWisePlayerCountList(List<Player> searchResult) throws IOException {
        Stage listStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CountryWisePlayerCount.fxml"));
        Parent root = loader.load();

        CountryWisePlayerCountController controller = loader.getController();
        controller.load(searchResult);

        listStage.setTitle("Country wise Player Count");
        listStage.setScene(new Scene(root, 600, 400));
        listStage.show();
    }

    public void viewTotalSalary(double totalSalary) {
        Stage showSalary = new Stage();
        showSalary.setTitle("Total Yearly Salary");
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#1c7a94"), CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(root, 400, 200);
        showSalary.setScene(scene);

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(4);
        Text hi = new Text("CLUB's TOTAL YEARLY SALARY\n\n" + "Total Yearly Salary : " + df.format(totalSalary));
        hi.setFont(Font.font("HoloLens MDL2 Assets", FontWeight.BOLD, 25));
        hi.setFill(Color.WHITE);
        root.getChildren().add(hi);
        showSalary.show();
    }

    public void connectToServer() throws IOException, ClassNotFoundException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        searchEngine = (SearchEngine) networkUtil.read();
        new Z_ReadThreadClient(this);
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }
    public void showMultipleClubError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Already Logged In");
        alert.setHeaderText("Already Logged In");
        alert.setContentText("The username and password you provided is already being used by another client");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void modifySearchEngine(auctionedPlayersList ob) {
        searchEngine.modifyClubs(ob);
    }
}
