package View_Controller;

import Model.Appointment;
import Utilities.AppointmentQuery;
import Utilities.DBConnection;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static Model.Appointment.appointments;
import static Model.Appointment.getAllAppointments;
import static Utilities.AppointmentQuery.appointmentAlert15Min;
import static View_Controller.LoginController.*;

public class MainScreenController<formatter> implements Initializable {

    @FXML
    private HBox root;

    @FXML
    private TableView<Appointment> upcomingAppointmentsTableView;

    @FXML
    private TableColumn<Appointment, String> custNameColumn;

    @FXML
    private TableColumn<Appointment, String> startColumn;

    @FXML
    private TableColumn<Appointment, String> endColumn;

    @FXML
    private TableColumn<Appointment, String> dateColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, String> consultantNameColumn;

    @FXML
    private Label UpcomingAppointmentsLabel;

    @FXML
    private Button manageCustomers;

    @FXML
    private Button manageAppointments;

    @FXML
    private Button generateReports;

    @FXML
    private Button logOff;

    @FXML
    private Label topBannerLabel;
    private static Appointment appointmentModify;
    private static int modifyAppIndex;

    public static int modifyAppIndex() {
        return modifyAppIndex;
    }


    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    void onActionGenerateReports(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/reportsDashboard.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();
    }

    @FXML
    void onActionLogOff(ActionEvent event) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Scheduling Application");
        alert.setHeaderText("Are you sure you want to log off?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            DBConnection.closeConnection();
            URL url = getClass().getResource("/view/login.fxml");
            Parent root = FXMLLoader.load(url);
            Scene mainScene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainScene);
            app_stage.setTitle(mainTitle);
            app_stage.show();
        }
    }

    @FXML
    void onActionAppointmentDash(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/appointmentDashboard.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();

    }

    @FXML
    void onActionCustomerDash(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/customerDashboard.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();

    }


    public void reloadAppointmentsUpcoming() throws SQLException{
        appointments.clear();
        upcomingAppointmentsTableView.setItems(getAllAppointments());
        AppointmentQuery.getAllAppointments();
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime day = currentDate.plusHours(36);

        FilteredList filter = new FilteredList<>(appointments, e -> true);
        filter.setPredicate((Predicate<? super Appointment>) (Appointment appointments) -> {

            LocalDateTime startTime = LocalDateTime.parse(appointments.getStartFull(), format);

            return startTime.isAfter(currentDate.minusDays(1)) && startTime.isBefore(day);
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(upcomingAppointmentsTableView.comparatorProperty());
        upcomingAppointmentsTableView.setItems(sort);
    }

    @FXML
    void modifyAppointmentClick(MouseEvent event) throws IOException {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topBannerLabel.setText(currentUser);
        //Here I'm using a  lambda expression to populate cell values in table view
        custNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeFormattedProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeFormattedProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().startDTdisplayProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().appointmentTypeProperty());
        consultantNameColumn.setCellValueFactory(cellData -> cellData.getValue().consultantNameProperty());

        try {
            reloadAppointmentsUpcoming();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}



