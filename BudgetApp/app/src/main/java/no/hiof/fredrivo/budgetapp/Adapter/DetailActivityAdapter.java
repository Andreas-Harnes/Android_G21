package no.hiof.fredrivo.budgetapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import no.hiof.fredrivo.budgetapp.R;
import no.hiof.fredrivo.budgetapp.classes.Expenses;

public class DetailActivityAdapter extends RecyclerView.Adapter<DetailActivityAdapter.ExpenseViewHolder> {

    private List<Expenses> expenseList;
    private LayoutInflater layoutInflater;

    public DetailActivityAdapter(Context context, List<Expenses> expenseList) {
        layoutInflater = LayoutInflater.from(context);
        this.expenseList = expenseList;

    }


    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.i("RecycleView", "onCreateViewHolder called " + i);
        View v = layoutInflater.inflate(R.layout.detail_list_item, viewGroup, false);

        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder expenseViewHolder, int i) {
        Log.i("RecycleView", "onBindViewHolder called " + i);
        Expenses ex = expenseList.get(i);

        expenseViewHolder.setExpenses(ex);
    }

    @Override
    public int getItemCount() {
        Log.i("expenseList", "expenselist has " + expenseList.size() + " elements");
        return expenseList.size();
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
            txtDetailSum.setText(ex.getSum()+"");
            txtDetailDate.setText(ex.getDate().toString());
            txtDetailLocation.setText(ex.getLocation());
        }
    }


}
