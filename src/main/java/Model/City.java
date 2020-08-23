package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class City {
    private final IntegerProperty cityId;
    private final StringProperty city;
    private final StringProperty country;

    public City() {
        this.cityId = new SimpleIntegerProperty();
        this.city = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
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

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
}
