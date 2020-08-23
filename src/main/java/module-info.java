module ScheduleNotifications.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires twilio;
    requires com.fasterxml.jackson.databind;
    requires java.xml.bind;
    requires spark.core;
    requires javax.servlet.api;
    requires com.jfoenix;
    opens Main to javafx.graphics;
    opens View_Controller to javafx.fxml;
}