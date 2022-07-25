package com.example.ternakin.pemilik;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternakin.R;
import com.example.ternakin.pemilik.masterTernak.ModelClassMasterTernak;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolder> {

    private Context context;
    private List<ModelClassMasterTernak> ternakList;

    public AdapterData (Context context, List<ModelClassMasterTernak> ternakList){
        this.context = context;
        this.ternakList = ternakList;
    }

    @NonNull
    @Override
    public AdapterData.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_periode_ternak_data,parent,  false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.ViewHolder holder, int position) {
        ModelClassMasterTernak modelClassMasterTernak = ternakList.get(position);

        holder.jumlah_bibit.setText(modelClassMasterTernak.getJumlah_bibit());
        holder.tanggal_datang.setText(modelClassMasterTernak.getTanggal_datang());

        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DataHarianList.class);

                intent.putExtra("id_periode", modelClassMasterTernak.getId_periode());
                intent.putExtra("nama_kandang", modelClassMasterTernak.getNama_kandang());
                intent.putExtra("id_kandang", modelClassMasterTernak.getId_kandang());

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
        RelativeLayout rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rv = itemView.findViewById(R.id.rv);
            jumlah_bibit = itemView.findViewById(R.id.jumlah_bibit);
            tanggal_datang = itemView.findViewById(R.id.tanggal_datang);
        }
    }
}