package com.example.ternakin.pemilik.masterKandang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternakin.R;

import java.util.List;

public class AdapterKandang extends RecyclerView.Adapter<AdapterKandang.ViewHolder> {

    private List<ModelKandang> kandangList;
    private Context context;

    public AdapterKandang (Context context, List<ModelKandang> kandangList){
        this.kandangList = kandangList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterKandang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_master_kandang,parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKandang.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelKandang modelKandang = kandangList.get(position);

        holder.nama_kandang.setText(modelKandang.getNama_kandang());
        holder.kapasitas.setText(modelKandang.getKapasitas());


        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditMasterKandang.class);

                intent.putExtra("nama_kandang", modelKandang.getNama_kandang());
                intent.putExtra("kapasitas", modelKandang.getKapasitas());
                intent.putExtra("id_kandang", modelKandang.getId_kandang());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return kandangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama_kandang, kapasitas;
        LinearLayout rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_kandang = itemView.findViewById(R.id.nama_kandang);
            kapasitas = itemView.findViewById(R.id.kapasitas);
            rv = itemView.findViewById(R.id.rv);
        }
    }

}
