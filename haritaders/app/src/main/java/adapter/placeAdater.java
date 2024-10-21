package adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Index;

import com.example.haritaders.MapsActivity;
import com.example.haritaders.databinding.RecyclerRowBinding;

import java.util.List;

import model.Place;

public class placeAdater extends RecyclerView.Adapter<placeAdater.recyclerholder> {
    public placeAdater(List<Place> placeList) {
        this.placeList = placeList;
    }

    List<Place> placeList;

    @NonNull
    @Override
    public recyclerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new recyclerholder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerholder holder, int position) {
        holder.binding.recyclerviewTextView.setText(placeList.get(position).yername);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), MapsActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("place",placeList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class recyclerholder extends RecyclerView.ViewHolder {
        RecyclerRowBinding binding;

        public recyclerholder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
