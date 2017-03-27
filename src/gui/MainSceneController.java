package gui;

import accounts.Account;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXProgressBar;
import database.SqliteDb;
import expenses.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import users.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class MainSceneController  {

    User user;
    double totalMoneySpend;
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
    @FXML
    private JFXButton addExpenseBtn;
    @FXML
    private JFXProgressBar budgetProgressBar;

    //monthly overview
    @FXML
    private PieChart expensesPieChart;


    public void GetUser(User user) {
        if(user!=null) {
            this.user = user;
            this.fillAccountsTable();
            this.computeTotalBalance();
            this.fillLineChart(user.getAccounts());
            this.fillPieChart(user.getAccounts());
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

        this.totalMoneySpend = 0;
        for(int i=1;i<=date.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
            for(Expense expense : allExpenses) {
                if(expense.getDate().get(Calendar.DAY_OF_MONTH)==i){
                    this.totalMoneySpend = this.totalMoneySpend + expense.getPrice();
                }
            }
            monthlyBudget.getData().add(new XYChart.Data<>(Integer.toString(i),user.getMonthlyBudget()));

            monthlyExpenses.getData().add(new XYChart.Data<>(Integer.toString(i),this.totalMoneySpend));
        }
        monthlyLineChart.getData().addAll(monthlyBudget, monthlyExpenses);
        monthlyLineChart.setTitle("Your expenses graph for current month");
        monthlyLineChart.setCreateSymbols(false);
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double moneyLeft =user.getMonthlyBudget()-this.totalMoneySpend;
        String money =formatter.format(moneyLeft);
        System.out.println(money);
        moneyLeftLbl.setText(money+" PLN");
    }
    public void addExpense() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addExpenseDialog.fxml"));
            Parent root = (Parent) loader.load();
            AddExpenseController controller = loader.getController();
            controller.populateData(user);
            Stage stage = new Stage();
            stage.setTitle("Add expense");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.showAndWait();
            this.refresh();
        } catch(IOException exc) {
            System.out.println(exc);

        }

    }
    public void addIncome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addIncomeDialog.fxml"));
            Parent root = (Parent) loader.load();
            AddIncomeController controller = loader.getController();
            controller.populateData(user);
            Stage stage = new Stage();
            stage.setTitle("Add income");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            this.refresh();
        } catch(IOException exc) {
            System.out.println(exc);
        }
    }
    private void refresh(){
        SqliteDb db = new SqliteDb();
        monthlyLineChart.getData().clear();
        AccountsTable.getItems().clear();
        expensesPieChart.getData().clear();
        this.GetUser(db.getUserData("budget"));
        db.closeConnection();
    }

    //overview
    public void fillPieChart(ArrayList<Account> accounts) {
        SqliteDb db = new SqliteDb();
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
        HashMap<String,Double> pieChartData = new HashMap<>();
        for(Expense expense : allExpenses) {
            if(pieChartData.containsKey(expense.getCategory().getName())) {
                pieChartData.put(expense.getCategory().getName(),
                        pieChartData.get(expense.getCategory().getName())+expense.getPrice());
            }
            else {
                pieChartData.put(expense.getCategory().getName(), expense.getPrice());
            }
        }
        pieChartData.forEach((k,v) -> {
            PieChart.Data slice = new PieChart.Data(k,v);
            expensesPieChart.getData().add(slice);
        });
        expensesPieChart.setLegendSide(Side.LEFT);
        budgetProgressBar.setProgress(totalMoneySpend/user.getMonthlyBudget());

    }
}
