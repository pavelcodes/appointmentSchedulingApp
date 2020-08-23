package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ReportsDashboardController {

    @FXML
    private HBox root;

    @FXML
    private Text topBannerReports;

    @FXML
    private Button consultantScheduleReport;

    @FXML
    private Button appointmentTypeByMonthReport;

    @FXML
    private Button AditionalReport;

    @FXML
    private Button back;

    @FXML
    void onActionAppointmentTypeByMonthReport(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/report2.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Report");
        app_stage.show();

    }

    @FXML
    void onActionBack(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/view/mainScreen.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();

    }

    @FXML
    void onActionConsultantScheduleReport(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/report1.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Report");
        app_stage.show();
    }

    @FXML
    void onActionAppointmentTypeByYearReport(ActionEvent event) throws IOException{
        URL url = getClass().getResource("/view/report3.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Report");
        app_stage.show();

    }

}
