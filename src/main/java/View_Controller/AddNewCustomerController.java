package View_Controller;

import Model.Customer;
import Utilities.CustomerQuery;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import static Model.Customer.customers;

public class AddNewCustomerController {

    @FXML
    private AnchorPane searchCustomersField;

    @FXML
    private HBox root;

    @FXML
    private Text topBannerCustomers;

    @FXML
    private Button saveNewCustomer;

    @FXML
    private Button back;

    @FXML
    private Label idLabel;

    @FXML
    private Label custNameLabel;

    @FXML
    private Label address1Label;

    @FXML
    private Label address2Label;

    @FXML
    private Label cityLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label postalCodeLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField custIDField;

    @FXML
    private TextField custNameField;

    @FXML
    private TextField address1Field;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField cityField;

    @FXML
    private  TextField countryField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField phoneField;

    @FXML
    private CheckBox smsReminder;

    private Integer reminder = 0;


    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        customers.clear();
        URL url = getClass().getResource("/view/customerDashboard.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();

    }



    @FXML
    void onActionSaveNewCustomer(ActionEvent event) throws SQLException, IOException {

        String custName = custNameField.getText();
        String address = address1Field.getText();
        String address2 = address2Field.getText();
        String phone = phoneField.getText();
        String city = cityField.getText();
        String country = countryField.getText();
        String postalCode = postalCodeField.getText();

        if (custName.matches("^[a-zA-Z\\s]*$")) {
            System.out.println("Its Valid name");
        } else{Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a valid name");
            alert.showAndWait();
            return;
        }
        if (city.matches("^[a-zA-Z\\s]*$")) {
            System.out.println("Its Valid city");
        } else{Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a valid city");
            alert.showAndWait();
            return;
        }
        if (country.matches("^[a-zA-Z\\s]*$")) {
            System.out.println("Its Valid country");
        } else{Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a valid country");
            alert.showAndWait();
            return;
        }

        if (phone.matches("^[+]+(?:[\\d]{11,11})$")) {
           System.out.println("Its Valid Number");
       } else{                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error");
           alert.setHeaderText("Phone enter a valid phone number");
           alert.showAndWait();
           return;
        }
       //source for regex pattern: https://stackoverflow.com/questions/578406/what-is-the-ultimate-postal-code-and-zip-regex
        if (postalCode.matches("(?i)^[a-z0-9][a-z0-9\\- ]{0,10}[a-z0-9]$")) {
            System.out.println("Its Valid Postal Code");
        } else{Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Phone enter a valid postal code number");
            alert.showAndWait();
            return;
        }
            //F:   entering nonexistent or invalid customer data exception
        boolean checkFields = custName.trim().isEmpty() || address.trim().isEmpty() || phone.trim().isEmpty() || city.trim().isEmpty() || country.trim().isEmpty(); //postal code can be empty because not every country has one
        if (checkFields){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("All Fields must be filled");
            alert.showAndWait();
            return;
        } else {

            Customer customer = new Customer();
            customer.setCustomerName(custName);
            customer.setAddress(address);
            customer.setAddress2(address2);
            customer.setPhone(phone);
            customer.setCity(city);
            customer.setCountry(country);
            customer.setPostalCode(postalCode);
            if (smsReminder.isSelected()){
                reminder = 1;
            }
            customer.setSmsActive(reminder);
            CustomerQuery.addCustomer(customer);
            customers.clear();
            URL url = getClass().getResource("/view/customerDashboard.fxml");
            Parent root = FXMLLoader.load(url);
            Scene mainScene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainScene);
            app_stage.setTitle("Scheduling Application");
            app_stage.show();
        }
    }
}
