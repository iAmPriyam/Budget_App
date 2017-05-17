package gui;

import accounts.Account;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.SqliteDb;
import incomes.IncomeCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import users.User;


public class AddIncomeController  {
    User user;

    @FXML
    private JFXTextField incomeNameTxt;
    @FXML
    private JFXTextField moneyTxt;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<IncomeCategory> categoryChoiceBox;
    @FXML
    private ChoiceBox<Account> accountChoiceBox;
    @FXML
    private JFXButton addIncomeBtn;

    public void populateData(User user ) {
        this.user = user;
        this.getCategories();
        this.getAccounts();
    }
    private void getCategories(){
        SqliteDb db = new SqliteDb();
        ObservableList<IncomeCategory> incomeCategories = FXCollections.observableList(db.getIncomeCategories());
        db.closeConnection();
        categoryChoiceBox.setItems(incomeCategories);
    }
    private void getAccounts(){
        ObservableList<Account> accounts = FXCollections.observableArrayList(user.getAccounts());
        accountChoiceBox.setItems(accounts);
    }

    public void addExpense() {
        SqliteDb db = new SqliteDb();
        db.insertIncome(accountChoiceBox.getSelectionModel().getSelectedItem().getId(),
                categoryChoiceBox.getSelectionModel().getSelectedItem().getId(),
                incomeNameTxt.getText(),Double.parseDouble(moneyTxt.getText()),datePicker.getValue().toString());
        db.updateAccount(accountChoiceBox.getSelectionModel().getSelectedItem(),
                accountChoiceBox.getSelectionModel().getSelectedItem().getAccountBalance()+Double.parseDouble(moneyTxt.getText()));
        db.closeConnection();
        Stage stage = (Stage) addIncomeBtn.getScene().getWindow();
        stage.close();
    }
}