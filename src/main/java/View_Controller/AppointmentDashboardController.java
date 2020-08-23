package View_Controller;

import Model.Appointment;
import Utilities.AppointmentQuery;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static Model.Appointment.appointments;
import static Model.Appointment.getAllAppointments;

public class AppointmentDashboardController implements Initializable {

    @FXML
    private AnchorPane searchCustomersField;

    @FXML
    private HBox root;

    @FXML
    private Text topBannerAppointments;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> custNameColumn;

    @FXML
    private TableColumn<Appointment, String> startColumn;

    @FXML
    private TableColumn<Appointment, String> endColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, String> consultantNameColumn;

    @FXML
    private TableColumn<Appointment, String> dateColumn;

    @FXML
    private Label UpcomingAppointmentsLabel;

    @FXML
    private Button addNewAppointment;

    @FXML
    private Button modifyAppointment;

    @FXML
    private Button deleteCustomer;

    @FXML
    private Button back;

    @FXML
    private RadioButton sortByAllRadio;

    @FXML
    private RadioButton sortByMonthRadio;

    @FXML
    private RadioButton sortByWeekRadio;

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static Appointment appointmentModify;
    private static int modifyAppIndex;
    public static int modifyAppIndex() {return modifyAppIndex;}
    @FXML
    void onActionAddNewAppointment(ActionEvent event) throws IOException,SQLException {
        reloadAppointmentsDash();
        URL url = getClass().getResource("/view/addNewAppointment.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();

    }

    @FXML
    void onActionBack(ActionEvent event) throws IOException, SQLException{
            reloadAppointmentsDash();
            URL url = getClass().getResource("/view/mainScreen.fxml");
             Parent root = FXMLLoader.load(url);
            Scene mainScene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainScene);
            app_stage.setTitle("Scheduling Application");
            app_stage.show();
    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
       if (appointment !=null) {
           AppointmentQuery.deleteAppointment(appointment);
           reloadAppointmentsDash();
           sortByAllRadio.setSelected(true);
       }
    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        appointmentModify = appointmentTableView.getSelectionModel().getSelectedItem();
        modifyAppIndex = getAllAppointments().indexOf(appointmentModify);
        if (appointmentModify == null) {
            return;}
        URL url = getClass().getResource("/view/modifyAppointment.fxml");
         Parent root = FXMLLoader.load(url);
            Scene mainScene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainScene);
            app_stage.setTitle("Scheduling Application");
            app_stage.show();
    }

    @FXML
    void onActionSortByAll(ActionEvent event) throws SQLException {
        CustomerQuery.getAllCustomers();
        appointmentTableView.setItems(appointments);
    }

    @FXML
    void onActionSortByMonth(ActionEvent event) {
        //CustomerQuery.getAllCustomers();
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime month = currentDate.plusMonths(1);

        FilteredList filter = new FilteredList<>(appointments,e->true);
        filter.setPredicate((Predicate<? super Appointment>) (Appointment appointments) -> {

            LocalDateTime startTime = LocalDateTime.parse(appointments.getStartFull(), format);

            return startTime.isAfter(currentDate.minusDays(1)) && startTime.isBefore(month);
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(appointmentTableView.comparatorProperty());
        appointmentTableView.setItems(sort);
    }

    @FXML
    void onActionSortByWeek(ActionEvent event) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime week = currentDate.plusWeeks(1);

        FilteredList filter = new FilteredList<>(appointments,e->true);
        filter.setPredicate((Predicate<? super Appointment>) (Appointment appointments) -> {

            LocalDateTime startTime = LocalDateTime.parse(appointments.getStartFull(), format);

            return startTime.isAfter(currentDate.minusDays(1)) && startTime.isBefore(week);
        });
        SortedList sort = new SortedList(filter);
        sort.comparatorProperty().bind(appointmentTableView.comparatorProperty());
        appointmentTableView.setItems(sort);

    }

    public void reloadAppointmentsDash() throws SQLException {
        appointments.clear();
        AppointmentQuery.getAllAppointments();
        appointmentTableView.setItems(getAllAppointments());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AppointmentQuery.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        custNameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeFormattedProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeFormattedProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().startDTdisplayProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().appointmentTypeProperty());
        consultantNameColumn.setCellValueFactory(cellData -> cellData.getValue().consultantNameProperty());
        appointmentTableView.setItems(appointments);
        try {
            reloadAppointmentsDash();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


