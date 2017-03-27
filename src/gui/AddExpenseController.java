package gui;

import accounts.Account;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.SqliteConnection;
import database.SqliteDb;
import expenses.Expense;
import expenses.ExpenseCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import users.User;

import javax.jws.soap.SOAPBinding;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddExpenseController  {
    User user;

    @FXML
    private JFXTextField expenseNameTxt;
    @FXML
    private JFXTextField priceTxt;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<ExpenseCategory> categoryChoiceBox;
    @FXML
    private ChoiceBox<Account> accountChoiceBox;
    @FXML
    private JFXButton addExpenseBtn;

    public void populateData(User user ) {
        this.user = user;
        this.putCategories();
        this.putAccounts();
    }
    private void putCategories(){
        SqliteDb db = new SqliteDb();
        ObservableList<ExpenseCategory> expenseCategories = FXCollections.observableList(db.getExpenseCategories());
        db.closeConnection();
        categoryChoiceBox.setItems(expenseCategories);
    }
    private void putAccounts(){
        ObservableList<Account> accounts = FXCollections.observableArrayList(user.getAccounts());
        accountChoiceBox.setItems(accounts);
    }


    public void addExpense(){
        SqliteDb db = new SqliteDb();
        db.insertExpense(accountChoiceBox.getSelectionModel().getSelectedItem().getId(),
                categoryChoiceBox.getSelectionModel().getSelectedItem().getId(),
                expenseNameTxt.getText(),
                Double.parseDouble(priceTxt.getText()),
                datePicker.getValue().toString());
        System.out.println("added new expense to database");
        db.closeConnection();
        Stage stage = (Stage) addExpenseBtn.getScene().getWindow();
        stage.close();
    }
}