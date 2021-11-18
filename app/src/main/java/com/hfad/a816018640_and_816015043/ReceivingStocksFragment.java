package com.hfad.a816018640_and_816015043;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a816018640_and_816015043.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ReceivingStocksFragment extends Fragment {

    private SQLiteDatabase db;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_receiving_stocks, container, false);
        // Inflate the layout for this fragment

        Spinner spinner=(Spinner)view.findViewById(R.id.spinner);
        SQLiteOpenHelper helper=new SqlDatabase(getActivity());
        try {
            ArrayList<String>data=new ArrayList<>();
            data.add("Windows10");
            data.add("Windows10");
            data.add("Windows10");
            data.add("Windows10");
            db=helper.getReadableDatabase();

            ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,
            data);
            spinner.setAdapter(adapter);

        }catch(SQLiteException e){
            Toast.makeText(getActivity(),"Database Unavailabe", Toast.LENGTH_LONG).show();
        }
        return view;
    }
}