package no.hiof.fredrivo.budgetapp.classes;

import java.util.ArrayList;
import java.util.List;

public class Expenses {

//    private String id;
    private int sum;
    private String date;
    private String location;
    private String description;
    private String category;
    private static List<Expenses> expensesArrayList = new ArrayList<>();


   public Expenses(int sum, String date, String location, String description, String category) {
//        this.id = id;
        this.sum = sum;
        this.date = date;
        this.location = location;
        this.description = description;
        this.category = category;

        expensesArrayList.add(this);
    }

    public Expenses() {}

//    public String getId() { return id; }
//    public void setId(String id) { this.id = id; }

    public int getSum() { return sum; }
    public void setSum(int sum) { this.sum = sum; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public static List<Expenses> getExpenseList() { return expensesArrayList; }
}

