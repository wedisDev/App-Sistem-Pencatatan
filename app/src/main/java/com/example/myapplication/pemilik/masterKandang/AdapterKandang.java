package com.example.myapplication.pemilik.masterKandang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DBContract;
import com.example.myapplication.R;
import com.example.myapplication.VolleyConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
