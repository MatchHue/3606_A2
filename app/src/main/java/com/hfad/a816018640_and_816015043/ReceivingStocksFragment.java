package com.hfad.a816018640_and_816015043;

import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a816018640_and_816015043.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ReceivingStocksFragment extends Fragment {

    private SQLiteDatabase db;
    private  Cursor cursor;
    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_receiving_stocks, container, false);
        // Inflate the layout for this fragment

        Spinner spinner=(Spinner)view.findViewById(R.id.spinner);
        SQLiteOpenHelper helper=new SqlDatabase(getActivity());
        try {
            ArrayList<String>data=new ArrayList<>();
            db=helper.getReadableDatabase();
            cursor=db.rawQuery("select * from Product",null,null);
            while(cursor.moveToNext()){
                data.add(cursor.getString(1));
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,
            data);
            spinner.setAdapter(adapter);

        }catch(SQLiteException e){
            Toast.makeText(getActivity(),"Database Unavailabe", Toast.LENGTH_LONG).show();
        }

        btn=view.findViewById(R.id.update);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String spinnerText=spinner.getSelectedItem().toString();
                    EditText editText=(EditText)getActivity().findViewById(R.id.edittext);
                    String text=editText.getText().toString();

                    SQLiteOpenHelper upHelp=new SqlDatabase(getActivity());
                    SQLiteDatabase uDB=upHelp.getReadableDatabase();
                    Cursor c=uDB.rawQuery("Select StockOnHand,StockInTransit FROM Product where name=?",
                            new String[]{spinnerText});
                  c.moveToFirst();
                  String soh=c.getString(0);
                  int SiT=Integer.parseInt(c.getString(1));
                  int sohV=Integer.parseInt(soh);
                  int newSoH=sohV+Integer.parseInt(text);
                  String newSohValue=String.valueOf(newSoH);
                  int newSiT;
                  if(SiT<Integer.parseInt(text)){
                      newSiT=0;
                      Toast.makeText(getActivity(),"EDITTEXT LARGER",Toast.LENGTH_SHORT).show();
                  }else{
                      newSiT=SiT-Integer.parseInt(text);
                      Toast.makeText(getActivity(),"EDITTEXT SMALLER",Toast.LENGTH_SHORT).show();
                  }
                ContentValues cv=new ContentValues();
                  cv.put("StockOnHand",newSohValue);
                  cv.put("StockInTransit",newSiT);
                  uDB.update("Product",cv,"Name=?",new String[]{spinnerText});
                Toast.makeText(getActivity(),newSohValue,Toast.LENGTH_SHORT).show();
                uDB.close();
                c.close();
            }
        });

        return view;
    }

    public void updateDb(View view){

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }


}