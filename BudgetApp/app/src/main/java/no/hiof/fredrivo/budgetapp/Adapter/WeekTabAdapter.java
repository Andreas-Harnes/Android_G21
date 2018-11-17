package no.hiof.fredrivo.budgetapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import no.hiof.fredrivo.budgetapp.R;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class WeekTabAdapter extends RecyclerView.Adapter<WeekTabAdapter.WeekExpenseViewHolder> {

    private List<Expenses> expenseList;

    //konstruktør
    public WeekTabAdapter(List<Expenses> expenseList) { this.expenseList = expenseList; }

    //setter ViewHolder til week_tab_list_item.xml
    @NonNull
    @Override
    public WeekTabAdapter.WeekExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.week_tab_list_item, viewGroup, false);

        return new WeekTabAdapter.WeekExpenseViewHolder(v);
    }

    //henter posisjon i expenseList, kaller setExpenses-metoden
    @Override
    public void onBindViewHolder(WeekTabAdapter.WeekExpenseViewHolder expenseViewHolder, int i) {
        Expenses ex = expenseList.get(i);
        expenseViewHolder.setExpenses(ex);
    }

    @Override
    public int getItemCount() { return expenseList.size(); }

    //indre klasse for fylling av CardView/ViewHolder
    class WeekExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDaySum;
        private TextView txtDayCategory;

        //konstruktør som tar imot ViewHolder og henter info fra den
        public WeekExpenseViewHolder(View v) {
            super(v);
            txtDaySum = v.findViewById(R.id.txtDaySum);
            txtDayCategory = v.findViewById(R.id.txtDayCategory);
        }

        //setter innhold i view til å være info fra expenselist
        public void setExpenses (Expenses ex) {
            txtDaySum.setText(ex.getSum()+ ",-");
            txtDayCategory.setText(ex.getCategory());
        }
    }
}