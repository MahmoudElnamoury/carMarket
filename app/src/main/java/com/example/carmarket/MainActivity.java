package com.example.carmarket;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv;
    FloatingActionButton fab;
    public static myAdapter adapter;
    DatabaseAccess db;
    ArrayList<car> cars;
    public static final String car_key="car_key";
    public static int edit_req_code=1;
    public static int add_req_code=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab=findViewById(R.id.main_fab);
        toolbar =findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        rv=findViewById(R.id.main_rv);

        db=DatabaseAccess.get_instance(getBaseContext());
        db.open_db();
        cars=db.get_cars();
        db.close_db();

        adapter=new myAdapter(cars, new onRecyclerViewListener() {
            @Override
            public void Onclick(int carId) {
                Intent intent=new Intent(getBaseContext(),viewCarDetails.class);
                intent.putExtra(car_key,carId);
                startActivityForResult(intent,edit_req_code);
            }
        });
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),viewCarDetails.class);
                startActivityForResult(intent,add_req_code);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainenu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                db.open_db();
                cars=db.get_cars(query);
                db.close_db();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                db.open_db();
                cars=db.get_cars(newText);
                db.close_db();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MainActivity.this, "cancel clicked ", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==add_req_code ){
            db.open_db();
            cars=db.get_cars();
            db.getClass();
            adapter.setCars(cars);
            adapter.notifyDataSetChanged();
        }
        else if (resultCode == viewCarDetails.edit_result_code || resultCode==viewCarDetails.add_result_code) {
            db.open_db();
            cars=db.get_cars();
            db.close_db();
            adapter.setCars(cars);
            adapter.notifyDataSetChanged();

        }
    }
}