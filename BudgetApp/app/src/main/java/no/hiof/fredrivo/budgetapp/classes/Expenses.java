package no.hiof.fredrivo.budgetapp.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Expenses {

    private int sum;
    private LocalDate date;
    private String location;
    private String description;
    private String category;
    private static ArrayList<Expenses> expensesArrayList = new ArrayList<>();


    // Test function
//    public Expenses(int sum, String location, String description, String category) {
//        this.sum = sum;
//        this.date = date;
//        this.location = location;
//        this.description = description;
//        this.category = category;
//
//        expensesArrayList.add(this);
//    }


   public Expenses(int sum, LocalDate date, String location, String description, String category) {
        this.sum = sum;
        this.date = date;
        this.location = location;
        this.description = description;
        this.category = category;

        expensesArrayList.add(this);
    }

    public Expenses() {}

    public int getSum() { return sum; }
    public void setSum(int sum) { this.sum = sum; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public static List<Expenses> getExpenseList() {
       //Test data for å teste detailed view.
        /* List<Expenses> expenseList = new ArrayList<>();

         int[] testSums = {123, 456, 789, 12, 34, 56, 78, 89};

         //TODO: satte midlertidig API til 26 i gradle.app for å ikke få feilmld på LocalDate, må fikses så vi kan ha API 21
         LocalDate[] testDates = {
                 LocalDate.of(2018,11,21),
                 LocalDate.of(2018,11,21),
                 LocalDate.of(2018,11,21),
                 LocalDate.of(2018,11,21),
                 LocalDate.of(2018,11,21),
                 LocalDate.of(2018,11,21),
                 LocalDate.of(2018,11,21),
                 LocalDate.of(2018,11,21)
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

            expenseList.add(ex);
        }*/

        return expensesArrayList;
    }


}

