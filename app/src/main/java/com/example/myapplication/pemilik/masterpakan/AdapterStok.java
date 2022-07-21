package com.example.myapplication.pemilik.masterpakan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class AdapterStok extends RecyclerView.Adapter<AdapterStok.ViewHolder> {

    private List<ModelStok> stokList;

    public AdapterStok(List<ModelStok> stokList){
        this.stokList = stokList;
    }

    @NonNull
    @Override
    public AdapterStok.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_tamplate_stock,parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStok.ViewHolder holder, int position) {
        ModelStok modelStok = stokList.get(position);

        holder.nama_pakan.setText(modelStok.getNama_pakan());
        holder.stok_pakan.setText(modelStok.getStok_pakan());
    }

    @Override
    public int getItemCount() {
        return stokList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama_pakan, stok_pakan;

        public ViewHolder(View view) {
            super(view);

            nama_pakan = view.findViewById(R.id.nama_pakan);
            stok_pakan = view.findViewById(R.id.stok_pakan);
        }
    }
}
