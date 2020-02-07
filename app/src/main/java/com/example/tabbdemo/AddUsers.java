package com.example.tabbdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUsers extends AppCompatActivity {
    EditText fristname;
    EditText lastname;
    EditText Phone_number;
    Button Add;
    NewUserDBHelper newUserDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        newUserDBHelper = new NewUserDBHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);
        fristname =findViewById(R.id.firstname_dialog_editTxt);
        lastname = findViewById(R.id.lastname_dialog_editTxt);
        Phone_number = findViewById(R.id.phone_dialog_editTxt);
        Add = findViewById(R.id.btn_add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = fristname.getText().toString();
                String lname = lastname.getText().toString();
                String phone_number = Phone_number.getText().toString();
                boolean result = newUserDBHelper.addUser(fname,lname,phone_number);
                if (result){
                    Toast.makeText(getApplicationContext(),"The user inserted succesfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"The user not inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
