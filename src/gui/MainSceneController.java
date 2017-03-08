package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import users.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable{

    User user;
    @FXML
    private Label userLbl;
    @FXML
    private MenuItem menuSettings;
    @FXML
    private MenuItem menuStats;
    @FXML
    private MenuItem menuPayments;
    @FXML
    private MenuItem menuAccounts;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private LineChart<?,?> monthlyBudgetChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //do sth here
    }
    public void GetUser(User user) {
        if(user!=null) {
            userLbl.setText(user.getName());
            this.user = user;
            this.fillChart();
        } else {
            userLbl.setText("zjebawszy cos");
        }
    }
    private void fillChart() {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("1", 2));
        monthlyBudgetChart.getData().addAll(series);
    }

}
