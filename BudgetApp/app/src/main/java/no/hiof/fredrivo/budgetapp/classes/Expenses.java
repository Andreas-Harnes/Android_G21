package no.hiof.fredrivo.budgetapp.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Expenses {

    private int sum;
    private Date date;
    private String location;
    private String description;
    private String category;
    //private static ArrayList<Expenses> expensesList;

   public Expenses(int sum, Date date, String location, String description, String category) {
        this.sum = sum;
        this.date = date;
        this.location = location;
        this.description = description;
        this.category = category;

        //Adds the current object into the ArrayList
        //expensesList.add(this);
    }

    // Funksjoner for expenses objekter

    public ArrayList<Expenses> getExpensesList() {
        return expensesList;
    }

    public static List<Expenses> getExpenseList() {
       //Test data for å teste detailed view.
         List<Expenses> expenseList = new ArrayList<>();

         int[] testSums = {123, 456, 789, 12, 34, 56, 78, 89};

         Date[] testDates = {
                 new Date(2018,11,21),
                 new Date(2018,11,21),
                 new Date(2018,11,21),
                 new Date(2018,11,21),
                 new Date(2018,11,21),
                 new Date(2018,11,21),
                 new Date(2018,11,21),
                 new Date(2018,11,21)
         };

         String[] testLocations = {
                 "Rema 1000",
                 "Spar",
                 "H&M",
                 "Komplett.no",
                 "Ark bokhandel",
                 "Narvesen",
                 "Netflix",
                 "Vipps"
         };

        String[] testDescriptions = {
                "desc",
                "desc",
                "desc",
                "desc",
                "desc",
                "desc",
                "desc",
                "desc"
        };

        String[] testCategories = {
                "Groceries",
                "Equipment",
                "Clothes",
                "Electronics",
                "Hobby",
                "Food",
                "Monthly expenses",
                "Entertainment"
        };

        for (int i = 0; i < testSums.length; i++) {

            Expenses ex = new Expenses();

            ex.setSum(testSums[i]);
            ex.setDate(testDates[i]);
            ex.setLocation(testLocations[i]);
            ex.setDescription(testDescriptions[i]);
            ex.setCategory(testCategories[i]);
        }

        return expenseList;
    }

    public int getSum() { return sum; }
    public void setSum(int sum) { this.sum = sum; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}

