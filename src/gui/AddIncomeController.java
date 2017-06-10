package gui;

import accounts.Account;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.SqliteDb;
import incomes.Income;
import incomes.IncomeCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import users.User;

import java.util.GregorianCalendar;
import java.util.logging.Logger;


public class AddIncomeController  {
    private static final Logger LOGGER = Logger.getLogger( SqliteDb.class.getName() );
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
        System.out.println(accounts);
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
        String[] dateArray = datePicker.getValue().toString().split("-");
        int dayOfMonth = Integer.parseInt(dateArray[2]);
        int month = Integer.parseInt(dateArray[1]);
        int year = Integer.parseInt(dateArray[0]);
        GregorianCalendar date = new GregorianCalendar(year,month,dayOfMonth);
        Account account = accountChoiceBox.getSelectionModel().getSelectedItem();
        account.addIncome(new Income(100,incomeNameTxt.getText(),Double.parseDouble(moneyTxt.getText()),categoryChoiceBox.getSelectionModel().getSelectedItem(),date));
        LOGGER.info("added new income");
        Stage stage = (Stage) addIncomeBtn.getScene().getWindow();
        stage.close();
    }
}