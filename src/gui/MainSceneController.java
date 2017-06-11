package gui;

import accounts.Account;
import com.jfoenix.controls.*;
import database.SqliteDb;
import expenses.Expense;
import expenses.ExpenseCategory;
import expenses.RegularExpense;
import incomes.Income;
import incomes.IncomeCategory;
import incomes.RegularIncome;
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

    //Incomes
    @FXML
    private TableView<Income> AllIncomesTable;
    @FXML
    private TableColumn<Income,String> IncomeNameColumn;
    @FXML
    private TableColumn<Income,GregorianCalendar> IncomeDateColumn;
    @FXML
    private TableColumn<Income,Double> IncomeMoneyColumn;
    @FXML
    private TableColumn<Income,IncomeCategory> IncomeCategoryColumn;

    //Incomes (regular)
    @FXML
    private TableView<RegularIncome> RegIncomesTable;
    @FXML
    private TableColumn<Income,String> RegIncomesColumnName;
    @FXML
    private TableColumn<Income,GregorianCalendar> RegIncomesColumnDate;
    @FXML
    private TableColumn<Income,Double> RegIncomesColumnMoney;
    @FXML
    private TableColumn<Income,IncomeCategory> RegIncomesColumnCategory;
    @FXML
    private TableColumn<Income,GregorianCalendar> RegIncomesColumnLastIncome;
    @FXML
    private TableColumn<Income,Integer> RegIncomesColumnFrequency;

    //Expenses
    @FXML
    private TableView<Expense> AllExpenseTable;
    @FXML
    private TableColumn<Expense,String> ExpenseNameColumn;
    @FXML
    private TableColumn<Expense,GregorianCalendar> ExpenseDateColumn;
    @FXML
    private TableColumn<Expense,Double> ExpensePriceColumn;
    @FXML
    private TableColumn<Expense, ExpenseCategory> ExpenseCategoryColumn;

    //Expenses (regular)
    @FXML
    private TableView<RegularExpense> RegExpenseTable;
    @FXML
    private TableColumn<RegularExpense,String> RegExpColumnName;
    @FXML
    private TableColumn<RegularExpense,GregorianCalendar> RegExpColumnDate;
    @FXML
    private TableColumn<RegularExpense,Double> RegExpColumnPrice;
    @FXML
    private TableColumn<RegularExpense,ExpenseCategory> RegExpColumnCategory;
    @FXML
    private TableColumn<RegularExpense,String> RegExpColumnLastExpense;
    @FXML
    private TableColumn<RegularExpense,Integer> RegExpColumnFrequency;


    //SETTING TAB'S ELEMENTS
    @FXML
    private JFXTextField newBudgetTxt;



    public void fillMainScene() {
        this.user = User.getInstance();
        if(this.user!=null) {
            this.setOverviewTab();
            this.setStatisticTab();
            this.setAllIncomesTable();
            this.setAllExpensesTable();
            this.setRegExpenseTable();
            this.setRegIncomesTable();
        } else {

        }
    }

    //METHODS TO FILL MAINSCENE
    private void setOverviewTab(){
        this.setAccountsTable();
        this.setTotalBalanceLbl();
        this.fillLineChart();
    }

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
        double balance = this.user.getTotalBalance();
        DecimalFormat formatter = new DecimalFormat("#0.00");
        totalBalanceLbl.setText(formatter.format(balance)+" PLN");
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

    public void setStatisticTab(){
        setPieChart();
        setBudgetProgressBar();
        setMonthlyBudgetDetails();
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

    private void setPieChart() {
        ArrayList<Expense> allExpenses = user.getMonthlyExpenses();

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
    }

    private void setBudgetProgressBar() {
        budgetProgressBar.setProgress(moneySpentThisMonth /user.getMonthlyBudget());
        DecimalFormat formatter = new DecimalFormat("#0.00");
        budgetPercentTxt.setText(formatter.format(moneySpentThisMonth*100 /user.getMonthlyBudget())+" % of monthly budget");
    }

    private void setMonthlyBudgetDetails() {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        moneyLeftTxt.setText(formatter.format(user.getMonthlyBudget()- moneySpentThisMonth));
        GregorianCalendar date = new GregorianCalendar();
        int dayLeft = date.getActualMaximum(Calendar.DAY_OF_MONTH)-date.get(Calendar.DAY_OF_MONTH)+1;
        double moneyPerDay = (user.getMonthlyBudget()- moneySpentThisMonth)/dayLeft;
        moneyPerDayTxt.setText(formatter.format(moneyPerDay));
    }

    //INCOMES METHODS
    // all incomes

    private void setAllIncomesTable(){
        ObservableList<Income> incomesObservableList = FXCollections.observableArrayList();
        for(Account account: user.getAccounts()) {
            for(Income income : account.getIncomesList()) {
                AllIncomesTable.getItems().add(income);
                incomesObservableList.add(income);
            }
        }
        IncomeNameColumn.setCellValueFactory
                (new PropertyValueFactory<Income, String>("name"));
        IncomeDateColumn.setCellValueFactory
                (new PropertyValueFactory<Income, GregorianCalendar>("date"));
        IncomeMoneyColumn.setCellValueFactory
                (new PropertyValueFactory<Income, Double>("money"));
        IncomeCategoryColumn.setCellValueFactory
                (new PropertyValueFactory<Income, IncomeCategory>("category"));
    }

    public void removeIncome(){
        Income income = AllIncomesTable.getSelectionModel().getSelectedItem();
        one:for(Account account: this.user.getAccounts()) {
            for(Income i : account.getIncomesList()) {
                if(i==income) {
                    account.removeIncome(income);

                    break one;
                }
            }
        }
        this.refresh();
    }

    //EXPENSES METHODS

    private void setAllExpensesTable(){
        ObservableList<Expense> expensesObservableList = FXCollections.observableArrayList();
        for(Account account: user.getAccounts()) {
            for(Expense expense : account.getExpensesList()) {
                AllExpenseTable.getItems().add(expense);
                expensesObservableList.add(expense);
            }
        }
        ExpenseNameColumn.setCellValueFactory
                (new PropertyValueFactory<Expense, String>("name"));
        ExpenseDateColumn.setCellValueFactory
                (new PropertyValueFactory<Expense, GregorianCalendar>("date"));
        ExpensePriceColumn.setCellValueFactory
                (new PropertyValueFactory<Expense, Double>("price"));
        ExpenseCategoryColumn.setCellValueFactory
                (new PropertyValueFactory<Expense, ExpenseCategory>("category"));

    }

    public void removeExpense() {
        Expense expense = AllExpenseTable.getSelectionModel().getSelectedItem();
        one:for(Account account : this.user.getAccounts()) {
            for(Expense e : account.getExpensesList()) {
                if(e == expense) {
                    account.removeExpense(expense);
                    break one;
                }
            }
        }
        this.refresh();
    }

    private void setRegExpenseTable(){
        ObservableList<RegularExpense> expensesObservableList = FXCollections.observableArrayList();
        for(Account account : user.getAccounts()) {
            for(RegularExpense expense : account.getRegularExpensesList()) {
                RegExpenseTable.getItems().add(expense);
                expensesObservableList.add(expense);
            }
        }
        RegExpColumnName.setCellValueFactory(new PropertyValueFactory<RegularExpense, String>("name"));
        RegExpColumnDate.setCellValueFactory(new PropertyValueFactory<RegularExpense, GregorianCalendar>("date"));
        RegExpColumnPrice.setCellValueFactory(new PropertyValueFactory<RegularExpense, Double>("price"));
        RegExpColumnCategory.setCellValueFactory(
                new PropertyValueFactory<RegularExpense, ExpenseCategory>("category"));
        RegExpColumnLastExpense.setCellValueFactory(
                new PropertyValueFactory<RegularExpense, String>("lastExpense"));
        RegExpColumnFrequency.setCellValueFactory(new PropertyValueFactory<RegularExpense, Integer>("frequency"));

    }

    private void setRegIncomesTable() {
        ObservableList<RegularIncome> incomesObservableList = FXCollections.observableArrayList();
        for(Account account : this.user.getAccounts()) {
            for(RegularIncome income : account.getRegularIncomesList()) {
                RegIncomesTable.getItems().add(income);
                incomesObservableList.add(income);
            }
        }
        RegIncomesColumnName.setCellValueFactory(new PropertyValueFactory<Income, String>("name"));
        RegIncomesColumnDate.setCellValueFactory(new PropertyValueFactory<Income, GregorianCalendar>("date"));
        RegIncomesColumnMoney.setCellValueFactory(new PropertyValueFactory<Income, Double>("money"));
        RegIncomesColumnLastIncome.setCellValueFactory(
                new PropertyValueFactory<Income, GregorianCalendar>("lastIncome"));
        RegIncomesColumnFrequency.setCellValueFactory(new PropertyValueFactory<Income, Integer>("frequency"));
        RegIncomesColumnCategory.setCellValueFactory(
                new PropertyValueFactory<Income, IncomeCategory>("category"));
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
        AllExpenseTable.getItems().clear();
        AllIncomesTable.getItems().clear();
        this.fillMainScene();
        db.closeConnection();
    }

}
