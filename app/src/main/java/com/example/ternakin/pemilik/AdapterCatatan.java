package com.example.ternakin.pemilik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternakin.R;
import com.example.ternakin.pemilik.masterTernak.ModelClassCatatan;

import java.util.List;

public class AdapterCatatan extends RecyclerView.Adapter<AdapterCatatan.ViewHolder> {

    private Context context;
    private List<ModelClassCatatan> ternakList;

    public AdapterCatatan (Context context, List<ModelClassCatatan> ternakList){
        this.context = context;
        this.ternakList = ternakList;
    }

    @NonNull
    @Override
    public AdapterCatatan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_catatan,parent,  false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCatatan.ViewHolder holder, int position) {
        ModelClassCatatan modelClassMasterTernak = ternakList.get(position);

        holder.lahir.setText(modelClassMasterTernak.getTanggal_catatan());
        holder.jml_mati.setText(modelClassMasterTernak.getJumlah_mati());
        holder.jml_kaling.setText(modelClassMasterTernak.getJumlah_kaling());
        holder.kode_pakan.setText(modelClassMasterTernak.getKode_pakan());
        holder.berat_badan.setText(modelClassMasterTernak.getBerat_badan());
        holder.sisa.setText(modelClassMasterTernak.getSisa_ayam());
        holder.pakan.setText(modelClassMasterTernak.getJumlah_pakan());
        holder.numberUmur.setText(modelClassMasterTernak.getUmur_ayam());

        holder.rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, DataHarianList.class);
//
//                intent.putExtra("id_periode", modelClassMasterTernak.getId_periode());
//
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ternakList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView lahir, sisa, pakan, kode_pakan, jml_mati, jml_kaling, berat_badan, numberUmur;
        CardView rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rv = itemView.findViewById(R.id.cardView);
            lahir = itemView.findViewById(R.id.tglLahir);
            sisa = itemView.findViewById(R.id.sisa);
            pakan = itemView.findViewById(R.id.pakan);
            kode_pakan = itemView.findViewById(R.id.kode_pakan);
            jml_mati = itemView.findViewById(R.id.jml_mati);
            jml_kaling = itemView.findViewById(R.id.jml_kaling);
            berat_badan = itemView.findViewById(R.id.berat_badan);
            numberUmur = itemView.findViewById(R.id.numberUmur);
        }
    }
}