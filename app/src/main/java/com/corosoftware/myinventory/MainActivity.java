package com.corosoftware.myinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // URL archivo real
    // https://docs.google.com/spreadsheets/d/1xXWnPwy2ElBKb4oFzLSGFsMmmoM4XH2Kd_wxwNCSOQ4/edit?usp=sharing

    // URL ya editado con el id del archivo real
    public String fileURL = "https://docs.google.com/spreadsheets/u/0/d/1xXWnPwy2ElBKb4oFzLSGFsMmmoM4XH2Kd_wxwNCSOQ4/gviz/tq?tqx=out:json";

    // URL ya editado con el id del archivo pruebas
//    public String fileURL = "https://docs.google.com/spreadsheets/u/0/d/1TSoMvXROXYPvGh8Atg0C66m-bT1XLU1b5ds4GnMvgyc/gviz/tq?tqx=out:json";


    private ArrayList<ItemModal> itemModalArrayList;
    private ItemRVAdapter itemRVAdapter;
    private RecyclerView itemRV;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemModalArrayList = new ArrayList<>();

        itemRV = findViewById(R.id.idRVItems);
        loadingPB = findViewById(R.id.idPBLoading);

        getDataFromAPI(fileURL);
    }


    private void getDataFromAPI(String url) {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(MainActivity.this, "onResponse", Toast.LENGTH_LONG).show();

                loadingPB.setVisibility(View.GONE);

                // Recortamos el inicio y el final del string
                String jsonString = response.toString().substring(47, response.length()-2);

                try {
                    // Convertimos el string a un objeto json
                    JSONObject jsonObject = new JSONObject(jsonString);

                    JSONObject jsonTable1 = jsonObject.getJSONObject("table");
//                    Log.d("DATAjsonTable1", jsonTable1.toString());

                    JSONArray arrayRows = jsonTable1.getJSONArray("rows");
//                    Log.d("DATAarrayRows", arrayRows.toString());
                    for (int i = 0; i < arrayRows.length(); i++) {
                        JSONObject entryObj = arrayRows.getJSONObject(i);
//                        Log.d("DATAentryObj", entryObj.toString());

                        JSONArray arrayC = entryObj.getJSONArray("c");
                        Log.d("DATAarrayC", arrayC.toString());

                        String rc = arrayC.getJSONObject(3).get("f").toString();
                        String description = arrayC.getJSONObject(4).get("v").toString();
                        String brand = getArrayItemValueAt(arrayC, 5, "v");
//                        String brand = arrayC.getJSONObject(5).get("v").toString();

                        String image = arrayC.getJSONObject(11).get("v").toString();
                        String[] p = image.split("/");
                        String imageLink = "https://drive.google.com/uc?export=download&id="+p[5];
                        Log.d("DATAimageLink", imageLink);

//                        String image = "https://reqres.in/img/faces/1-image.jpg";     // Este ejemplo si funciona, es algo con Google
                        itemModalArrayList.add( new ItemModal(description, brand, rc, imageLink) );

//                        Passing array list to our adapter class
                        itemRVAdapter = new ItemRVAdapter(itemModalArrayList, MainActivity.this);

//                        Setting layout manager to our recycler view
                        itemRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

//                        Setting adapter to our recycler view
                        itemRV.setAdapter(itemRVAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

//    private boolean arrayItemIsNullAtIndex()
    private String getArrayItemValueAt( JSONArray jsonArray, int index, String key ) throws JSONException {

        return jsonArray.isNull(index) ? " " : jsonArray.getJSONObject(index).get(key).toString();

//        String result = "";
//        if ( !jsonArray[index].equals(null) ) {
//            result = jsonArray.getJSONObject(index).get(key);
//        } else {
//            return " ";
//        };
//        return result;
    }
}

