package View_Controller;

import Model.Appointment;
import Utilities.AppointmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Model.Appointment.appointments;
import static Model.Appointment.getAllAppointments;

public class Report1 implements Initializable {

    @FXML
    private AnchorPane searchCustomersField;

    @FXML
    private HBox root;

    @FXML
    private Text topBannerCustomerDash;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String>  consultantNameColumn;

    @FXML
    private TableColumn<Appointment, String>  startColumn;

    @FXML
    private TableColumn<Appointment, String>  endColumn;

    @FXML
    private TableColumn<Appointment, String> dateColumn;

    @FXML
    private TableColumn<Appointment, String>  typeColumn;

    @FXML
    private Label UpcomingAppointmentsLabel;

    @FXML
    private Button back;

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/reportsDashboard.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();
    }
    public void reloadAppointments() throws SQLException{
        appointments.clear();
        AppointmentQuery.getAllAppointments();
        appointmentTableView.setItems(getAllAppointments());
        appointmentTableView.getSortOrder().addAll(consultantNameColumn);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AppointmentQuery.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeFormattedProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeFormattedProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().startDTdisplayProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().appointmentTypeProperty());
        consultantNameColumn.setCellValueFactory(cellData -> cellData.getValue().consultantNameProperty());
        appointmentTableView.setItems(appointments);
        try {
            reloadAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
