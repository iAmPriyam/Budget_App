package database;

import accounts.Account;
import expenses.Expense;
import expenses.ExpenseCategory;
import incomes.Income;
import incomes.IncomeCategory;
import users.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class SqliteDb {

    private Connection connection;

    /**
     * SqliteDB constructor - connecting to database via SqliteConnection class
     */
    public SqliteDb() {
        this.connection = SqliteConnection.connector();
        if(connection==null) {
            System.exit(1);
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }

    /**
     * Checking if application is connected to database
     * @return true - connected or false - not connected
     * */
    public boolean isDbConnected() {
        try{
            return !connection.isClosed();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifies password
     * @param password password to verify
     * @return true - password correct false - password incorrect
     */
    public boolean isPasswordCorrect(String password) {
        String query = "SELECT * FROM users WHERE password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    //DATABASE GETTERS
    /**
     * Getting user data based on password and filling User object attributes
     * @param password
     * @return User object with all attributes that are stored in database
     */
    public User getUserData(String password) {
        String query = "SELECT * FROM users WHERE password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                User user = new User(resultSet.getString("name"),resultSet.getInt("id"),resultSet.getDouble("monthlyBudget"));
                ArrayList<Account> accounts = this.getAccounts(user.getId());
                user.setAccounts(accounts);
                return user;
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    /**
     * Getting user's Account. Account's are connected to user by ids
     * @param user_id user's id
     * @return ArrayList of all user's account
     */
    private ArrayList<Account> getAccounts(int user_id) {
        String query = "SELECT * FROM accounts where user_id = ?";
        String userId = Integer.toString(user_id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                int accountId = resultSet.getInt("id");
                String accountName = resultSet.getString("name");
                double accountBalance = resultSet.getDouble("balance");
                Account account = new Account(accountId, accountName,accountBalance);
                account.setExpensesList(this.getAccountExpenses(account.getId()));
                account.setIncomesList(this.getAccountIncomes(account.getId()));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    /**
     *
     * @param id
     * @return
     */
    private ArrayList<Expense> getAccountExpenses(int id) {
        String expensesQuery = "SELECT * FROM expenses WHERE account_id = ?";
        String categoriesQuery = "SELECT * FROM expensesCategories";
        String accountId = Integer.toString(id);
        try {
            PreparedStatement ps = connection.prepareStatement(categoriesQuery);
            ResultSet categoriesSet = ps.executeQuery();
            HashMap<Integer,ExpenseCategory> categories = new HashMap<>();
            while(categoriesSet.next()) {
                int categoryId = categoriesSet.getInt("id");
                String categoryName = categoriesSet.getString("name");
                categories.put(categoryId,new ExpenseCategory(categoryName,categoryId));
            }

            PreparedStatement preparedStatement = connection.prepareStatement(expensesQuery);
            preparedStatement.setString(1,accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Expense> expenses = new ArrayList<>();

            while(resultSet.next()) {
                int expenseId = resultSet.getInt("id");
                String expenseName = resultSet.getString("name");
                double expensePrice = resultSet.getDouble("price");
                int expenseCategoryId = resultSet.getInt("category_id");
                String expenseDate = resultSet.getString("date");
                String[] date = expenseDate.split("-");
                int dayOfMonth = Integer.parseInt(date[2]);
                int month = Integer.parseInt(date[1]);
                int year = Integer.parseInt(date[0]);
                GregorianCalendar expDate = new GregorianCalendar(year,month,dayOfMonth);
                ExpenseCategory category = categories.get(expenseCategoryId);
                Expense expense = new Expense(expenseId,expenseName,expensePrice,category,expDate);
                expenses.add(expense);
            }
            ps.close();
            preparedStatement.close();
            resultSet.close();
            categoriesSet.close();
            return expenses;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    private ArrayList<Income> getAccountIncomes(int id) {
        String accountId = Integer.toString(id);
        String categoriesQuery = "SELECT * FROM incomesCategories";
        String incomeQuery = "SELECT * FROM incomes WHERE account_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(categoriesQuery);
            ResultSet categoriesSet = preparedStatement.executeQuery();
            HashMap<Integer, IncomeCategory> categories = new HashMap<>();
            while(categoriesSet.next()) {
                int categoryId = categoriesSet.getInt("id");
                String categoryName = categoriesSet.getString("name");
                categories.put(categoryId,new IncomeCategory(categoryId,categoryName));
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement(incomeQuery);
            preparedStatement1.setString(1,accountId);
            ResultSet incomesSet = preparedStatement1.executeQuery();
            ArrayList<Income> incomes = new ArrayList<>();

            while(incomesSet.next()) {
                int incomeId = incomesSet.getInt("id");
                int incomeCategoryId = incomesSet.getInt("category_id");
                String incomeName = incomesSet.getString("name");
                double incomeMoney = incomesSet.getDouble("money");
                String incomeDate = incomesSet.getString("date");
                String[] date = incomeDate.split("-|\\.");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int dayOfMonth = Integer.parseInt(date[2]);
                GregorianCalendar incDate = new GregorianCalendar(year,month,dayOfMonth);
                IncomeCategory category = categories.get(incomeCategoryId);
                Income income = new Income(incomeId,incomeName,incomeMoney,category,incDate);
                incomes.add(income);
            }

            preparedStatement1.close();
            categoriesSet.close();
            incomesSet.close();
            preparedStatement.close();
            return incomes;
        } catch(SQLException exc) {
            System.out.println(exc);
            return null;
        }
    }
    //DATABASE INSERTS
    public void insertAccount(int userId, String accountName, double accountBalance) {
        String sql = "INSERT INTO accounts (user_id,name,balance) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setString(2,accountName);
            preparedStatement.setDouble(3,accountBalance);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }
    @SuppressWarnings("Duplicates")
    public void insertExpense(int accountId, int categoryId ,String expenseName, double expensePrice, String date) {
        String sql = "INSERT INTO expenses (account_id,category_id,name,price,date) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountId);
            preparedStatement.setInt(2,categoryId);
            preparedStatement.setString(3,expenseName);
            preparedStatement.setDouble(4,expensePrice);
            preparedStatement.setString(5,date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }
    @SuppressWarnings("Duplicates")
    public void insertIncome(int account_id, int category_id, String name, double money, String date ) {
        String str = "INSERT INTO incomes (account_id, category_id, name, money, date) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(str);
            preparedStatement.setInt(1,account_id);
            preparedStatement.setInt(2,category_id);
            preparedStatement.setString(3,name);
            preparedStatement.setDouble(4,money);
            preparedStatement.setString(5,date);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException exc) {
            System.out.println(exc);
        }
    }
    //DATABASE UPDATE

    /**
     * updates accounts table's row in database
     * @param account account that should be updated
     */
    @SuppressWarnings("Duplicates")
    public void updateAccount(Account account) {
        String string = "UPDATE accounts SET name = ?, balance = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(string);
            preparedStatement.setString(1,account.getAccountName());
            preparedStatement.setDouble(2,account.getAccountBalance());
            preparedStatement.setInt(3,account.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }

    @SuppressWarnings("Duplicates")
    public void updateAccount(Account account, double money) {
        String string = "UPDATE accounts SET name = ?, balance = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(string);
            preparedStatement.setString(1,account.getAccountName());
            preparedStatement.setDouble(2,money);
            preparedStatement.setInt(3,account.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }

    /**
     * Updates expenses table's row in database
     * @param expense expense that should be updated
     */
    public void updateExpense(Expense expense) {
        String sql = "UPDATE expense SET name = ?, price = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,expense.getName());
            preparedStatement.setDouble(2,expense.getPrice());
            preparedStatement.setInt(3,expense.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    /**
     * Updates users table's row in database
     * @param user User that should be updated
     */
    public void updateUser(User user) {
        String s = "UPDATE users SET name = ?, monthlyBudget = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setDouble(2,user.getMonthlyBudget());
            preparedStatement.setInt(3,user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }
    public ArrayList<ExpenseCategory> getExpenseCategories() {
        String query = "SELECT * FROM expensesCategories";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ExpenseCategory> categories = new ArrayList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                ExpenseCategory category = new ExpenseCategory(name,id);
                categories.add(category);
            }
            return categories;

        } catch (SQLException exc) {
            System.out.println(exc);
            return null;
        }
    }
    public ArrayList<IncomeCategory> getIncomeCategories() {
        String query = "SELECT * FROM incomesCategories";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<IncomeCategory> categories = new ArrayList<>();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                IncomeCategory category = new IncomeCategory(id,name);
                categories.add(category);
            }
            return categories;
        } catch (SQLException exc) {
            System.out.println(exc);
            return null;
        }
    }
}
