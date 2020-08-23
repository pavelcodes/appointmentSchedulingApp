package View_Controller;

import Model.Customer;
import Utilities.CustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

import static Model.Customer.customers;
import static Model.Customer.getAllCustomers;
import static View_Controller.CustomerDashboardController.modifyCustIndex;


public class ModifyCustomerController implements Initializable {

    @FXML
    private AnchorPane searchCustomersField;

    @FXML
    private HBox root;

    @FXML
    private Text topBannerModCustomers;

    @FXML
    private Button saveModifiedCustomer;

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
    public TextField address1Field;

    @FXML
    private TextField address2Field;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TextField phoneField;

    @FXML
    private CheckBox smsReminder;
    private Integer reminder;

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
    private int custId;
    private final int custIndex = modifyCustIndex();
    private int countryId;
    private int cityId;
    private int addressId;

    @FXML
    void onActionSaveModifiedCustomer(ActionEvent event) throws IOException, SQLException {


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


//source for regex pattern: https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
        if (phone.matches("^[+]+(?:[\\d]{11,11})$")) {
            System.out.println("Its Valid Number");
        } else{Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Phone enter a phone number in this format: +12223334567");
            alert.showAndWait();
            return;
        }
//source for regex pattern: https://stackoverflow.com/questions/578406/what-is-the-ultimate-postal-code-and-zip-regex
        if (postalCode.matches("(?i)^[a-z0-9][a-z0-9\\- ]{0,10}[a-z0-9]$")) {
            System.out.println("Its Valid Number");
        } else{Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Phone enter a valid postal code number");
            alert.showAndWait();
            return;
        }




        //F:   entering nonexistent or invalid customer data exception
        boolean checkFields = custName.trim().isEmpty() || address.trim().isEmpty()  || phone.trim().isEmpty() || city.trim().isEmpty() || country.trim().isEmpty(); //postal codes can be empty because not every country has one
        if (checkFields) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("All Fields must be filled");
            alert.showAndWait();
        } else {
            Customer customer = new Customer();
            customer.setCustomerId(custId);
            customer.setCustomerName(custName);
            customer.setAddress(address);
            customer.setAddress2(address2);
            customer.setPhone(phone);
            customer.setCity(city);
            customer.setCountry(country);
            customer.setPostalCode(postalCode);
            customer.setCountryId(countryId);
            if (customer.getCountry() != original.getCountry()) {
                CustomerQuery.addCountry(customer);
            }
            if (customer.getCity() != original.getCity()) {
                CustomerQuery.addCity(customer);
            }
            if (customer.getAddressId() != original.getAddressId()) {
                CustomerQuery.addAddress(customer);
            }
            if (smsReminder.isSelected()){
                reminder = 1;
            }
            if (!smsReminder.isSelected()){
                reminder = 0;
            }
            customer.setSmsActive(reminder);
            CustomerQuery.addCustomer(customer);

            URL url = getClass().getResource("/view/customerDashboard.fxml");
            Parent root = FXMLLoader.load(url);
            Scene mainScene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainScene);
            app_stage.setTitle("Scheduling Application");
            app_stage.show();
        }
    }
    Customer original =new Customer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Customer customer1 = new Customer();
        customer1 = getAllCustomers().get(custIndex);
        address1Field.setText(customer1.getAddress());
        address2Field.setText(customer1.getAddress2());
        phoneField.setText(customer1.getPhone());
        cityField.setText(customer1.getCity());
        countryField.setText(customer1.getCountry());
        postalCodeField.setText(customer1.getPostalCode());
        custNameField.setText(customer1.getCustomerName());
        custId= customer1.getCustomerId();
        addressId=customer1.getAddressId();
        cityId=customer1.getCityId();
        countryId=customer1.getCountryId();
        reminder=customer1.getSmsActive();
        if (reminder == 1) {
            smsReminder.setSelected(true);
        }
        if (reminder == 0){
            smsReminder.setSelected(false);
        }
        original.setAddress(customer1.getAddress());
        original.setAddress2(customer1.getAddress2());
        original.setPhone(customer1.getPhone());
        original.setCity(customer1.getCity());
        original.setCountry(customer1.getCountry());
        original.setPostalCode(customer1.getPostalCode());
        original.setCustomerName(customer1.getCustomerName());
        original.setCustomerId(custId);
        original.setCityId(cityId);
        original.setCountryId(countryId);
        original.setAddressId(addressId);
        original.setSmsActive(customer1.getSmsActive());



    }
}
