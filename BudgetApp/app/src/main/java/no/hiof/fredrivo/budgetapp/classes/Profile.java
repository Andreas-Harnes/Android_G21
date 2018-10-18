package no.hiof.fredrivo.budgetapp.classes;

import java.util.ArrayList;

public class Profile {
    private String profileFirstName;
    private String profileLastName;
    private int incomePerMonth;
    private int savePerMonth;
    private int expensesPerMonth;
    private ArrayList<String> categoryToSave;

    public Profile(String profileFirstName, String profileLastName, int incomePerMonth, int savePerMonth, int expensesPerMonth, ArrayList<String> category) {
        this.profileFirstName = profileFirstName;
        this.profileLastName = profileLastName;
        this.incomePerMonth = incomePerMonth;
        this.savePerMonth = savePerMonth;
        this.expensesPerMonth = expensesPerMonth;
        this.categoryToSave = category;
    }

    public String getProfileFirstName() {
        return profileFirstName;
    }

    public void setProfileFirstName(String profileFirstName) {
        this.profileFirstName = profileFirstName;
    }

    public String getProfileLastName() {
        return profileLastName;
    }

    public void setProfileLastName(String profileLastName) {
        this.profileLastName = profileLastName;
    }

    public int getIncomePerMonth() {
        return incomePerMonth;
    }

    public void setIncomePerMonth(int incomePerMonth) {
        this.incomePerMonth = incomePerMonth;
    }

    public int getSavePerMonth() {
        return savePerMonth;
    }

    public void setSavePerMonth(int savePerMonth) {
        this.savePerMonth = savePerMonth;
    }

    public int getExpensesPerMonth() {
        return expensesPerMonth;
    }

    public void setExpensesPerMonth(int expensesPerMonth) {
        this.expensesPerMonth = expensesPerMonth;
    }

    public ArrayList<String> getCategoryToSave() {
        return categoryToSave;
    }

    public void setCategoryToSave(ArrayList<String> categoryToSave) {
        this.categoryToSave = categoryToSave;
    }
}
