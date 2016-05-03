package com.colt.android041_parse_json_using_volley_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button buttonReq;
    private TextView textView;
    private RequestQueue requestQueue;
    private String jsonURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonReq = (Button) findViewById(R.id.buttonReq);
        textView = (TextView) findViewById(R.id.textView);
        requestQueue = Volley.newRequestQueue(this);
        jsonURL = "http://mysafeinfo.com/api/data?list=englishmonarchs&format=json"; //Sample test JSON file.

        buttonReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        jsonURL,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        String nm = jsonObject.getString("nm");
                                        String cty = jsonObject.getString("cty");
                                        String hse = jsonObject.getString("hse");
                                        String yrs = jsonObject.getString("yrs");
                                        textView.append("\nName: " + nm + "\nCity: " + cty + "\nHouse: " + hse + "\nYears: " + yrs + "\n");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("VOLLEY", "ERROR");
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                requestQueue.add(jsonArrayRequest);
            }
        });
    }

}