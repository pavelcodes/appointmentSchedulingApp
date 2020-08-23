package View_Controller;

import Utilities.DBConnection;
import Utilities.CustomerQuery;
import java.sql.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.twilio.converter.DateConverter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import static Main.Main.ACCOUNT_SID;
import static Main.Main.AUTH_TOKEN;
import static Utilities.AppointmentQuery.appointmentAlert15Min;
import static com.twilio.Twilio.*;
import static spark.Spark.*;


public class LoginController implements Initializable {



    @FXML
    private HBox root;

    @FXML
    private Label labelMain;

    @FXML
    private  TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button cancelLogin;

    @FXML
    private Button loginBTN;
    @FXML
    private Label loginResultLabel;

    @FXML
    private ComboBox<String> langComboBox;

    public static String currentUser;
    @FXML
    void onActionLogin(ActionEvent event) throws Exception {
        if (userNameField.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorTitle);
            alert.setHeaderText(blankUsername);
            alert.showAndWait();

        } else if (passwordField.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorTitle);
            alert.setHeaderText(blankPassword);
            alert.showAndWait();

        } else {
            String username = userNameField.getText();
            String password = passwordField.getText();
           try {
               if (check_login(username, password)) {
                   currentUser = username;
                   appointmentAlert15Min();
                   URL url = getClass().getResource("/view/mainScreen.fxml");
                   Parent root = FXMLLoader.load(url);
                   Scene mainScene = new Scene(root);
                   Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                   app_stage.setScene(mainScene);
                   app_stage.setTitle("Scheduling Application");
                   app_stage.show();
               }
           }
           catch (SQLException e) {
               e.printStackTrace();
           }
        }
    }





//check login credentials
    private boolean check_login(String username, String password) throws  IOException {
        String query= "Select * from user where username=? and password=?";
        try(PreparedStatement checkLoginPST = (PreparedStatement) DBConnection.connection.prepareStatement(query)) {
                checkLoginPST.setString(1, username);
                 checkLoginPST.setString(2, password);

            try (ResultSet rs = checkLoginPST.executeQuery()) {
                if (rs.next()) {
                    CustomerQuery.getAllUsers();
                    FileWriter userLogin = new FileWriter("userLogin.txt", true); // Log of users Requirement J
                    userLogin.write(System.getProperty("line.separator"));
                    userLogin.write(username + " Logged in Successfully at: " + (Instant.now()));
                    userLogin.close();
                    return true;
                } else {
                    loginResultLabel.setText(badLogin);
                    return false;
                }
            }
        catch (SQLException e) {
                    e.printStackTrace();
                }

        }   catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    private String blankPassword;
    private String blankUsername;
    private String errorTitle;
    private String badLogin;
    private String invalidLang= "Invalid Language";
    public static String mainTitle;



    @Override
    public void initialize(URL location, ResourceBundle rb) {
        try {
            DBConnection.startConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        rb = ResourceBundle.getBundle("Languages/rb", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("en") ||
                Locale.getDefault().getLanguage().equals("es")) {
            loginBTN.requestFocus();
            loginBTN.setDefaultButton(true);
            labelMain.setText(rb.getString("labelMain"));
            userNameField.setPromptText(rb.getString("username"));
            passwordField.setPromptText(rb.getString("password"));
            labelMain.setText(rb.getString("labelMain"));
            loginBTN.setText(rb.getString("loginBTN"));
            errorTitle = rb.getString("errorTitle");
            badLogin = rb.getString("badLogin");
            blankPassword = rb.getString("blankPassword");
            blankUsername = rb.getString("blankUsername");
            mainTitle = rb.getString("mainTitle");

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(errorTitle);
            alert.setHeaderText(invalidLang);
            alert.showAndWait();

        }
    }

}



