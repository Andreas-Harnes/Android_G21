package no.hiof.fredrivo.budgetapp.classes;

import java.util.Date;

public class Expenses {

    private int sum;
    private Date date;
    private String location;
    private String description;
    private String category;

    public Expenses(int sum, Date date, String location, String description, String category) {
        this.sum = sum;
        this.date = date;
        this.location = location;
        this.description = description;
        this.category = category;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
