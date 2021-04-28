package com.easy.customdialog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
//import Aniket Kumar
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    // Global Variables:
    DialogClass dialogClass = DialogClass.getInstance();
    RelativeLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressLayout = findViewById(R.id.SHOW_PROGRESS);
        progressLayout.setVisibility(View.GONE);

        // Checking for Internet Connection:
        if (isOnline()) {
            progressLayout.setVisibility(View.VISIBLE);
            fetchApiData();
        } else {
            progressLayout.setVisibility(View.GONE);
            Toast.makeText(this, "Check Your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void fetchApiData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://backend-test-zypher.herokuapp.com/testData";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, response -> {
                    progressLayout.setVisibility(View.GONE);
                    try {
                        dialogClass.AlertDialog(MainActivity.this, response.getString("title"), response.getString("imageURL"), response.getString("success_url"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    progressLayout.setVisibility(View.GONE);
                    // TODO: Handle error
                    Log.e("TAG", error.toString());
                });
        queue.add(jsonObjectRequest);
    }
}