package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class smsValidatorModel {
    private SimpleStringProperty custName;
    private SimpleIntegerProperty appointmentId;
    private SimpleIntegerProperty smsActive;
    private SimpleStringProperty phone;
    private SimpleIntegerProperty customerId;
    private final StringProperty startFull;
    private SimpleStringProperty replyBody;
    private SimpleStringProperty replyDate;
    private SimpleStringProperty smsId;

    public static ObservableList<smsValidatorModel> smsValidatorModelObservableList = FXCollections.observableArrayList();
    public static ObservableList<smsValidatorModel> getAllSMSCustomers() {
        return smsValidatorModelObservableList;
    }

    public smsValidatorModel() {
        this.startFull = new SimpleStringProperty();
        this.appointmentId = new SimpleIntegerProperty();
        this.custName = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.smsActive = new SimpleIntegerProperty();
        this.customerId = new SimpleIntegerProperty();
        this.replyBody = new SimpleStringProperty();
        this.replyDate = new SimpleStringProperty();
        this.smsId = new SimpleStringProperty();
    }

    public String getSmsId() {
        return smsId.get();
    }

    public SimpleStringProperty smsIdProperty() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId.set(smsId);
    }

    public int getSmsActive() {
        return smsActive.get();
    }

    public SimpleIntegerProperty smsActiveProperty() {
        return smsActive;
    }

    public void setSmsActive(int smsActive) {
        this.smsActive.set(smsActive);
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

    public static ObservableList<smsValidatorModel> getSmsValidatorModelObservableList() {
        return smsValidatorModelObservableList;
    }

    public String getReplyBody() {
        return replyBody.get();
    }

    public SimpleStringProperty replyBodyProperty() {
        return replyBody;
    }

    public void setReplyBody(String replyBody) {
        this.replyBody.set(replyBody);
    }

    public String getReplyDate() {
        return replyDate.get();
    }

    public SimpleStringProperty replyDateProperty() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate.set(replyDate);
    }

    public static void setSmsValidatorModelObservableList(ObservableList<smsValidatorModel> smsValidatorModelObservableList) {
        smsValidatorModel.smsValidatorModelObservableList = smsValidatorModelObservableList;
    }

    public static ObservableList<smsValidatorModel> getAllUsers() {
        return smsValidatorModelObservableList;
    }


    public String getCustName() {
        return custName.get();
    }

    public SimpleStringProperty custNameProperty() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName.set(custName);
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public SimpleIntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId.set(appointmentId);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }
}
