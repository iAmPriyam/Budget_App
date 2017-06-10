package accounts;

import expenses.Expense;

public class CreditAccount extends Account {
    private double interest;

    public CreditAccount(int id, String accountName, double accountBalance, double interest) {
        super(id,accountName,accountBalance);
        this.interest = interest;
    }

    private Expense computeExpense(Expense expense) {
        if(this.getAccountBalance()-expense.getPrice()>0) {
            return expense;
        }  else {
            expense.setPrice(expense.getPrice()+expense.getPrice()*interest);
            return expense;
        }
    }

    @Override
    public void addExpense(Expense expense) {
        expense = this.computeExpense(expense);
        this.getExpensesList().add(expense);
        this.reduceAccountBalance(expense.getPrice());
    }

//    @Override
//    public String toString() {
//        return super.toString() + "CreditAccount{" +
//                "interest=" + interest +
//                '}';
//    }
}
