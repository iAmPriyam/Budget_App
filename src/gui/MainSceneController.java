package gui;

import accounts.Account;
import com.jfoenix.controls.*;
import database.SqliteDb;
import expenses.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import users.User;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class MainSceneController  {

    User user;
    double moneySpentThisMonth;
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
    private JFXProgressBar budgetProgressBar;

    //MONTHLY OVERVIEW TAB'S ELEMENTS
    @FXML
    private PieChart expensesPieChart;
    @FXML
    private Label budgetPercentTxt;
    @FXML
    private JFXTextField moneyLeftTxt;
    @FXML
    private JFXTextField moneyPerDayTxt;
    @FXML
    private JFXTextField inc_expRatingTxt;

    //SETTING TAB'S ELEMENTS
    @FXML
    private JFXTextField newBudgetTxt;

    public void fillMainScene() {
        this.user = User.getInstance();
        if(this.user!=null) {
            this.setAccountsTable();
            this.setTotalBalanceLbl();
            this.fillLineChart();
            this.setPieChart();
        } else {

        }
    }

    //METHODS TO FILL MAINSCENE
    private void setAccountsTable() {
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList();
        for(Account account: user.getAccounts()) {
            AccountsTable.getItems().addAll(account);
            accountObservableList.add(account);
        }
        AccountNameColumn.setCellValueFactory
                (new PropertyValueFactory<Account, String>("accountName"));
        AccountBalanceColumn.setCellValueFactory
                (new PropertyValueFactory<Account, Double>("accountBalance"));
    }

    private void setTotalBalanceLbl() {
        double balance = this.computeTotalBalance();
        DecimalFormat formatter = new DecimalFormat("#0.00");
        totalBalanceLbl.setText(formatter.format(balance)+" PLN");
    }
    
    private double computeTotalBalance(){
        double totalBalance = 0;
        for(Account account: user.getAccounts()) {
            totalBalance +=account.getAccountBalance();
        }
        return totalBalance;
    }

    private void fillLineChart() {

        XYChart.Series monthlyBudget = new XYChart.Series();
        monthlyBudget.setName("Your budget");
        XYChart.Series monthlyExpenses = new XYChart.Series();
        monthlyExpenses.setName("Your expenses");

        ArrayList<Expense> currentMonthExpenses = user.getMonthlyExpenses();
        GregorianCalendar date = new GregorianCalendar();
        
        this.moneySpentThisMonth = 0;
        for(int i=1;i<=date.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
            for(Expense expense : currentMonthExpenses) {
                if(expense.getDate().get(Calendar.DAY_OF_MONTH)==i){
                    this.moneySpentThisMonth += expense.getPrice();
                }
            }
            monthlyBudget.getData().add(new XYChart.Data<>(Integer.toString(i),user.getMonthlyBudget()));

            monthlyExpenses.getData().add(new XYChart.Data<>(Integer.toString(i),this.moneySpentThisMonth));
        }

        monthlyLineChart.getData().addAll(monthlyBudget, monthlyExpenses);
        monthlyLineChart.setTitle("Your expenses graph for current month");
        monthlyLineChart.setCreateSymbols(false);
        DecimalFormat formatter = new DecimalFormat("#0.00");
        double moneyLeft =user.getMonthlyBudget()-this.moneySpentThisMonth;
        String money =formatter.format(moneyLeft);
        System.out.println(money);
        moneyLeftLbl.setText(money+" PLN");
    }
    
    
    //MONTHLY OVERVIEW METHODS

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

    public void setPieChart() {
        ArrayList<Expense> allExpenses = user.getMonthlyExpenses();
        GregorianCalendar date = new GregorianCalendar();
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
        budgetProgressBar.setProgress(moneySpentThisMonth /user.getMonthlyBudget());
        DecimalFormat formatter = new DecimalFormat("#0.00");
        budgetPercentTxt.setText(formatter.format(moneySpentThisMonth /user.getMonthlyBudget())+" % of monthly budget");
        moneyLeftTxt.setText(formatter.format(user.getMonthlyBudget()- moneySpentThisMonth));
        GregorianCalendar date2 = new GregorianCalendar();
        int dayLeft = date.getActualMaximum(Calendar.DAY_OF_MONTH)-date.get(Calendar.DAY_OF_MONTH)+1;
        double moneyPerDay = (user.getMonthlyBudget()- moneySpentThisMonth)/dayLeft;
        moneyPerDayTxt.setText(formatter.format(moneyPerDay));
    }

    //SETTING METHODS

    public void setNewBudget() {
            try {
                user.setMonthlyBudget(Double.parseDouble(newBudgetTxt.getText()));
                SqliteDb db = new SqliteDb();
                db.updateUser(user);
                db.closeConnection();
                this.refresh();
            } catch (NumberFormatException exc) {
                System.out.println(exc);
                System.out.println("NOT A NUMBER");
            }
    }

    private void refresh(){
        SqliteDb db = new SqliteDb();
        monthlyLineChart.getData().clear();
        AccountsTable.getItems().clear();
        expensesPieChart.getData().clear();
        this.fillMainScene();
        db.closeConnection();
    }


}
