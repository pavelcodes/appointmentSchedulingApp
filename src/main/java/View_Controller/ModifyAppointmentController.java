package View_Controller;

import Model.Appointment;
import Model.BusinessHours;
import Utilities.AppointmentQuery;
import Utilities.CustomerQuery;
import javafx.collections.ObservableList;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static View_Controller.AddNewAppointmentController.getBusinessHours;
import static View_Controller.AppointmentDashboardController.modifyAppIndex;

public class ModifyAppointmentController implements Initializable {

    @FXML
    private AnchorPane searchCustomersField;

    @FXML
    private HBox root;

    @FXML
    private Text topBannerCustomers;

    @FXML
    private Button saveNewAppointment;

    @FXML
    private Button back;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label consultantNameLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label endDateLabel;

    @FXML
    private Label endTimeLabel;

    @FXML
    private Label appointmentTypeLabel;

    @FXML
    private ComboBox<String> customerNameCombo;

    @FXML
    private ComboBox<String> consultantCombo;

    @FXML
    private DatePicker startDateDP;

    @FXML
    private ComboBox<String> startTimeCombo;

    @FXML
    private DatePicker endDateDP;

    @FXML
    private ComboBox<String> endTimeCombo;

    @FXML
    private ComboBox<String> appointmentTypeCombo;

    @FXML
    private TextArea notesText;

    @FXML
    private Label notesLabel;

    @FXML
    private Label openLabel;

    @FXML
    private Label closeLabel;

    private final int appIndex = modifyAppIndex();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String businessHoursClose;
    String businessHoursOpen;
    String appointmentIdString;
    private int appointmentId;


    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/appointmentDashboard.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();

    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException, SQLException {

        String selectedCustomerName = customerNameCombo.getSelectionModel().getSelectedItem();
        String selectedProviderName = consultantCombo.getSelectionModel().getSelectedItem();
        String selectedAppointmentType = appointmentTypeCombo.getSelectionModel().getSelectedItem();
        String notes = notesText.getText();
        LocalDate selectedStartDate = startDateDP.getValue();
        LocalDate selectedEndDate = endDateDP.getValue();

        boolean checkFields = consultantCombo.getSelectionModel().isEmpty() || customerNameCombo.getSelectionModel().isEmpty() || startTimeCombo.getSelectionModel().isEmpty() || endTimeCombo.getSelectionModel().isEmpty() || appointmentTypeCombo.getSelectionModel().isEmpty() || startDateDP.getValue() == null || endDateDP.getValue() == null;
        if (checkFields) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("All Fields must be filled");
            alert.showAndWait();
            return;
        } else {
            LocalTime selectedStartTime = LocalTime.parse((startTimeCombo.getSelectionModel().getSelectedItem()));
            LocalTime selectedEndTime = LocalTime.parse((endTimeCombo.getSelectionModel().getSelectedItem()));
            LocalTime bhOpen = LocalTime.parse(businessHoursOpen);
            LocalTime bhClose = LocalTime.parse(businessHoursClose);
            boolean t = selectedStartTime.isBefore(bhOpen) || selectedStartTime.isAfter(bhClose)|| selectedEndTime.isAfter(bhClose) || selectedEndTime.isBefore(bhOpen);
            boolean d = selectedEndDate.isBefore(selectedStartDate) || selectedStartDate.isAfter(selectedEndDate);
            boolean sameTime= selectedStartTime.equals(selectedEndTime) && selectedEndDate.equals(selectedStartDate);
            ZoneId zoneId = ZoneId.systemDefault();//local timezone
            DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("UTC")); //fortmat to UTC

            ZonedDateTime startZDT = ZonedDateTime.of(selectedStartDate, selectedStartTime, zoneId);
            ZonedDateTime endZDT = ZonedDateTime.of(selectedEndDate, selectedEndTime, zoneId);

            String startOverlap = formatterUTC.format(startZDT);
            String endOverlap = formatterUTC.format(endZDT);
            appointmentIdString= String.valueOf(appointmentId);

            if (t) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment is made outside of business hours.\nThe business hours in your local time are between: " + businessHoursOpen + "\n"+ "and " + businessHoursClose);
                alert.showAndWait();
                return;
            }
            if (sameTime) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Appointment must be at least 15 minutes long");
                alert.showAndWait();
                return;
            }
            if (d) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("The appointment cannot have an end date before start date.");
                alert.showAndWait();
                return;
            }

            if (AppointmentQuery.appointmentOverlap(startOverlap, endOverlap,selectedProviderName, appointmentIdString)) {
                return;
            } else {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(appointmentId);
                    appointment.setCustomerName(selectedCustomerName);
                    appointment.setConsultantName(selectedProviderName);
                    appointment.setStartFull(formatterUTC.format(startZDT));
                    appointment.setEndFull(formatterUTC.format(endZDT));
                    appointment.setAppointmentType(selectedAppointmentType);
                    appointment.setNotes(notes);

                    AppointmentQuery.modifyAppointment(appointment);
                    URL url = getClass().getResource("/view/appointmentDashboard.fxml");
                    Parent root = FXMLLoader.load(url);
                    Scene mainScene = new Scene(root);
                    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_stage.setScene(mainScene);
                    app_stage.setTitle("Scheduling Application");
                    app_stage.show();
                }
            }
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getBusinessHours();
        businessHoursClose = BusinessHours.getCloseTime();
        businessHoursOpen = BusinessHours.getOpenTime();
        openLabel.setText(BusinessHours.openTime);
        closeLabel.setText(BusinessHours.closeTime);

        Appointment appointment1 = new Appointment();
        try {
            appointment1 = AppointmentQuery.getAllAppointments().get(appIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<String> customerNameList= null;
        try {
            customerNameList = CustomerQuery.customerList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerNameCombo.setItems(customerNameList);
        customerNameCombo.setValue(appointment1.getCustomerName());

        ObservableList<String> consultantNameList= null;
        try {
            consultantNameList = CustomerQuery.consultantList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        consultantCombo.setItems(consultantNameList);
        consultantCombo.setValue(appointment1.getConsultantName());
        appointmentTypeCombo.setItems(Appointment.appointmentTypes);
        appointmentTypeCombo.setValue(appointment1.getAppointmentType());
        startTimeCombo.setItems(Appointment.appointmentTimes);
        startTimeCombo.setValue(appointment1.getStartTimeFormatted());
        endTimeCombo.setItems(Appointment.appointmentTimes);
        endTimeCombo.setValue(appointment1.getEndTimeFormatted());
        startDateDP.setValue((LocalDate.parse(appointment1.getStartFull(),format)));
        endDateDP.setValue(LocalDate.parse(appointment1.getEndFull(),format));
        notesText.setWrapText(true);
        notesText.setText(appointment1.getNotes());
        appointmentId= appointment1.getAppointmentId();

    }
}
