package com.example.shantacse.sendingdatatoserver;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ed1,ed2,ed3;
    Button btn;
    String url = "http://192.168.43.54/api/insert.php";
    AlertDialog.Builder builder;
    String data1;
    String data2;
    String data3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = findViewById(R.id.editText1);
        ed2 = findViewById(R.id.editText2);
        ed3 = findViewById(R.id.editText3);
        btn = findViewById(R.id.button);
        builder = new AlertDialog.Builder(this);


        data1 = ed1.getText().toString();
        data2 = ed2.getText().toString();
        data3 = ed3.getText().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertdata();

            }
        });

    }

    public void insertdata()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("error", response);

                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String getcode = jsonObject.getString("code");
                    String getmessenger = jsonObject.getString("message");
                    builder.setTitle(getcode);
                    builder.setCancelable(true);
                    builder.setMessage(getmessenger);
                    builder.show();


                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error is: "+e, Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error is: "+error, Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> mp = new HashMap<String, String>();
                mp.put("name",data1);
                mp.put("email",data2);
                mp.put("phone",data3);
                return mp;
            }
        };

        com.example.shantacse.sendingdatatoserver.AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
