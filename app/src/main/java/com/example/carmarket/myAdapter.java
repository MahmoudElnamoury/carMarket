package com.example.carmarket;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter <myAdapter.carViewHolder> {

    ArrayList <car> cars;
    onRecyclerViewListener listener;

    public myAdapter(ArrayList<car> cars,onRecyclerViewListener listener) {
        this.cars = cars;
        this.listener=listener;
    }
    public ArrayList<car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<car> cars) {
        this.cars = cars;

    }

    @NonNull
    @Override
    public carViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,null,false);
        carViewHolder holder=new carViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull carViewHolder holder, int position) {
        car car=cars.get(position);
        if (car.getImage()!=null)
            holder.iv.setImageURI(Uri.parse(car.getImage()));
        holder.model.setText(car.getModel());
        holder.color.setText(car.getColor());
        try {
            holder.color.setTextColor(Color.parseColor(car.getColor()));
        }
        catch (Exception e){
        }

        holder.dpl.setText(car.getDpl()+"");

        holder.carId=car.getId();
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class carViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView model,color,dpl;
        int carId;
        public carViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.custom_item_iv);
            model=itemView.findViewById(R.id.custom_item_car_type);
            color=itemView.findViewById(R.id.custom_item_color_);
            dpl=itemView.findViewById(R.id.custom_item_dpl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.Onclick(carId);
                }
            });
        }
    }
}
