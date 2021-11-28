package com.hfad.a816018640_and_816015043;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a816018640_and_816015043.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void receiveClick(View view){
        replaceFragment(new ReceivingStocksFragment());
    }

    public void orderClick(View View){
        replaceFragment(new OrderingStocksFragment());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentView,fragment);
        fragmentTransaction.commit();

    }

    String url="http://192.168.100.192/starbuzz/syncdrinks.php";

    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> ProductList;
        ProductList = new ArrayList<HashMap<String, String>>();
        SQLiteOpenHelper sqlDataHelper = new SqlDatabase(MainActivity.this);
        SQLiteDatabase database = sqlDataHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from Product", null,null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("_id", cursor.getString(0));
                ProductList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(ProductList);
    }

    //Example of updating the DB on the server using a POST request
    public void onSync(View view) {
        Toast.makeText(MainActivity.this, "Attempting to sync...", Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("productsJSON", composeJSONfromSQLite());
        client.post(url, params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String byteToString = null;
                try {
                    byteToString = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
               Toast.makeText(MainActivity.this, "Successful response: " + byteToString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occurred! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}