package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private final IntegerProperty customerId;
    private final StringProperty customerName;
    private final StringProperty address;
    private final StringProperty address2;
    private final StringProperty city;
    private final StringProperty postalCode;
    private final StringProperty country;
    private final StringProperty phone;
    private final IntegerProperty smsActive;
    private final StringProperty messageSid;
    private int addressId;
    private int cityId;
    private int countryId;
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        return customers;
    }

    public Customer() {
        this.customerId = new SimpleIntegerProperty();
        this.customerName = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.address2 = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.postalCode = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.smsActive = new SimpleIntegerProperty();
        this.messageSid = new SimpleStringProperty();
    }

    public int getSmsActive() {
        return smsActive.get();
    }

    public IntegerProperty smsActiveProperty() {
        return smsActive;
    }

    public void setSmsActive(int smsActive) {
        this.smsActive.set(smsActive);
    }

    public String getCountry() {
        return country.get();
    }

    public String getMessageSid() {
        return messageSid.get();
    }

    public StringProperty messageSidProperty() {
        return messageSid;
    }

    public void setMessageSid(String messageSid) {
        this.messageSid.set(messageSid);
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
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

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getAddress2() {
        return address2.get();
    }

    public StringProperty address2Property() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }


    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }


}
