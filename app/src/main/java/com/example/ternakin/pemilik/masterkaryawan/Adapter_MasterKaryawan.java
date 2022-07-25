package com.example.ternakin.pemilik.masterkaryawan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternakin.R;

import java.util.List;

public class Adapter_MasterKaryawan extends RecyclerView.Adapter<Adapter_MasterKaryawan.ViewHolder> {

    private List<ModelClassMasterKaryawan> karyawanList;
    Context context;

    public Adapter_MasterKaryawan(Context context, List<ModelClassMasterKaryawan> karyawanList){
        this.context = context;
        this.karyawanList = karyawanList;
    }

    @NonNull
    @Override
    public Adapter_MasterKaryawan.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_tamplate_master_karyawan,parent,  false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_MasterKaryawan.ViewHolder holder, int position) {
        ModelClassMasterKaryawan modelClassMasterKaryawan = karyawanList.get(position);

        holder.jabatan.setText(modelClassMasterKaryawan.getJabatan());
        holder.nama.setText(modelClassMasterKaryawan.getNama());
        holder.notelp.setText(modelClassMasterKaryawan.getNotelp());
        holder.email.setText(modelClassMasterKaryawan.getEmail());
        holder.status.setText(modelClassMasterKaryawan.getStatus());

    }

    @Override
    public int getItemCount() {
        return karyawanList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jabatan, nama, notelp, email, status;
        ImageView btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jabatan = itemView.findViewById(R.id.jabatan);
            nama = itemView.findViewById(R.id.nama);
            notelp = itemView.findViewById(R.id.notelp);
            email = itemView.findViewById(R.id.email);
            status = itemView.findViewById(R.id.status);
        }
    }
}