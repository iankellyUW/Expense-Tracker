package com.example.expensetracker3;

// this object is used for storage of data intermediate to the SQL database
public class ExpenseData {
    private String Name;
    private String Category;
    private String Date;
    private Float Amount;
    private String Note;
    private Long _id;

    public ExpenseData(String name, String category, String date, Float amount, String note) {
        this._id = null;
        this.Name = name;
        this.Category = category;
        this.Date = date;
        this.Amount = amount;
        this.Note = note;
    }

    public ExpenseData(){
    }

    public String toString() {
        return "ExpenseData{" +
                "Name='" + Name + '\'' +
                ", Category='" + Category + '\'' +
                ", Date='" + Date + '\'' +
                ", Amount=" + Amount +
                ", Note='" + Note + '\'' +
                ", _id=" + _id +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
