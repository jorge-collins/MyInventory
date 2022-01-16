package com.corosoftware.myinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    private Toolbar toolbar;

    // URL archivo real
    // https://docs.google.com/spreadsheets/d/1xXWnPwy2ElBKb4oFzLSGFsMmmoM4XH2Kd_wxwNCSOQ4/edit?usp=sharing

    // URL ya editado con el id del archivo real
    public String fileURL = "https://docs.google.com/spreadsheets/u/0/d/1xXWnPwy2ElBKb4oFzLSGFsMmmoM4XH2Kd_wxwNCSOQ4/gviz/tq?tqx=out:json";

    private ArrayList<ItemModal> itemModalArrayList;
    private ItemRVAdapter itemRVAdapter;
    private RecyclerView itemRV;
    private ProgressBar loadingPB;

    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_import_data, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String itemId = item.toString();
        Log.d("DATAitemId", itemId);
        switch (itemId) {
            case "Yes":
                // Oculta el listado
                itemRV.setVisibility(View.GONE);
                // Muestra el spinning
                loadingPB.setVisibility(View.VISIBLE);
                // Genera la db
                createDB();
                // Oculta el spinning
                loadingPB.setVisibility(View.GONE);

                // Inicia la actividad que va a mostrar todos los elementos en la db
                Intent i = new Intent(MainActivity.this, ViewItemsActivity.class);
                startActivity(i);
                break;
            case "No":
                itemRV.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "[ Return to previous activity ]", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(MainActivity.this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.action_bar_loading);

        itemModalArrayList = new ArrayList<>();

        itemRV = findViewById(R.id.idRVItems);
        loadingPB = findViewById(R.id.idPBLoading);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        getDataFromAPI(fileURL);
    }


    private void createDB() {
        dbHandler.deleteAllItems();

//        Log.d("DATAarraySize", String.valueOf(itemModalArrayList.size()));

        for (int i = 0; i < itemModalArrayList.size(); i++) {
            ItemModal itemModal = itemModalArrayList.get(i);

            String itemDescriptionTxt = itemModal.getDescription();
            String itemBrandTxt = itemModal.getBrand();
            String itemRcTxt = itemModal.getRc();
            String itemImageTxt = itemModal.getImage();
            String itemLocationTxt = itemModal.getLocation();
            String itemDateupdatedTxt = itemModal.getDateupdated();
            String itemGpsTxt = itemModal.getGps();

            dbHandler.addNewItem(itemDescriptionTxt, itemBrandTxt, itemRcTxt, itemImageTxt, itemLocationTxt, itemDateupdatedTxt, itemGpsTxt);
        }
        Toast.makeText(MainActivity.this, "DB created", Toast.LENGTH_LONG).show();
    }

    private void getDataFromAPI(String url) {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, response -> {

                    toolbar.setTitle(R.string.action_bar_title_import_data);
                    setSupportActionBar(toolbar);
                    loadingPB.setVisibility(View.GONE);

                    // Recortamos el inicio y el final del string
                    String jsonString = response.substring(47, response.length()-2);

                    try {
                        // Convertimos el string a un objeto json
                        JSONObject jsonObject = new JSONObject(jsonString);
                        JSONObject jsonTable1 = jsonObject.getJSONObject("table");
                        JSONArray arrayRows = jsonTable1.getJSONArray("rows");

                        for (int i = 0; i < arrayRows.length(); i++) {
                            JSONObject entryObj = arrayRows.getJSONObject(i);

                            // Recuperamos cada renglon
                            JSONArray arrayC = entryObj.getJSONArray("c");

                            String rc = getArrayItemValueAt(arrayC, 3, "f");
                            String description = getArrayItemValueAt(arrayC,4, "v");
                            String brand = getArrayItemValueAt(arrayC, 5, "v");

                            // Recuperamos el enlace compartido de la imagen
                            String image = getArrayItemValueAt(arrayC, 11, "v");
                            String imageLink = "https://freesvg.org/img/Image-Not-Found.png";
                            if (image.isEmpty()) { Log.d("DATAimage", "[" + image + "] is empty"); }
                            else {
                                // En los links para compartir de Google, cuando es una imagen
                                // se formatea como -download- para que se pueda visualizar correctamente
                                String[] p = image.split("/");
                                imageLink = "https://drive.google.com/uc?export=download&id=" + p[5];
                            }
                            String lastLocation = getArrayItemValueAt(arrayC, 16, "v");

                            String dateupdated = simpleDateFormat.format( calendar.getTime() );

                            itemModalArrayList.add( new ItemModal(description, brand, rc, imageLink, lastLocation, dateupdated, "0,0") );

    //                        Passing array list to our adapter class
                            itemRVAdapter = new ItemRVAdapter(itemModalArrayList, MainActivity.this);

    //                        Setting layout manager to our recycler view
                            itemRV.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    //                        Setting adapter to our recycler view
                            itemRV.setAdapter(itemRVAdapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("ERRORe", e.toString());
                        toolbar.setTitle("Please, contact support...");
                        Toast.makeText(MainActivity.this, "Fail to get data (JSONException).", Toast.LENGTH_LONG).show();
                        loadingPB.setVisibility(View.GONE);
                    }

                }, error -> {
                    Log.d("ERRORvolley", error.toString());
                    toolbar.setTitle("Please, confirm source...");
                    Toast.makeText(MainActivity.this, "Fail to get data (volley), confirm source.", Toast.LENGTH_LONG).show();
                    loadingPB.setVisibility(View.GONE);
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private String getArrayItemValueAt( JSONArray jsonArray, int index, String key ) throws JSONException {

        return jsonArray.isNull(index) ? "" : jsonArray.getJSONObject(index).get(key).toString();
    }

}

