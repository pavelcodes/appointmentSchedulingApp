package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class typeReport {

    private final StringProperty month;
    private final StringProperty type;
    private final StringProperty total;
    private final StringProperty year;
    public static ObservableList<typeReport> typeReport = FXCollections.observableArrayList();

    public static ObservableList<typeReport> getTypeReport() {
        return typeReport;
    }


    public typeReport() {
        this.month = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.total = new SimpleStringProperty();
        this.year = new SimpleStringProperty();
    }

    public String getYear() {
        return year.get();
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public String getMonth() {
        return month.get();
    }

    public StringProperty monthProperty() {
        return month;
    }

    public void setMonth(String month) {
        this.month.set(month);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getTotal() {
        return total.get();
    }

    public StringProperty totalProperty() {
        return total;
    }

    public void setTotal(String total) {
        this.total.set(total);
    }
}
