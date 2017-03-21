package gui;

import accounts.Account;
import expenses.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import users.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class MainSceneController  {

    User user;
    //OVERVIEW TAB'S ELEMENTS
    @FXML
    private LineChart<?, ?> monthlyLineChart;
    @FXML
    private CategoryAxis dayOfMonthAxis;
    @FXML
    private NumberAxis moneyAxis;
    @FXML
    private Label moneyLeftLbl;
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
            this.fillLineChart(user.getAccounts());
        } else {

        }
    }
    private void fillAccountsTable() {
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList();
        for(Account account: user.getAccounts()) {
            AccountsTable.getItems().addAll(account);
            accountObservableList.add(account);
        }
        AccountNameColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("accountName"));
        AccountBalanceColumn.setCellValueFactory(new PropertyValueFactory<Account, Double>("accountBalance"));
    }
    private void computeTotalBalance(){
        double totalBalance = 0;
        for(Account account: user.getAccounts()) {
            totalBalance +=account.getAccountBalance();
        }
        totalBalanceLbl.setText(Double.toString(totalBalance)+" PLN");
    }
    private void fillLineChart(ArrayList<Account> accounts) {

        ArrayList<Expense> allExpenses = new ArrayList<>();
        GregorianCalendar date = new GregorianCalendar();
        int month = date.get(Calendar.MONTH);
        for(Account account: accounts) {
            for(Expense expense: account.getExpensesList()) {
                if(expense.getDate().get(Calendar.MONTH)==month+1) {
                    allExpenses.add(expense);
                }
            }
        }
        XYChart.Series monthlyBudget = new XYChart.Series();
        monthlyBudget.setName("Your budget");
        XYChart.Series monthlyExpenses = new XYChart.Series();
        monthlyExpenses.setName("Your expenses");

        double totalMoneySpend = 0;
        for(int i=1;i<=date.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
            for(Expense expense : allExpenses) {
                if(expense.getDate().get(Calendar.DAY_OF_MONTH)==i){
                    totalMoneySpend = totalMoneySpend + expense.getPrice();
                }
            }
            monthlyBudget.getData().add(new XYChart.Data<>(Integer.toString(i),user.getMonthlyBudget()));

            monthlyExpenses.getData().add(new XYChart.Data<>(Integer.toString(i),totalMoneySpend));
        }
        monthlyLineChart.getData().addAll(monthlyBudget, monthlyExpenses);
        monthlyLineChart.setTitle("Your expenses graph for current month");
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double moneyLeft =user.getMonthlyBudget()-totalMoneySpend;
        String money =formatter.format(moneyLeft);
        System.out.println(money);
        moneyLeftLbl.setText(money+" PLN");
    }

}
