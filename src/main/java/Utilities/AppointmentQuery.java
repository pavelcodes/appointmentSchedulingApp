package Utilities;

import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static Model.Appointment.appointments;
import static View_Controller.LoginController.currentUser;

public class AppointmentQuery {


    //Appointment View String
    public static final String appointmentView = "CREATE OR REPLACE VIEW AppointmentView AS SELECT a.appointmentId, a.customerId, c.customerName, a.type, a.start, a.end, a.description, a.userId, a.createDate,a.createdBy,a.lastUpdate,a.lastUpdateBy, u.userName FROM appointment a INNER JOIN customer c ON a.customerId = c.customerId INNER JOIN user u ON a.userId = u.userId";

    //creates view for customers
    public static void createAppointmentView() throws SQLException {
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(appointmentView)) {
            statement.execute();
        }
    }


    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        createAppointmentView();
        String query1 = "select appointmentId,customerId,customerName,type,start,end,description,userId,username from AppointmentView order by start,username";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query1)) {
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                ZoneId zoneId = ZoneId.systemDefault();
                DateTimeFormatter formatFullLocal = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(zoneId);
                DateTimeFormatter formatDateLocal = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(zoneId);
                DateTimeFormatter formatTimeLocal = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId);
                DateTimeFormatter formatDateDisplay = DateTimeFormatter.ofPattern("MMMM, dd yyyy").withZone(zoneId);

                LocalTime startTime = rs.getObject("start", LocalTime.class);
                LocalDate startDate = rs.getObject("start", LocalDate.class);
                LocalDate endDate = rs.getObject("end", LocalDate.class);
                LocalTime endTime = rs.getObject("end", LocalTime.class);

                ZonedDateTime startUTC = ZonedDateTime.of(startDate, startTime, ZoneId.of("UTC")); //full utc start date/time
                ZonedDateTime endUTC = ZonedDateTime.of(endDate, endTime, ZoneId.of("UTC"));   //full utc end date/time


                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointmentId"));
                appointment.setCustomerId(rs.getInt("customerId"));
                appointment.setCustomerName(rs.getString("customerName"));
                appointment.setAppointmentType(rs.getString("type"));
                appointment.setStartFull(formatFullLocal.format(startUTC));
                appointment.setEndFull(formatFullLocal.format(endUTC));
                appointment.setEndDateFormatted(formatDateLocal.format(endDate));
                appointment.setStartDTdisplay(formatDateDisplay.format(startUTC));
                appointment.setStartDateFormatted(formatDateLocal.format(startDate));
                appointment.setNotes(rs.getString("description"));
                appointment.setConsultantId(rs.getInt("userId"));
                appointment.setConsultantName(rs.getString("username"));
                appointment.setStartTimeFormatted(formatTimeLocal.format(startUTC));
                appointment.setEndTimeFormatted(formatTimeLocal.format(endUTC));

                appointments.add(appointment);

            }
            return appointments;
        }

    }


    public static void addAppointment(Appointment appointment) throws SQLException {
        String query2 = "UPDATE appointment SET customerId = (select customerId from customer where customerName = ?), userId = (select userId from user where userName = ?), type = ?, start = ?, end = ?, description = ?, lastUpdate = ?, lastUpdateBy = ?, title =?, contact=?  WHERE appointmentId = ?";
        String query3 = "INSERT INTO appointment(customerId, userId, description, type, start, end, createDate, createdBy, lastUpdate, lastUpdateBy,title,contact,location,url) values ((select customerId from customer where customerName = ?), (select userId from user where userName = ?), ?, ?, ?, ?, ?, ?, ?,?,?,?,'','')";

        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query2);
             PreparedStatement statement2 = DBConnection.connection.prepareStatement(query3)) {

            if (appointment.getAppointmentId() > 0) {

                statement.setString(1, appointment.getCustomerName());
                statement.setString(2, appointment.getConsultantName());
                statement.setString(3, appointment.getAppointmentType());
                statement.setString(4, appointment.getStartFull());
                statement.setString(5, appointment.getEndFull());
                statement.setString(6, appointment.getNotes());
                statement.setTimestamp(7, Timestamp.from(Instant.now()));
                statement.setString(8, currentUser);
                statement.setInt(9, appointment.getAppointmentId());
                statement.setString(10,appointment.getReplyBody());
                statement.setString(11,appointment.getReplyDate());
                statement.executeUpdate();


            } else {
                //create new appointment

                statement2.setString(1, appointment.getCustomerName());
                statement2.setString(2, appointment.getConsultantName());
                statement2.setString(3, appointment.getNotes());
                statement2.setString(4, appointment.getAppointmentType());
                statement2.setString(5, appointment.getStartFull());
                statement2.setString(6, appointment.getEndFull());
                statement2.setTimestamp(7, Timestamp.from(Instant.now()));
                statement2.setString(8, currentUser);
                statement2.setTimestamp(9, Timestamp.from(Instant.now()));
                statement2.setString(10, currentUser);
                statement2.setString(11,appointment.getReplyBody());
                statement2.setString(12,appointment.getReplyDate());

                statement2.executeUpdate();
            }
        }

    }

    public static void modifyAppointment(Appointment appointment) throws SQLException {
        String query4 = "UPDATE appointment SET customerId = (select customerId from customer where customerName = ?), userId = (select userId from user where userName = ?), type = ?, start = ?, end = ?, description = ?, lastUpdate = ?, lastUpdateBy = ?  WHERE appointmentId = ?";

        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query4)) {

            statement.setString(1, appointment.getCustomerName());
            statement.setString(2, appointment.getConsultantName());
            statement.setString(3, appointment.getAppointmentType());
            statement.setString(4, appointment.getStartFull());
            statement.setString(5, appointment.getEndFull());
            statement.setString(6, appointment.getNotes());
            statement.setTimestamp(7, Timestamp.from(Instant.now()));
            statement.setString(8, currentUser);
            statement.setInt(9, appointment.getAppointmentId());
            statement.executeUpdate();
        }

    }


    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String query5 = "DELETE appointment.* FROM appointment WHERE appointment.customerId = ? AND appointment.appointmentId = ?";

        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query5);
        ) {
            statement.setInt(1, appointment.getCustomerId());
            statement.setInt(2, appointment.getAppointmentId());
            statement.execute();
        }
    }

    public static void appointmentAlert15Min() throws SQLException {
        createAppointmentView();
        String query6 = "select customerName,type,start,end,username,appointmentId from AppointmentView order by start,username";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query6);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {

                ZoneId zoneId = ZoneId.systemDefault();
                LocalTime startTime = rs.getObject("start", LocalTime.class); //gets start time from db
                LocalDate startDate = rs.getObject("start", LocalDate.class); //gets start date from db
                ZonedDateTime startUTC = ZonedDateTime.of(startDate, startTime, ZoneId.of("UTC"));  //full utc start date/time
                Instant instant = Instant.now(); //get instant in current time
                ZonedDateTime zdt = instant.atZone(zoneId); // takes instant in current time and applys system zoneID. saves it as ZonedDateTIme


                int appId= rs.getInt("appointmentId");
                long timeDifference = ChronoUnit.MINUTES.between(zdt, startUTC);
                long timeBeforeAppointment = timeDifference + 1;
                if (currentUser.equals(rs.getString("username"))) {
                    if (timeBeforeAppointment >= 0 && timeBeforeAppointment <= 15) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Reminder");
                        alert.setHeaderText(rs.getString("username") + ", you have an upcoming " + rs.getString("type") + " appointment with " + rs.getString("customerName") + " in " + timeBeforeAppointment + " minute(s)");
                        alert.showAndWait();
                        return;
                    }
                }

            }
        }

    }

    public static boolean appointmentOverlap(String startOverlap, String endOverlap, String providerName) throws SQLException {
        createAppointmentView();
        String query7 = "select start, end, customerName, username, appointmentId from AppointmentView where ? BETWEEN start AND (end + Interval -1 MINUTE ) OR (?+ INTERVAL -1 MINUTE ) BETWEEN start AND end";

        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query7)) {
            statement.setString(1, startOverlap);
            statement.setString(2, endOverlap);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ZoneId zoneId = ZoneId.systemDefault();
                DateTimeFormatter formatDateDisplay = DateTimeFormatter.ofPattern("MMMM, dd yyyy HH:mm").withZone(zoneId);

                LocalTime startTime = rs.getObject("start", LocalTime.class);
                LocalDate startDate = rs.getObject("start", LocalDate.class);
                LocalDate endDate = rs.getObject("end", LocalDate.class);
                LocalTime endTime = rs.getObject("end", LocalTime.class);

                ZonedDateTime startUTC = ZonedDateTime.of(startDate, startTime, ZoneId.of("UTC")); //full utc start date/time
                ZonedDateTime endUTC = ZonedDateTime.of(endDate, endTime, ZoneId.of("UTC"));   //full utc end date/time

                String customerName = rs.getString("customerName");
                String username = rs.getString("username");
                boolean test = customerName.length() > 1 && providerName.equals(username); // checks that a customer overlap exists and makes sure the overlap provider is the same
                if (test) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Reminder");
                    alert.setHeaderText("There is a scheduling conflict with customer:  " + rs.getString("customerName") + ". The appointment conflict in question starts on: " + formatDateDisplay.format(startUTC) + " ends on " + formatDateDisplay.format(endUTC));
                    alert.showAndWait();
                    return true;
                }
            }
        }

        return false;
    }


    public static boolean appointmentOverlap(String startOverlap, String endOverlap, String providerName, String appointmentID) throws SQLException {
        createAppointmentView();

        String query8 = "select start, end, customerName, username, appointmentId from AppointmentView where ? BETWEEN start AND (end + Interval -1 MINUTE ) OR (?+ INTERVAL -1 MINUTE ) BETWEEN start AND end";

        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query8)) {
            statement.setString(1, startOverlap);
            statement.setString(2, endOverlap);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ZoneId zoneId = ZoneId.systemDefault();
                DateTimeFormatter formatDateDisplay = DateTimeFormatter.ofPattern("MMMM, dd yyyy HH:mm").withZone(zoneId);
                DateTimeFormatter formatCheck = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalTime startTime = rs.getObject("start", LocalTime.class);
                LocalDate startDate = rs.getObject("start", LocalDate.class);
                LocalDate endDate = rs.getObject("end", LocalDate.class);
                LocalTime endTime = rs.getObject("end", LocalTime.class);

                ZonedDateTime startUTC = ZonedDateTime.of(startDate, startTime, ZoneId.of("UTC")); //full utc start date/time
                ZonedDateTime endUTC = ZonedDateTime.of(endDate, endTime, ZoneId.of("UTC"));   //full utc end date/time
                String customerName = rs.getString("customerName");
                String username = rs.getString("username");
                String appID = String.valueOf(rs.getInt("appointmentId"));

                boolean test = (customerName.length() > 1 && providerName.equals(username)); // check to see if any user returned and if the users returned have the same provider
                boolean test2 = (customerName.length() > 1 && appointmentID.equals(appID)); //check to see if overlapping appointment is the same ID as modified appointment
                if (test2) {
                    return false;
                } else if (test) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Reminder");
                    alert.setHeaderText("There is a scheduling conflict with customer:  " + rs.getString("customerName") + ". The appointment conflict in question starts on: " + formatDateDisplay.format(startUTC) + " ends on " + formatDateDisplay.format(endUTC));
                    alert.showAndWait();
                    return true;
                }
            }
        }
        return false;
    }

}
