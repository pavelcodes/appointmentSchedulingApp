package Utilities;

import Model.typeReport;
import java.sql.*;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

import static Model.typeReport.typeReport;

public class ReportQuery {

    public static ObservableList<typeReport> getReport2() throws SQLException{
        String query = "SELECT  monthname(start), type, count(appointmentId) from appointment WHERE YEAR(start)=YEAR(CURDATE()) group by monthname(start), type order by start,type";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                typeReport report = new typeReport();
                String month = rs.getString(1);
                String type = rs.getString(2);
                String total = rs.getString(3);
                
                report.setMonth(month);
                report.setType(type);
                report.setTotal(total);
                typeReport.add(report);
            }
            return typeReport;
        }

    }

    public static ObservableList<typeReport> getReport3() throws SQLException {
        String query2 = "SELECT  YEAR(start), type, count(appointmentId) from appointment group by YEAR(start), type order by YEAR(start),type";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query2);
             ResultSet rs = statement.executeQuery()){

            while (rs.next()) {
                typeReport report = new typeReport();
                String year = rs.getString(1);
                String type = rs.getString(2);
                String total = rs.getString(3);

                report.setYear(year);
                report.setType(type);
                report.setTotal(total);
                typeReport.add(report);
            }
            return typeReport;
        }

    }
}
