package com.covid19.simple.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.simple.app.api.ModelClass;

import java.text.NumberFormat;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    int mSelectedOption = 1;
    Context context;
    List<ModelClass> countryList;

    public DataAdapter(Context context, List<ModelClass> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public DataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.DataViewHolder holder, int position) {

        ModelClass modelClass = countryList.get(position);
        if(mSelectedOption==1){
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getCases())));
        }
        else if(mSelectedOption==2){
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getRecovered())));
        }
        else if(mSelectedOption==3){
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getDeaths())));
        }
        else{
            holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(modelClass.getActive())));
        }

        holder.country.setText(modelClass.getCountry());

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        TextView cases, country;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            cases = itemView.findViewById(R.id.countryCase);
            country = itemView.findViewById(R.id.countryName);
        }
    }

    public void filter(String item) {

        if(item.equals("cases")){
            mSelectedOption=1;
        }

        else if(item.equals("recovered")){
            mSelectedOption=2;
        }

        else if(item.equals("deaths")){
            mSelectedOption=3;
        }

        else{
            mSelectedOption=4;
        }
        notifyDataSetChanged();

    }
}
