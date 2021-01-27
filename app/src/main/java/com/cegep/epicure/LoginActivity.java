package com.cegep.epicure;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import com.cegep.epicure.model.User;
import com.cegep.epicure.network.NetworkInteractor;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailEditText = findViewById(R.id.email_editText);
        EditText passwordEditText = findViewById(R.id.password_editText);

        btLogin = findViewById(R.id.btLogin);
        btLogin.setOnClickListener(view -> {
            //todo remove following
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            // TODO: 26/01/21 uncomment following
//            String email = emailEditText.getText().toString();
//            String password = passwordEditText.getText().toString();
//
//            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                Toast.makeText(this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (TextUtils.isEmpty(password)) {
//                Toast.makeText(this, "Password must 8 or more characters", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            new LoginAsyncTask().execute(Pair.create(email, password));
        });

        findViewById(R.id.tvSignup).setOnClickListener(view -> {
                                                           Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
                                                           startActivity(signup);
                                                       }
        );
    }

    private class LoginAsyncTask extends AsyncTask<Pair<String, String>, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Pair<String, String>... pairs) {
            String email = pairs[0].first;
            String password = pairs[0].second;

            try {
                boolean isValidCredentials = false;
                Response<List<User>> usersResponse = NetworkInteractor.getService().fetchUsersList().execute();
                if (usersResponse.isSuccessful()) {
                    List<User> usersList = usersResponse.body();
                    for (User user : usersList) {
                        if (user.Email.equals(email) && user.Password.equals(password)) {
                            isValidCredentials = true;
                            break;
                        }
                    }
                }
                return isValidCredentials;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }
}