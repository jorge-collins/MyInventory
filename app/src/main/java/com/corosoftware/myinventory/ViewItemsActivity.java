package com.corosoftware.myinventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewItemsActivity extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<ItemModal> itemModalArrayList;
    private DBHandler dbHandler;
    private ViewItemsRVAdapter viewItemsRVAdapter;
    private RecyclerView itemsRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        // initializing our all variables.
        itemModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewItemsActivity.this);

        // getting our course array
        // list from db handler class.
        itemModalArrayList = dbHandler.readItems();

        // on below line passing our array lost to our adapter class.
        viewItemsRVAdapter = new ViewItemsRVAdapter(itemModalArrayList, ViewItemsActivity.this);
        itemsRV = findViewById(R.id.idRVItems);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewItemsActivity.this, RecyclerView.VERTICAL, false);
        itemsRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        itemsRV.setAdapter(viewItemsRVAdapter);
    }
}