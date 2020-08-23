package View_Controller;

import Model.typeReport;
import Utilities.ReportQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Model.typeReport.getTypeReport;
import static Model.typeReport.typeReport;

public class Report2 implements Initializable {

    @FXML
    private AnchorPane searchCustomersField;

    @FXML
    private HBox root;

    @FXML
    private Text topBannerCustomerDash;

    @FXML
    private TableView<Model.typeReport> appointmentTableView;

    @FXML
    private TableColumn<Model.typeReport, String> monthColumn;

    @FXML
    private TableColumn<Model.typeReport, String> typeColumn;

    @FXML
    private TableColumn<Model.typeReport, String> totalColumn;

    @FXML
    private Label UpcomingAppointmentsLabel;

    @FXML
    private Button back;

    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        URL url = getClass().getResource("/view/reportsDashboard.fxml");
        Parent root = FXMLLoader.load(url);
        Scene mainScene = new Scene(root);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(mainScene);
        app_stage.setTitle("Scheduling Application");
        app_stage.show();
    }
    public void reloadAppointments() throws SQLException {
        typeReport.clear();
        ReportQuery.getReport2();
        appointmentTableView.setItems(getTypeReport());

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ReportQuery.getReport2();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        monthColumn.setCellValueFactory(cellData -> cellData.getValue().monthProperty());
        appointmentTableView.setItems(typeReport);
        try {
            reloadAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
