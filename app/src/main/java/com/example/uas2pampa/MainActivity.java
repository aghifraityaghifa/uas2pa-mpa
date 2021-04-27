package com.example.uas2pampa;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.AuthFailureError;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText et_username, et_password;
    private String username, password;
    private String url = "http://192.168.0.21/MPA/uasPA&MPA/show.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.btn_login);
        et_username= findViewById(R.id.et_username);
        et_password= findViewById(R.id.et_password);
        username = password = "";

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (!username.equals("") && !password.equals("")) {
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.21/MPA/uasPA&MPA/login.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("success")) {
                                Intent intent= new Intent(getApplicationContext(), MainActivity2.class);
                                intent.putExtra("NAME_SESSION", username);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "username dan password yang anda masukkan salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", et_username.getText().toString());
                    params.put("password", et_password.getText().toString());
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(request);
        } else {
            Toast.makeText(getApplicationContext(), "Masukkan username dan password anda", Toast.LENGTH_SHORT).show();
        }
    }
}