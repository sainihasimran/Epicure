package com.cegep.epicure;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.cegep.epicure.model.SignUpRequest;
import com.cegep.epicure.model.User;
import com.cegep.epicure.network.NetworkInteractor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private String selectedGender;

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
                (parent, view1, position, id) -> selectedGender = genders.get(position));

        findViewById(R.id.btLogin).setOnClickListener(v -> {
            SignUpRequest signUpRequest = new SignUpRequest();

            String firstName = ((EditText) findViewById(R.id.firstName_editText)).getText().toString();
            if (TextUtils.isEmpty(firstName)) {
                Toast.makeText(SignUpActivity.this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            signUpRequest.FirstName = firstName;

            String lastName = ((EditText) findViewById(R.id.lastName_editText)).getText().toString();
            if (TextUtils.isEmpty(lastName)) {
                Toast.makeText(SignUpActivity.this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            signUpRequest.LastName = lastName;

            String phoneNumber = ((EditText) findViewById(R.id.phoneNumber_editText)).getText().toString();
            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(SignUpActivity.this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            signUpRequest.PhoneNumber = phoneNumber;

            String email = ((EditText) findViewById(R.id.email_editText)).getText().toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(SignUpActivity.this, "Invalid email entered", Toast.LENGTH_SHORT).show();
                return;
            }
            signUpRequest.Email = email;

            try {
                signUpRequest.Age = Integer.parseInt(((EditText) findViewById(R.id.age_editText)).getText().toString());
            } catch (Exception e) {
                Toast.makeText(SignUpActivity.this, "Age cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(selectedGender)) {
                Toast.makeText(SignUpActivity.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                return;
            }
            signUpRequest.Gender = selectedGender;

            String password = ((EditText) findViewById(R.id.password_editText)).getText().toString();
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(SignUpActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            String confirmPassword = ((EditText) findViewById(R.id.confirm_password_editText)).getText().toString();
            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            signUpRequest.Password = password;

            new SignUpAsyncTask().execute(signUpRequest);
        });
    }

    private class SignUpAsyncTask extends AsyncTask<SignUpRequest, Void, Boolean> {

        @Override
        protected Boolean doInBackground(SignUpRequest... signUpRequests) {
            try {
                Response<User> userResponse = NetworkInteractor.getService().createUser(signUpRequests[0]).execute();
                if (userResponse.isSuccessful()) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
