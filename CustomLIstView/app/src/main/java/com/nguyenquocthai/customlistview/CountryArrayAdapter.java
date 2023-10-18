package com.nguyenquocthai.customlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryArrayAdapter extends BaseAdapter {
    // Class's attributes
    List<Country> lstDataSource;
    private LayoutInflater inflater;
    private Context context;
    // Constructor
    public CountryArrayAdapter(List<Country> lstDataSource, Context context) {
        this.lstDataSource = lstDataSource;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    //inner class
    class CountryItemViewHoder {// mapping from customize item XML layout
        ImageView flagView;
        TextView countryNameView;
        TextView populationView;
    }
    @Override
    public int getCount() {
        return lstDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return lstDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {  //later
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CountryItemViewHoder itemViewHoder;
        if(view==null){
            view=inflater.inflate(R.layout.activity_country,null);
            itemViewHoder = new CountryItemViewHoder();
            itemViewHoder.flagView=view.findViewById(R.id.imageView);
            itemViewHoder.countryNameView=view.findViewById(R.id.textViewCountry);
            itemViewHoder.populationView=view.findViewById(R.id.textViewPopulation);
            view.setTag(itemViewHoder);
        }else{
            itemViewHoder=(CountryItemViewHoder) view.getTag();
        }
        Country country = this.lstDataSource.get(i);
        itemViewHoder.countryNameView.setText(country.getCountryName());
        itemViewHoder.populationView.setText("Population: "+country.getPopulation());
        int imageId=getMipmapResldByName(country.getCountryFlag());
        itemViewHoder.flagView.setImageResource(imageId);
        return view;
    }

    //My own function for reading image ID from filename in mipmap folder
    public int getMipmapResldByName(String resName) {
        String packageName = context.getPackageName();
        int imgId = context.getResources().getIdentifier(resName,"mipmap",packageName);
        return imgId;
    };


}
