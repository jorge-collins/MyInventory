package com.corosoftware.myinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewItemsRVAdapter extends RecyclerView.Adapter<ViewItemsRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<ItemModal> itemModalArrayList;
    private Context context;

    // constructor
    public ViewItemsRVAdapter(ArrayList<ItemModal> itemModalArrayList, Context context) {
        this.itemModalArrayList = itemModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitems_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        ItemModal modal = itemModalArrayList.get(position);
        holder.itemDescriptionTV.setText(modal.getDescription());
        holder.itemBrandTV.setText(modal.getBrand());
        holder.itemRcTV.setText(modal.getRc());
        holder.itemLocationTV.setText(modal.getLocation());
        holder.itemDateupdatedTV.setText(modal.getDateupdated());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return itemModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView itemDescriptionTV, itemBrandTV, itemRcTV, itemLocationTV, itemDateupdatedTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            itemDescriptionTV = itemView.findViewById(R.id.idTVItemDescription);
            itemBrandTV = itemView.findViewById(R.id.idTVItemBrand);
            itemRcTV = itemView.findViewById(R.id.idTVItemRC);
            itemLocationTV = itemView.findViewById(R.id.idTVItemLocation);
            itemDateupdatedTV = itemView.findViewById(R.id.idTVItemDateupdated);
        }
    }
}
