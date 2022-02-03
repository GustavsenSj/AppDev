package no.ntnu.crudrest;

import java.util.Date;

public class Loan {
    private int loanID;
    private int borrowerID;
    private int bookID;
    private int branchID;
    private String date;
    private String dueDate;

    public Loan(int loanID, int borrowerID, int bookID, int branchID, String date, String dueDate) {
        this.loanID = loanID;
        this.borrowerID = borrowerID;
        this.bookID = bookID;
        this.branchID = branchID;
        this.date = date;
        this.dueDate = dueDate;
    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isValid() {
        return loanID > 0;
    }

    public Integer getId() {
        return loanID;
    }
}
