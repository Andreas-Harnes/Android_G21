package no.hiof.fredrivo.budgetapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class DetailActivityAdapter extends RecyclerView.Adapter<DetailActivityAdapter.ExpenseViewHolder> {

    private List<Expenses> expensesList;
    private LayoutInflater layoutInflater;

    public DetailActivityAdapter(Context context, ArrayList<Expenses> expensesList) {
        layoutInflater = LayoutInflater.from(context);
        this.expensesList = expensesList;
    }


    @NonNull
    @Override
    public DetailActivityAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.detail_list_item, viewGroup, false);

        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailActivityAdapter.ExpenseViewHolder expenseViewHolder, int i) {
        Expenses ex = expensesList.get(i);

        expenseViewHolder.setExpenses(ex);
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDetailSum;
        private TextView txtDetailDate;
        private TextView txtDetailLocation;

        public ExpenseViewHolder(View v) {
            super(v);
            txtDetailSum = v.findViewById(R.id.txtDetailSum);
            txtDetailDate = v.findViewById(R.id.txtDetailDate);
            txtDetailLocation = v.findViewById(R.id.txtDetailLocation);
        }

        public void setExpenses (Expenses ex) {
            txtDetailSum.setText(ex.getSum());
            txtDetailDate.setText(ex.getDate().toString());
            txtDetailLocation.setText(ex.getLocation());
        }
    }


}
