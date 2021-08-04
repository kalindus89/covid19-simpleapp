package com.covid19.simple.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.covid19.simple.app.api.ApiClient;
import com.covid19.simple.app.api.ApiInterface;
import com.covid19.simple.app.api.ModelClass;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CountryCodePicker countryCodePicker;
    TextView mTodayTotal, mTotal, mActive, mTodayActive, mRecovered, mTotalRecovered, mDeaths, mTotalDeaths, mFilter;
    String country;
    Spinner spinner;
    String[] types={"cases","deaths", "recovered","active"};
    private List<ModelClass> modelClassList_top;
    private List<ModelClass> modelClassList_recyclerView;
    PieChart mPieChart;
    private RecyclerView recyclerView;
    DataAdapter dataAdapter;
    private ShimmerFrameLayout shimmerFrameLayout_simple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        countryCodePicker =findViewById(R.id.ccp);
        mTodayTotal =findViewById(R.id.todayTotal);
        mTotal =findViewById(R.id.totalCase);
        mActive =findViewById(R.id.activeCase);
        mTodayActive =findViewById(R.id.todayActive);
        mRecovered =findViewById(R.id.recoveredCase);
        mTotalRecovered =findViewById(R.id.todayRecovered);
        mDeaths =findViewById(R.id.deathCase);
        mTotalDeaths =findViewById(R.id.todayDeath);
        mFilter =findViewById(R.id.filter);
        mPieChart =findViewById(R.id.pieChart);
        recyclerView =findViewById(R.id.recyclerView);
        spinner =findViewById(R.id.spinner);
        modelClassList_top = new ArrayList<>();
        modelClassList_recyclerView = new ArrayList<>();

        shimmerFrameLayout_simple = findViewById(R.id.shimmerFrameLayout_simple);
        shimmerFrameLayout_simple.setVisibility(View.VISIBLE);
        shimmerFrameLayout_simple.startShimmer();

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        ApiClient.getApiClient().create(ApiInterface.class).getCountryData().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {

                modelClassList_recyclerView.addAll(response.body());
                dataAdapter.notifyDataSetChanged();
                shimmerFrameLayout_simple.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {

            }
        });

        dataAdapter = new DataAdapter(getApplicationContext(), modelClassList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);

        countryCodePicker.setAutoDetectedCountry(true);
        country=countryCodePicker.getSelectedCountryName();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {

                country=countryCodePicker.getSelectedCountryName();
                fetchedData();
            }
        });

        fetchedData();

    }

    private void fetchedData() {

        ApiClient.getApiClient().create(ApiInterface.class).getCountryData().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {

                modelClassList_top.addAll(response.body());

                for(int i=0; i<modelClassList_top.size();i++){

                    if(modelClassList_top.get(i).getCountry().equals(country)){

                        mActive.setText((modelClassList_top.get(i).getActive()));

                        mTodayTotal.setText((modelClassList_top.get(i).getTodayCases()));
                        mTotal.setText((modelClassList_top.get(i).getCases()));

                        mTotalRecovered.setText((modelClassList_top.get(i).getTodayRecovered()));
                        mRecovered.setText((modelClassList_top.get(i).getRecovered()));

                        mTotalDeaths.setText((modelClassList_top.get(i).getTodayDeaths()));
                        mDeaths.setText((modelClassList_top.get(i).getDeaths()));

                        updateGraph(Integer.parseInt(modelClassList_top.get(i).getCases()),Integer.parseInt(modelClassList_top.get(i).getActive()),Integer.parseInt(modelClassList_top.get(i).getRecovered()),Integer.parseInt(modelClassList_top.get(i).getDeaths()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {

            }
        });

    }

    private void updateGraph(int todayCases, int active, int recovered, int deaths) {

         mPieChart.clearChart();
         mPieChart.addPieSlice(new PieModel("Total" ,todayCases, Color.parseColor("#FFB701")));
         mPieChart.addPieSlice(new PieModel("Active" ,active, Color.parseColor("#FF4CAF50")));
         mPieChart.addPieSlice(new PieModel("Recovered" ,recovered, Color.parseColor("#38ACDD")));
         mPieChart.addPieSlice(new PieModel("Deaths" ,deaths, Color.parseColor("#f55c47")));
         mPieChart.startAnimation();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        String item =types[position];
        mFilter.setText(item);
        dataAdapter.filter(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}