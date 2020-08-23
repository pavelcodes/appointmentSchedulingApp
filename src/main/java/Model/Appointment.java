package Model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Appointment {
    private final IntegerProperty appointmentId;
    private final IntegerProperty customerId;
    private final StringProperty customerName;
    private final IntegerProperty consultantId;
    private final StringProperty consultantName;
    private final StringProperty appointmentType;
    private final StringProperty notes;
    private final StringProperty startFull; //full start time and date
    private final StringProperty endFull;   //full end time and date
    private final StringProperty startDateFormatted; //start date formatted to "MMMM d, yyyy"
    private final StringProperty endDateFormatted;   //end date formatted to "MMMM d, yyyy"
    private final StringProperty startTimeFormatted; //start time formatted to "HH:mm"
    private final StringProperty endTimeFormatted;   //end time formatted to "HH:mm"
    private final StringProperty startDTdisplay;
    private final StringProperty replyBody;
    private final StringProperty replyDate;


    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static ObservableList<String> appointmentTypes = FXCollections.observableArrayList("Intake","General","Expedited","Other");

    public static ObservableList<String> appointmentTimes = FXCollections.observableArrayList(
            "00:00","00:15","00:30","00:45","01:00","01:15","01:30","01:45","02:00","02:15","02:30","02:45","03:00","03:15","03:30","03:45","04:00","04:15","04:30","04:45","05:00","05:15","05:30","05:45","06:00","06:15","06:30","06:45","07:00","07:15","07:30","07:45","08:00","08:15","08:30","08:45","09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45","12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45","19:00","19:15","19:30","19:45","20:00","20:15","20:30","20:45","21:00","21:15","21:30","21:45","22:00","22:15","22:30","22:45","23:00","23:15","23:30","23:45","24:00");

    public String getStartTimeFormatted() {
        return startTimeFormatted.get();
    }

    public StringProperty startTimeFormattedProperty() {
        return startTimeFormatted;
    }

    public void setStartTimeFormatted(String startTimeFormatted) {
        this.startTimeFormatted.set(startTimeFormatted);
    }

    //appointment constructor
    public Appointment() {
        this.appointmentId = new SimpleIntegerProperty();
        this.appointmentType = new SimpleStringProperty();
        this.customerId = new SimpleIntegerProperty();
        this.startFull = new SimpleStringProperty();
        this.endFull = new SimpleStringProperty();
        this.customerName = new SimpleStringProperty();
        this.consultantId = new SimpleIntegerProperty();
        this.consultantName = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        this.startDateFormatted = new SimpleStringProperty();
        this.endDateFormatted = new SimpleStringProperty();
        this.startTimeFormatted = new SimpleStringProperty();
        this.endTimeFormatted= new SimpleStringProperty();
        this.startDTdisplay= new SimpleStringProperty();
        this.replyBody= new SimpleStringProperty();
        this.replyDate= new SimpleStringProperty();
    }

    //getters/setters


    public String getReplyBody() {
        return replyBody.get();
    }

    public StringProperty replyBodyProperty() {
        return replyBody;
    }

    public void setReplyBody(String replyBody) {
        this.replyBody.set(replyBody);
    }

    public String getReplyDate() {
        return replyDate.get();
    }

    public StringProperty replyDateProperty() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate.set(replyDate);
    }

    public String getStartDTdisplay() {
        return startDTdisplay.get();
    }

    public StringProperty startDTdisplayProperty() {
        return startDTdisplay;
    }

    public void setStartDTdisplay(String startDTdisplay) {
        this.startDTdisplay.set(startDTdisplay);
    }

    public String getEndTimeFormatted() {
        return endTimeFormatted.get();
    }

    public StringProperty endTimeFormattedProperty() {
        return endTimeFormatted;
    }

    public void setEndTimeFormatted(String endTimeFormatted) {
        this.endTimeFormatted.set(endTimeFormatted);
    }

    public String getStartDateFormatted() {
        return startDateFormatted.get();
    }

    public StringProperty startDateFormattedProperty() {

        return startDateFormatted;
    }

    public void setStartDateFormatted(String startDateFormatted) {
        this.startDateFormatted.set(startDateFormatted);
    }

    public String getEndDateFormatted() {
        return endDateFormatted.get();
    }

    public StringProperty endDateFormattedProperty() {
        return endDateFormatted;
    }

    public void setEndDateFormatted(String endDateFormatted) {
        this.endDateFormatted.set(endDateFormatted);
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set(appointmentId);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public int getConsultantId() {
        return consultantId.get();
    }

    public IntegerProperty consultantIdProperty() {
        return consultantId;
    }

    public void setConsultantId(int consultantId) {
        this.consultantId.set(consultantId);
    }

    public String getConsultantName() {
        return consultantName.get();
    }

    public StringProperty consultantNameProperty() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName.set(consultantName);
    }

    public String getAppointmentType() {
        return appointmentType.get();
    }

    public StringProperty appointmentTypeProperty() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType.set(appointmentType);
    }

    public String getStartFull() {
        return startFull.get();
    }

    public StringProperty startFullProperty() {
        return startFull;
    }

    public void setStartFull(String startFull) {
        this.startFull.set(startFull);
    }

    public String getEndFull() {
        return endFull.get();
    }

    public StringProperty endFullProperty() {
        return endFull;
    }

    public void setEndFull(String endFull) {
        this.endFull.set(endFull);
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public static ObservableList<Appointment> getAllAppointments() {
        return appointments;
    }

    public static void setAllAppointments(ObservableList<Appointment> appointments) {
        Appointment.appointments = appointments;
    }
}
