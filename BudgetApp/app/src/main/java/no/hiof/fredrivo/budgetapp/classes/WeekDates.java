package no.hiof.fredrivo.budgetapp.classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeekDates {
    private Calendar calendar;
    private List<String> dates;


    public WeekDates() {
        calendar = Calendar.getInstance();

        dates = new ArrayList<>();
    }

    public List<String> getDates(){
        int year = calendar.get(Calendar.YEAR);
        int intMonth = calendar.get(Calendar.MONTH);
        int intDay = calendar.get(Calendar.DAY_OF_MONTH);



        return dates;
    }
}
