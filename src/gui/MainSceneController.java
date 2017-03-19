package gui;

import accounts.Account;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import users.User;

public class MainSceneController  {

    User user;

    @FXML
    private TableView<Account> AccountsTable;
    @FXML
    private TableColumn<Account,String> AccountNameColumn;
    @FXML
    private TableColumn<Account,Double> AccountBalanceColumn;
    @FXML
    private Label totalBalanceLbl;


    public void GetUser(User user) {
        if(user!=null) {

            this.user = user;
            this.fillAccountsTable();
            this.computeTotalBalance();
        } else {

        }
    }
    private void fillAccountsTable() {
        for(Account account: user.getAccounts()) {
            AccountsTable.getItems().addAll(account);
        }
        AccountNameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("accountName"));
        AccountBalanceColumn.setCellValueFactory(new PropertyValueFactory<Account, Double>("accountBalance"));
    }

    private void computeTotalBalance(){
        double totalBalance = 0;
        for(Account account: user.getAccounts()) {
            totalBalance +=account.getAccountBalance();
        }
        totalBalanceLbl.setText(Double.toString(totalBalance));
    }

}
