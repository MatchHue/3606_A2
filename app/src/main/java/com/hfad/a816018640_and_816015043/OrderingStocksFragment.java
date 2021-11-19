package com.hfad.a816018640_and_816015043;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a816018640_and_816015043.R;

import java.util.ArrayList;
import java.util.List;


public class OrderingStocksFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ordering_stocks, container, false);

        Spinner spinner=(Spinner)view.findViewById(R.id.Orderspinner);
        TextView listView=(TextView) view.findViewById(R.id.ListProducts);
        SQLiteOpenHelper helper=new SqlDatabase(getActivity());
        StringBuilder Sb=new StringBuilder();
        try {
            ArrayList<String> data=new ArrayList<>();
            db=helper.getReadableDatabase();
            cursor=db.rawQuery("select * from Product",null,null);
            while(cursor.moveToNext()){
                data.add(cursor.getString(1));
                Sb.append("\tName \t");
                Sb.append(cursor.getString(1));
                Sb.append("\tStockOnHand \t");
                Sb.append(cursor.getString(2));
                Sb.append("\tStockInTransit \t");
                Sb.append(cursor.getString(3));
                Sb.append("\tReorderQunaity \t");
                Sb.append(cursor.getString(5));
                Sb.append("\tReorderAmount \t");
                Sb.append(cursor.getString(6));
                Sb.append("\n");
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,
                    data);
            listView.setText(Sb);
            spinner.setAdapter(adapter);

        }catch(SQLiteException e){
            Toast.makeText(getActivity(),"Database Unavailabe", Toast.LENGTH_LONG).show();
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