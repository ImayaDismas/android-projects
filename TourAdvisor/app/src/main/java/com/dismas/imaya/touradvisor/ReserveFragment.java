package com.dismas.imaya.touradvisor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dismas.imaya.touradvisor.adapters.RecyclerViewAdapter;
import com.dismas.imaya.touradvisor.configs.ParkAllConfig;
import com.dismas.imaya.touradvisor.constructors.ParksAllConstructor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imaya on 12/2/16.
 */
public class ReserveFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private LinearLayoutManager lLayout;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<ParksAllConstructor> reserves = new ArrayList<>();

    private ProgressDialog loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View x =  inflater.inflate(R.layout.fragment_reserve,container, false);
        ((MainActivityDrawer) getActivity()).topToolBar.setTitle("My tours");
        lLayout = new LinearLayoutManager(getActivity());

        final RecyclerView rView = (RecyclerView) x.findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);
        List<ParksAllConstructor> rowListItem = getAllReserves();
        final RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(getActivity(), rowListItem);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                rView.setAdapter(rcAdapter);
            }
        }, 3000);

        Toast.makeText(getActivity(), "Swipe down to refresh", Toast.LENGTH_LONG).show();
        mSwipeRefreshLayout = (SwipeRefreshLayout) x.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Inflate the layout for this fragment
        return x;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            getActivity().getActionBar()
                    .setTitle("My tours");
        }
    }

    @Override
    public void onRefresh() {

    }
    public List<ParksAllConstructor> getAllReserves(){
        loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching National Reserves...", false, false);

        String url = ParkAllConfig.RESERVE_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
                loading.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        return reserves;
    }

    private void showJSON(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("details");


            for (int i = 0; i < result.length(); i++) {

                JSONObject reserveData = result.getJSONObject(i);
                ParksAllConstructor reserve = new ParksAllConstructor();
                //park.setAttraction_site_id(reserveData.getInt("attraction_site_id"));
                reserve.setSite_name(reserveData.getString("site_name"));
                reserve.setLocation_name(reserveData.getString("location_name"));
                reserve.setSite_image(reserveData.getString("site_image"));
                reserves.add(reserve);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

