package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Address {
    private final IntegerProperty addressId;
    private final StringProperty address;
    private final StringProperty address2;
    private final StringProperty fullAddress;
    private final IntegerProperty cityId;
    private final StringProperty postalCode;
    private final StringProperty phone;
    private final City city;

    public int getAddressId() {
        return addressId.get();
    }

    public IntegerProperty addressIdProperty() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
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

    public int getCityId() {
        return cityId.get();
    }

    public IntegerProperty cityIdProperty() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId.set(cityId);
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

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public City getCity() {
        return city;
    }


    public String getFullAddress() {
        return fullAddress.get();
    }

    public StringProperty fullAddressProperty() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress.set(fullAddress);
    }


    public Address() {
        this.addressId = new SimpleIntegerProperty();
        this.address = new SimpleStringProperty();
        this.address2 = new SimpleStringProperty();
        this.cityId = new SimpleIntegerProperty();
        this.postalCode = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.fullAddress = new SimpleStringProperty();
        this.city = new City();

    }
}

