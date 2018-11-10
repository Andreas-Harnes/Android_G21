package no.hiof.fredrivo.budgetapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.hiof.fredrivo.budgetapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class week_tab extends Fragment {

    private static final String TAG = "Tab2frag";
    public week_tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_tab, container, false);
    }

}
