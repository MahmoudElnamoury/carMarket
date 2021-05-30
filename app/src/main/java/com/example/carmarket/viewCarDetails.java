package com.example.carmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Magnifier;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class viewCarDetails extends AppCompatActivity {

    Toolbar toolbar;
    ImageView iv ;
    TextInputEditText et_model,et_color,et_dpl,et_description,et_phone;
    Button btn;
    DatabaseAccess db;
    public static int add_result_code=1;
    public static int edit_result_code=2;
    public static int carId;
    boolean res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        inflating views
        setContentView(R.layout.activity_view_car_details);
        toolbar =findViewById(R.id.car_details_collapsing_toolbar_toolbar);
        setSupportActionBar(toolbar);

        iv=findViewById(R.id.car_details_collapsing_toolbar_iv);
        et_model=findViewById(R.id.car_details_et_model);
        et_color=findViewById(R.id.car_details_et_color);
        et_dpl=findViewById(R.id.car_details_et_dpl);
        et_description=findViewById(R.id.car_details_et_description);
        et_phone=findViewById(R.id.car_details_et_phone);
        btn=findViewById(R.id.car_details_btn);

//      btn listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                String phone=et_phone.getText().toString();
                Uri uri=Uri.parse("tel:"+phone);
                intent.setData(uri);
                startActivity(intent);
            }
        });

//      check permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            String [] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,permissions,0);
        }


        Intent intent=getIntent();
        carId=intent.getIntExtra(MainActivity.car_key,-1);
        //      deal with the state of adding car
        if (carId==-1){
            clearFields();
            btn.setVisibility(View.GONE);
        }
        //      deal with the state of showing car
        else {
            E_D_Fields(false);
            db=DatabaseAccess.get_instance(getBaseContext());
            db.open_db();
            car car=db.get_car(String.valueOf(carId));
            db.close_db();

            if (car.getImage()!=null)
                iv.setImageURI(Uri.parse(car.getImage()));
            et_model.setText(car.getModel());
            et_color.setText(car.getColor());
            et_dpl.setText(car.getDpl()+"");
            et_description.setText(car.getDescription());
            et_phone.setText(car.getPhone()+"");

        }
    }

//    menu of the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_car_menu,menu);
        MenuItem save=menu.findItem(R.id.save);
        MenuItem edit=menu.findItem(R.id.edit);
        MenuItem delete=menu.findItem(R.id.delete);
        if (carId==-1){
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        }
        else {
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }

//  deal with options in menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String model,color,description;
        Double dpl;
        int phone;
        switch (item.getItemId()){
            //click on save button
            case R.id.save:

                model=et_model.getText().toString();
                color=et_color.getText().toString();
                description=et_description.getText().toString();
                dpl=Double.parseDouble(et_dpl.getText().toString());
                phone=Integer.parseInt(et_phone.getText().toString());
                car car=new car(carId,null,model,color,dpl,description,phone);

                db=DatabaseAccess.get_instance(getBaseContext());
                db.open_db();
//                insert new car
                if (carId==-1) {
                    btn.setVisibility(View.GONE);
                    res = db.insert(car);
                    if (res==true)
                        Toast.makeText(this, "car added successfully", Toast.LENGTH_SHORT).show();
                    }
//                update a car
                else {
                    et_phone.setVisibility(View.GONE);
                    res=db.update(car);
                    if (res==true)
                        Toast.makeText(this, "car updated successfully", Toast.LENGTH_SHORT).show();
//                        MainActivity.adapter.notifyDataSetChanged();
                }
                db.close_db();

                setResult(add_result_code,null);
                finish();
                return true;

            //click on save button
            case R.id.edit:
                E_D_Fields(true);
                MenuItem save=toolbar.getMenu().findItem(R.id.save);
                MenuItem edit=toolbar.getMenu().findItem(R.id.edit);
                MenuItem delete=toolbar.getMenu().findItem(R.id.delete);
                save.setVisible(true);
                edit.setVisible(false);
                delete.setVisible(false);

                return true;

            //click on save button
            case R.id.delete:
                E_D_Fields(false);
                db.open_db();
                car=new car(carId,null,null,null,0.0,null,0);


                res=db.delete(car);
                if (res){
                    Toast.makeText(this, "delete car complete ", Toast.LENGTH_SHORT).show();
//                    MainActivity.adapter.notifyDataSetChanged();

                }

                db.close_db();
                setResult(edit_result_code,null);
                finish();
                return true;
        }
        return false;
    }


    public void clearFields(){
        iv.setImageURI(null);
        et_model.setText(null);
        et_color.setText(null);
        et_dpl.setText(null);
        et_description.setText(null);
    }
    public void E_D_Fields(boolean state){
        iv.setEnabled(state);
        et_model.setEnabled(state);
        et_color.setEnabled(state);
        et_dpl.setEnabled(state);
        et_description.setEnabled(state);
        et_phone.setEnabled(state);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "The Permission Is Granted", Toast.LENGTH_SHORT).show();
                }
                else finish();
                break;

        }
    }
}