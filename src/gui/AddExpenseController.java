package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import expenses.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

public class AddExpenseController {

    @FXML
    private JFXTextField expenseNameTxt;
    @FXML
    private JFXTextField PriceTxt;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox<?> categoryChoiceBox;
    @FXML
    private ChoiceBox<?> accountChoiceBox;
    @FXML
    private JFXButton addExpenseBtn;


    public boolean addExpense(){
        return true;
    }

}