package View_Controller;

import Model.Customer;
import Utilities.CustomerQuery;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static Model.Customer.customers;
import static Model.Customer.getAllCustomers;


public class CustomerDashboardController implements Initializable {

    @FXML
    private AnchorPane searchCustomersField;

    @FXML
    private HBox root;

    @FXML
    private Text topBannerCustomers;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> custIDColumn;

    @FXML
    private TableColumn<Customer, String> custNameColumn;

    @FXML
    private TableColumn<Customer, String> custAddressColumn;

    @FXML
    private TableColumn<Customer, String> custPhoneColumn;

    @FXML
    private TableColumn<Customer, String> custAddressColumn2;

    @FXML
    private TableColumn<Customer, String> custCityColumn;

    @FXML
    private TableColumn<Customer, String> custCountryColumn;

    @FXML
    private Label UpcomingAppointmentsLabel;

    @FXML
    private Button addNewCustomer;

    @FXML
    private Button updateCustomer;

    @FXML
    private Button deleteCustomer;

    @FXML
    private Button back;

    @FXML
    private Button searchBTN;
    @FXML
    private TextField search;

    private static Customer customerModify;
    private static int modifyCustIndex;
    public static int modifyCustIndex() {return modifyCustIndex;}
    FilteredList filter= new FilteredList(customers,e->true);

    @FXML
    void onActionAddNewCustomer(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/addNewCustomer.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();

    }

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        customers.clear();
        URL url = getClass().getResource("/view/mainScreen.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();

    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        CustomerQuery.deleteCustomer(customer);
        reloadCustomers();

    }
    public void reloadCustomers()throws SQLException {
        customers.clear();
        CustomerQuery.getAllCustomers();
        customerTableView.setItems(getAllCustomers());
    }


    @FXML
    void search(KeyEvent event) {
        //Here I am using lambdas with a listener for the search bar, and testing the input values against a list of costumers names and their IDs;
        search.textProperty().addListener((observable, oldValue, newValue) ->{
            filter.setPredicate((Predicate<? super Customer>) (Customer customers) -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                } else if (customers.getCustomerName().contains(newValue)) {
                    return true;
                }
                else if (Integer.toString(customers.getCustomerId()).contains(newValue)) {
                    return true;
                }
                return false;

            });
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(customerTableView.comparatorProperty());
            customerTableView.setItems(sort);

    }


    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        customerModify = customerTableView.getSelectionModel().getSelectedItem();
        modifyCustIndex = getAllCustomers().indexOf(customerModify);
        if (customerModify == null) {
            return;
        } else {
            URL url = getClass().getResource("/view/modifyCustomer.fxml");
            Parent root = FXMLLoader.load(url);
            Scene mainScene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainScene);
            app_stage.setTitle("Scheduling Application");
            app_stage.show();

        }
    }
        @Override
        public void initialize (URL location, ResourceBundle resources){
            try {
                CustomerQuery.getAllCustomers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            custIDColumn.setCellValueFactory(cellData -> cellData.getValue().customerIdProperty().asObject());  //lambda expression to set cell data
            custNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
            custAddressColumn.setCellValueFactory(cellData -> (cellData.getValue().addressProperty().concat(" ").concat(cellData.getValue().address2Property())));
            custCityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
            custCountryColumn.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
            custPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
            customerTableView.setItems(customers);
            try {
                reloadCustomers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

