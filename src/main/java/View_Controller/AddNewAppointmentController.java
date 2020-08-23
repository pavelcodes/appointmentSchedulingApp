package View_Controller;


import Model.Appointment;
import Model.BusinessHours;
import Model.Customer;
import Model.smsValidatorModel;
import Utilities.AppointmentQuery;
import Utilities.CustomerQuery;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.type.PhoneNumber;
import javafx.collections.FXCollections;
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
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;


import static Model.smsValidatorModel.getAllSMSCustomers;
import static Model.smsValidatorModel.smsValidatorModelObservableList;
import static Utilities.CustomerQuery.*;
import static spark.Spark.get;
import static spark.Spark.post;


public class AddNewAppointmentController implements Initializable {

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
    private ComboBox<Customer> customerNameCombo;

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
    private CheckBox reminderCheckbox;
    @FXML
    private Label closeLabel;

    String businessHoursClose;
    String businessHoursOpen;
    Integer appointmentId;
    String customerPhone;
    int smsReminders;
    ObservableList<Customer> customerList;


    public void sendSMS() throws SQLException {
        ZoneId zoneId = ZoneId.systemDefault();//local timezone
        String selectedCustomerName = customerNameCombo.getSelectionModel().getSelectedItem().toString();
        LocalDate selectedStartDate = startDateDP.getValue();
        LocalTime selectedStartTime = LocalTime.parse((startTimeCombo.getSelectionModel().getSelectedItem()));
        ZonedDateTime startZDT = ZonedDateTime.of(selectedStartDate, selectedStartTime, zoneId);
        DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("UTC")); //fortmat to UTC
        String start = formatterUTC.format(startZDT);
        return;
    }

    public static void getBusinessHours() {

        ZoneId zoneId = ZoneId.systemDefault(); //systems zoneID
        LocalDate today = LocalDate.now(zoneId); //date on system with zoneID
        LocalTime openlt = LocalTime.of( 9 , 0 ); //time of 9:00 hours
        LocalTime closelt= LocalTime.of(17,0); //sets time to 17:00 hours
        ZonedDateTime zdtOpen = ZonedDateTime.of( today , openlt , ZoneId.of("UTC")); // zdt with systems current date, open hours at 9:00 in UTC
        ZonedDateTime zdtClose = ZonedDateTime.of( today , closelt , ZoneId.of("UTC")); // zdt with systems current date, close hours at 17:00 in UTC
        DateTimeFormatter formatToCurrent = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId); // formats the Date/time to HH:mm with systemZoneID
        String openTime= formatToCurrent.format(zdtOpen); // converts today's date with 9:00 UTC to local time zone
        String closeTime= formatToCurrent.format(zdtClose); // converts today's date with 17:00 UTC to local time zone
        BusinessHours.closeTime=closeTime;
        BusinessHours.openTime=openTime;
    }


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
    void onActionSaveNewAppointment(ActionEvent event) throws IOException, SQLException {

        String selectedCustomerName = customerNameCombo.getSelectionModel().getSelectedItem().getCustomerName();
        Integer customerId = customerNameCombo.getSelectionModel().getSelectedItem().getCustomerId();
        String selectedProviderName = consultantCombo.getSelectionModel().getSelectedItem();
        String selectedAppointmentType = appointmentTypeCombo.getSelectionModel().getSelectedItem();
        String notes = notesText.getText();

        System.out.println(customerNameCombo.getSelectionModel().getSelectedItem());
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
            DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("UTC"));
            DateTimeFormatter formatterZone = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of(String.valueOf(zoneId))); //fortmat to UTC
