package com.example.myapplication.pemilik;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ModelClass> userlist;

    public Adapter(List<ModelClass>userlist){
        this .userlist=userlist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_tamplate,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String kandang = userlist.get(position).getNama_kandang();
        String hari = userlist.get(position).getKet_hari();

        holder.setData(kandang, hari);

    }

    @Override
    public int getItemCount() {
        return userlist.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nama_kandang;
        private TextView ket_hari;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_kandang = itemView.findViewById(R.id.nama_kandang);
            ket_hari = itemView.findViewById(R.id.ket_hari);
        }

        public void setData(String kandang, String hari) {
            nama_kandang.setText(kandang);
            ket_hari.setText(hari);
        }
    }
}