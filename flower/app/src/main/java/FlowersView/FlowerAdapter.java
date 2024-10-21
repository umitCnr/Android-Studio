package FlowersView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flower.databinding.RecyclerFlowerrowBinding;
import com.example.flower.databinding.RecyclerRowBinding;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.holderflower> {
    public FlowerAdapter(ArrayList<Plant> list) {
        this.list = list;
    }

    ArrayList<Plant> list;
    Plant plant;
    @NonNull
    @Override
    public holderflower onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("FlowerAdapter", "onBindViewHolder");
        RecyclerFlowerrowBinding recyclerFlowerrowBinding = RecyclerFlowerrowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new holderflower(recyclerFlowerrowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull holderflower holder, int position) {
        Log.d("FlowerAdapter", "onCreateViewHolder");
        holder.binding1.flowertextapiview.setText(list.get(position).getCommonName());
        String imageUrl = list.get(position).getImageUrl();
        Picasso.get().load(imageUrl).into(holder.binding1.apiview);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  holderflower extends RecyclerView.ViewHolder {
        private RecyclerFlowerrowBinding binding1;
        public holderflower(RecyclerFlowerrowBinding binding) {
            super(binding.getRoot());
            this.binding1=binding;
        }
    }
}
