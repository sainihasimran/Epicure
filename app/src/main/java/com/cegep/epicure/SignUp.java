package com.cegep.epicure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        List<String> genders = new ArrayList<>();
        genders.add("M");
        genders.add("F");
        AutoCompleteTextView genderDropDown = findViewById(R.id.genderDropDown);
        ArrayAdapter<String> genderDropDownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genders);
        genderDropDown.setAdapter(genderDropDownAdapter);
        genderDropDown.setOnItemClickListener(
                (parent, view1, position, id) -> Toast.makeText(this, "Gender selected: " + genders.get(position), Toast.LENGTH_SHORT)
                        .show());



    }
}