//fortmat to UTC

            ZonedDateTime startZDT = ZonedDateTime.of(selectedStartDate, selectedStartTime, zoneId);
            LocalDateTime ldt = LocalDateTime.of(selectedStartDate,selectedStartTime);
            ZonedDateTime endZDT = ZonedDateTime.of(selectedEndDate, selectedEndTime, zoneId);

            String startOverlap = formatterUTC.format(startZDT);
            String endOverlap = formatterUTC.format(endZDT);

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

            if (AppointmentQuery.appointmentOverlap(startOverlap, endOverlap, selectedProviderName)) {
                return;
            }


            final ObservableList<smsValidatorModel> customersForSMS = CustomerQuery.getCustomersForSMS(selectedCustomerName, customerId);
            for (int i = 0; i < customersForSMS.size(); i++) {
                customerPhone =customersForSMS.get(i).getPhone();
                smsReminders = customersForSMS.get(i).getSmsActive();
                appointmentId = customersForSMS.get(i).getAppointmentId();
            }
            if (smsReminders == 1){
                ScheduledExecutorService scheduler
                        = Executors.newSingleThreadScheduledExecutor();

                Runnable task = new Runnable() {
                    public void run() {
                        String date = formatterUTC.format(startZDT);
                        Message message = Message.creator(new PhoneNumber(customerPhone),
                                new PhoneNumber("+13092333586"),
                                "Hello " + selectedCustomerName + ". This is a reminder that you have an appointment on:  " + selectedStartDate + " " + "at " + selectedStartTime +" Please respond with a \"Yes\" to confirm. Or \"No\" to cancel").create();
                        String messageSid = message.getSid();
                        try {
                            addMessageId(messageSid, customerId);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                };
                ZonedDateTime currentInstant = Instant.now().atZone(zoneId);
                ZonedDateTime zonedDateTime = ldt.atZone(zoneId);
                Duration duration = Duration.between(currentInstant,zonedDateTime);
                long seconds = duration.getSeconds() -86400;
                scheduler.schedule(task, seconds, TimeUnit.SECONDS);
                scheduler.shutdown();

            }

                Appointment appointment = new Appointment();
                appointment.setCustomerName(selectedCustomerName);
                appointment.setConsultantName(selectedProviderName);
                appointment.setStartFull(formatterUTC.format(startZDT));
                appointment.setEndFull(formatterUTC.format(endZDT));
                appointment.setAppointmentType(selectedAppointmentType);
                appointment.setNotes(notes);
                appointment.setReplyBody("");
                appointment.setReplyDate("");
                AppointmentQuery.addAppointment(appointment);






            URL url = getClass().getResource("/view/appointmentDashboard.fxml");
            Parent root = FXMLLoader.load(url);
            Scene mainScene = new Scene(root);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.setScene(mainScene);
            app_stage.setTitle("Scheduling Application");
            app_stage.show();
            sendSMS();

        }
    }

    ObservableList<String> customerNameList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getBusinessHours();
        businessHoursClose = BusinessHours.getCloseTime();
        businessHoursOpen = BusinessHours.getOpenTime();
        openLabel.setText(BusinessHours.openTime);
        closeLabel.setText(BusinessHours.closeTime);
        try {
            customerList = getAllCustomersNameId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<String> a = reloadDatabase();
        customerNameList.setAll(a);

        customerNameCombo.getItems().addAll(customerList);

        Callback<ListView<Customer>, ListCell<Customer>> cellFactory = new Callback<ListView<Customer>, ListCell<Customer>>() {

            @Override
            public ListCell<Customer> call(ListView<Customer> l) {
                return new ListCell<Customer>() {

                    @Override
                    protected void updateItem(Customer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getCustomerId() + "    " + item.getCustomerName());
                        }
                    }
                } ;
            }
        };
        customerNameCombo.setButtonCell(cellFactory.call(null));
        customerNameCombo.setCellFactory(cellFactory);


        ObservableList<String> consultantNameList= null;
        try {
            consultantNameList = CustomerQuery.consultantList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        consultantCombo.setItems(consultantNameList);
        //sets appointment type combo box
        appointmentTypeCombo.setItems(Appointment.appointmentTypes);

        startTimeCombo.setItems(Appointment.appointmentTimes);
        endTimeCombo.setItems(Appointment.appointmentTimes);
    }
    private List<String> reloadDatabase() {

        List<String> custName = new ArrayList<>();


            for (int i = 0; i < customerList.size(); i++) {
                custName.add(String.valueOf(customerList.get(i).getCustomerName()));
                custName.add(String.valueOf(customerList.get(i).getCustomerId()));
            }
        return custName;
        }


}
