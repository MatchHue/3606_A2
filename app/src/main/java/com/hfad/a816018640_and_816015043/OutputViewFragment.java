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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a816018640_and_816015043.R;

import java.util.ArrayList;


public class OutputViewFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_output_view, container, false);

        TextView listView=(TextView) view.findViewById(R.id.OutputView);
        SQLiteOpenHelper helper=new SqlDatabase(getActivity());
        StringBuilder Sb=new StringBuilder();
        try {
            db=helper.getReadableDatabase();
            cursor=db.rawQuery("select * from Product",null,null);
            while(cursor.moveToNext()){
                Sb.append("||");
                Sb.append("\tName \t");
                Sb.append(cursor.getString(1));
                Sb.append("\tStockOnHand \t");
                Sb.append(cursor.getString(2));
                Sb.append("\tStockInTransit \t");
                Sb.append(cursor.getString(3));
                Sb.append("\tReorderQuantity \t");
                Sb.append(cursor.getString(5));
                Sb.append("\tReorderAmount \t");
                Sb.append(cursor.getString(6));
                int price=Integer.parseInt(cursor.getString(4));
                int soh=Integer.parseInt(cursor.getString(2));
                int sit=Integer.parseInt(cursor.getString(3));
                int valuation=price*soh;
                int itval=price*sit;
                Sb.append("\tValuation :$"+valuation+"\t");
                Sb.append("\t In-Transit Valuation :$"+itval+"\t");
                Sb.append("||");
                Sb.append("\n");
            }
            listView.setText(Sb);

        }catch(SQLiteException e){
            Toast.makeText(getActivity(),"Database Unavailable", Toast.LENGTH_LONG).show();
        }

    return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

}