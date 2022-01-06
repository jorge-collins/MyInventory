package com.corosoftware.myinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ViewHolder> {

    private ArrayList<ItemModal> itemModalArrayList;
    private Context context;

    public ItemRVAdapter(ArrayList<ItemModal> itemModalArrayList, Context context) {
        this.itemModalArrayList = itemModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_itemlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ItemModal itemModal = itemModalArrayList.get(position);

        holder.descriptionTV.setText(itemModal.getDescription());
        holder.brandTV.setText(itemModal.getBrand());
        holder.rcTV.setText(itemModal.getRc());
        Picasso.get().load(itemModal.getImage()).into(holder.itemIV);
    }

    @Override
    public int getItemCount() {
        return itemModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView descriptionTV, brandTV, rcTV;
        private ImageView itemIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            descriptionTV = itemView.findViewById(R.id.idTVDescription);
            brandTV = itemView.findViewById(R.id.idTVBrand);
            rcTV = itemView.findViewById(R.id.idTVRc);
            itemIV = itemView.findViewById(R.id.idIVItem);
        }
    }
}
