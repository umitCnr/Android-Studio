package com.example.flower;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.databinding.RecyclerRowBinding;

import java.util.ArrayList;

import UserSqliteFlower.UserActivity;

public class recycler extends RecyclerView.Adapter<recycler.holder> {

    public recycler(ArrayList<flower> arrayList) {
        this.arrayList = arrayList;
    }

    ArrayList<flower> arrayList;



    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new holder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.binding.textviewrecyclerviewtext.setText(arrayList.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), UserActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("id",arrayList.get(position).id);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        private RecyclerRowBinding binding;

        public holder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
