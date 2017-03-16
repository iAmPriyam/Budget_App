package gui;

import accounts.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import users.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController {

    User user;
    ObservableList<Account> accounts = FXCollections.observableArrayList();
    @FXML
    private MenuItem menuSettings;
    @FXML
    private MenuItem menuStats;
    @FXML
    private MenuItem menuPayments;
    @FXML
    private MenuItem menuAccounts;
    @FXML
    private TreeTableView AccountsTable;
    @FXML
    private TreeTableColumn AccountNameColumn;
    @FXML
    private TreeTableColumn AccountBalanceColumn;


    public void GetUser(User user) {
        if(user!=null) {

            this.user = user;
            this.fillData();
        } else {

        }
    }
    private void fillData() {
        for(Account account: user.getAccounts())
        accounts.add(account);

    }

}
