package com.example.uas2pampa;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {
    private TextView txtJSON;
    private Button btnJSON;
    private  RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mQueue = Volley.newRequestQueue(this);
        txtJSON = findViewById(R.id.txtJson);
        btnJSON = findViewById(R.id.btnJson);

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uraiJson();
            }
        });

    }

    private void uraiJson() {
        String nama = getIntent().getStringExtra("NAME_SESSION");
        String url = "http://192.168.0.21/MPA/uasPA&MPA/show.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            txtJSON.append("Nama : "+response.getString("nama : ") + "\n" + "Hobby : ");
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject mahasantri = jsonArray.getJSONObject(i);
                                String nama = mahasantri.getString("nama");
                                txtJSON.append(nama);
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity2.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity2.this,"" + error, Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);
    }
}