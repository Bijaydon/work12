package com.example.bijay.work1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bijay.work1.google_places.Results;

import java.util.List;

/**
 * Created by Bijay on 6/7/2018.
 */

public class Recycler_adapter extends RecyclerView.Adapter<Recycler_adapter.MyViewHolder> {

    Context context;
    List<Results>results;

    public Recycler_adapter(Context context, List<Results> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public Recycler_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(Recycler_adapter.MyViewHolder holder, int position) {



        //// getting data according to position

        final Results result = results.get(position);

        //// getting the item price and displaying in txt_item_price

        holder.txt_item_price.setText(result.getPlace_id());


        holder.txt_item_name.setText(result.getName());
        holder.txt_item_desc.setText(result.getVicinity());

        /// loading image in image view

        Glide.with(context)
                .load("http://vedisapp.berlin-webdesign-agentur.de/Image/"+result.getPhotos())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.item_image);

        ///// click listener in recycler view

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked " + result.getName(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

   class MyViewHolder extends RecyclerView.ViewHolder{

       TextView txt_item_name,txt_item_desc,txt_item_price;
       ImageView item_image,item_type;

       public MyViewHolder(View itemView) {
           super(itemView);

           txt_item_desc = itemView.findViewById(R.id.item_desc);
           txt_item_name = itemView.findViewById(R.id.item_name);
           txt_item_price = itemView.findViewById(R.id.item_price);
           item_image = itemView.findViewById(R.id.item_image);
           item_type = itemView.findViewById(R.id.item_type);
       }
   }
}
