package com.example.myapplication.pemilik;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.DBContract;
import com.example.myapplication.R;
import com.example.myapplication.pemilik.masterpakan.MasterPakan;
import com.example.myapplication.pemilik.masterpakan.ModelStok;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragmentPemilik#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragmentPemilik extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout statistikPakan;
    private TextView tanggal, stok_pakan;
    private RecyclerView recyclerView;
    private List<ModelClass> userList;
    private Adapter adapter;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;

    public HomeFragmentPemilik() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragmentPemilik.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragmentPemilik newInstance(String param1, String param2) {
        HomeFragmentPemilik fragment = new HomeFragmentPemilik();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_pemilik, container, false);

        tanggal = view.findViewById(R.id.tanggal);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                tanggal.setText(dateFormat.format(new Date()));

                //interfal
                handler.postDelayed(this, 1000);
            }
        });

        // recyclerView
        recyclerView = view.findViewById(R.id.recyclerTernak);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userList = new ArrayList<>(3); //kalo menampilkan data dari database pengennya cuma sampe 3
        userList.add(new ModelClass("Kandang Timur A1", "6" ));
        userList.add(new ModelClass("Kandang Barat B1", "18" ));
        userList.add(new ModelClass("Kandang Barat B2", "29"));
        recyclerView.setAdapter(new Adapter(userList));

        //stok_pakan
        stok_pakan = view.findViewById(R.id.stok_pakan);

        requestQueue = Volley.newRequestQueue(getContext());

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, DBContract.SERVER_COUNT_STOCK_DASHBOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("pakan");
                            for (int a = 0; a < jsonArray.length(); a ++){
                                JSONObject json = jsonArray.getJSONObject(a);
                                HashMap<String, String> map  = new HashMap<String, String>();
                                map.put("stok_pakan", json.getString("stok_pakan"));

                                list_data.add(map);
                            }

                            stok_pakan.setText(list_data.get(0).get("stok_pakan"));


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

        //intent statistic pakan
        statistikPakan = view.findViewById(R.id.statistikPakan);
        statistikPakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MasterPakan.class);
                startActivity(intent);
            }
        });

        return view;
    }
}