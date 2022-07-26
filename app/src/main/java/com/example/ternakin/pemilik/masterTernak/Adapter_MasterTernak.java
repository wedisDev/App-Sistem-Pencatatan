package com.example.ternakin.pemilik.masterTernak;

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

public class Adapter_MasterTernak extends RecyclerView.Adapter<Adapter_MasterTernak.ViewHolder> {

    private Context context;
    private List<ModelClassMasterTernak> ternakList;

    public Adapter_MasterTernak (Context context, List<ModelClassMasterTernak> ternakList){
        this.context = context;
        this.ternakList = ternakList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_periode_ternak,parent,  false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelClassMasterTernak modelClassMasterTernak = ternakList.get(position);

        holder.jumlah_bibit.setText(modelClassMasterTernak.getJumlah_bibit());
        holder.tanggal_datang.setText(modelClassMasterTernak.getTanggal_datang());

        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMasterTernak.class);

                intent.putExtra("jumlah_bibit", modelClassMasterTernak.getJumlah_bibit());
                intent.putExtra("tanggal_datang", modelClassMasterTernak.getTanggal_datang());
                intent.putExtra("id_periode", modelClassMasterTernak.getId_periode());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ternakList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView jumlah_bibit, tanggal_datang;
        LinearLayout rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rv = itemView.findViewById(R.id.rv);
            jumlah_bibit = itemView.findViewById(R.id.jumlah_bibit);
            tanggal_datang = itemView.findViewById(R.id.tanggal_datang);
        }
    }
}
