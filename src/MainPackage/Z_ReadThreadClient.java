package MainPackage;

import dto.Alarm;
import dto.LogInfo;
import dto.auctionedPlayersList;
import javafx.application.Platform;

import java.io.IOException;

public class Z_ReadThreadClient implements Runnable {
    private Thread thr;
    private MainPage mainPage;

    public Z_ReadThreadClient(MainPage mainPage) {
        this.mainPage = mainPage;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = mainPage.getNetworkUtil().read();
                if (o instanceof LogInfo) {
                    LogInfo logInfo = (LogInfo) o;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (logInfo.getStatus()) {
                                System.out.println("success");
                                try {
                                    mainPage.setLogInfo(logInfo);
                                    mainPage.showHomePage(logInfo.getUserName());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                mainPage.showAlert();
                            }
                        }
                    });
                }
                if (o instanceof auctionedPlayersList) {
                    // showing the buy player page
                    Platform.runLater(() -> {
                        try {
                            auctionedPlayersList ob = (auctionedPlayersList) o;
                            if (ob.isBuyButtonClicked()) {
                                mainPage.modifySearchEngine(ob);
                            }
                            if (ob.isRefresh()) {
                                mainPage.setListBuyPlayerPage(ob);
                            } else {
                                mainPage.setListBuyPlayerPage(ob);
                                mainPage.showBuyPlayerPage(ob);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                }

                if (o instanceof Alarm) {
                    // showing the buy player page
                    Platform.runLater(() -> {
                        try {
                            mainPage.showMultipleClubError();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                mainPage.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
