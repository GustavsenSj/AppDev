package no.ntnu.crudrest;

public class Borrowers {
    private String name;
    private int borrowerID;
    private String address;
    private int phoneNumber;


    public Borrowers(String name, int borrowerID, String address, int phoneNumber) {
        this.name = name;
        this.borrowerID = borrowerID;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }


    public boolean isValid() {
        return borrowerID > 0;
    }
}
