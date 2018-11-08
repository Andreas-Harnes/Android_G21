package no.hiof.fredrivo.budgetapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import no.hiof.fredrivo.budgetapp.R;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class DayTabAdapter extends RecyclerView.Adapter<DayTabAdapter.ExpenseViewHolder> {

    private List<Expenses> expenseList;
    private LayoutInflater layoutInflater;

    public DayTabAdapter(Context context, List<Expenses> expenseList) {
        layoutInflater = LayoutInflater.from(context);
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public DayTabAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.day_tab_list_item, viewGroup, false);

        return new DayTabAdapter.ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DayTabAdapter.ExpenseViewHolder expenseViewHolder, int i) {
        Expenses ex = expenseList.get(i);

        expenseViewHolder.setExpenses(ex);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDaySum;
        private TextView txtDayCategory;

        public ExpenseViewHolder(View v) {
            super(v);
            txtDaySum = v.findViewById(R.id.txtDaySum);
            txtDayCategory = v.findViewById(R.id.txtDayCategory);
        }

        public void setExpenses (Expenses ex) {
            txtDaySum.setText(ex.getSum()+"");
            txtDayCategory.setText(ex.getCategory());
        }
    }

}
