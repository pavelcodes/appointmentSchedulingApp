package Main;

import Utilities.DBConnection;
import com.twilio.Twilio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;


import static Utilities.SmsApp.sms;
import static View_Controller.LoginController.mainTitle;

public class Main extends Application {

    public static String ACCOUNT_SID = "";
    public static String AUTH_TOKEN = "";

    public Main() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/view/login.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle(mainTitle);
        primaryStage.setScene(new Scene(root, 261, 232));
        primaryStage.show();
        root.requestFocus();
    }

    public static void main(String[] args) throws SQLException, IOException {
        GetTwilioLogin loginAccount = new GetTwilioLogin();
        ACCOUNT_SID = loginAccount.getACCOUNT_SID();
        AUTH_TOKEN = loginAccount.getAUTH_TOKEN();
        Twilio.init(
                ACCOUNT_SID, AUTH_TOKEN);
        sms();
        launch(args);
        DBConnection.closeConnection();

    }

    public static class GetTwilioLogin {
        String ACCOUNT_SID = "";
        String AUTH_TOKEN = "";
        InputStream inputStream;

        public String getACCOUNT_SID() throws IOException {
            try {
                Properties properties = new Properties();
                String propFileName = "twilioLogin.properties";
                inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                if (inputStream != null) {
                    properties.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }
                ACCOUNT_SID = properties.getProperty("username");
                AUTH_TOKEN = properties.getProperty("password");
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            } finally {
                inputStream.close();
            }
            return ACCOUNT_SID;
        }
        public String getAUTH_TOKEN() throws IOException {
            try {
                Properties properties = new Properties();
                String propFileName = "twilioLogin.properties";
                inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                if (inputStream != null) {
                    properties.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }
                AUTH_TOKEN = properties.getProperty("password");
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            } finally {
                inputStream.close();
            }
            return AUTH_TOKEN;
        }
    }
}

