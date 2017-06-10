package gui;

import accounts.Account;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.SqliteDb;
import expenses.Expense;
import expenses.ExpenseCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import users.User;

import java.util.GregorianCalendar;
import java.util.logging.Logger;

public class AddExpenseController  {
    User user;
    private static final Logger LOGGER = Logger.getLogger( SqliteDb.class.getName() );
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
        this.getCategories();
        this.getAccounts();
    }
    private void getCategories(){
        SqliteDb db = new SqliteDb();
        ObservableList<ExpenseCategory> expenseCategories = FXCollections.observableList(db.getExpenseCategories());
        db.closeConnection();
        categoryChoiceBox.setItems(expenseCategories);
    }
    private void getAccounts(){
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
        LOGGER.info("inserting new expense to database...");
        db.updateAccount(accountChoiceBox.getSelectionModel().getSelectedItem(),
                accountChoiceBox.getSelectionModel().getSelectedItem().getAccountBalance()-Double.parseDouble(priceTxt.getText()));
        db.closeConnection();

        String[] dateArray = datePicker.getValue().toString().split("-");
        int dayOfMonth = Integer.parseInt(dateArray[2]);
        int month = Integer.parseInt(dateArray[1]);
        int year = Integer.parseInt(dateArray[0]);
        GregorianCalendar date = new GregorianCalendar(year,month,dayOfMonth);
        Account account = accountChoiceBox.getSelectionModel().getSelectedItem();
        account.addExpense(new Expense(100,expenseNameTxt.getText(),Double.parseDouble(priceTxt.getText()),categoryChoiceBox.getSelectionModel().getSelectedItem(),date));
        LOGGER.info("added new expense");

        Stage stage = (Stage) addExpenseBtn.getScene().getWindow();
        stage.close();
    }
}