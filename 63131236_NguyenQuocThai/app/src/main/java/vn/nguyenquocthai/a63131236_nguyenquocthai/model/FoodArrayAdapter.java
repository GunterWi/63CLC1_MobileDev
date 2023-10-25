package vn.nguyenquocthai.a63131236_nguyenquocthai.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.nguyenquocthai.a63131236_nguyenquocthai.R;

public class FoodArrayAdapter extends BaseAdapter {
    // Class's attributes
    List<Food> lstDataSource;
    private LayoutInflater inflater;
    private Context context;
    // Constructor
    public FoodArrayAdapter(List<Food> lstDataSource, Context context) {
        this.lstDataSource = lstDataSource;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    //inner class
    class FoodItemViewHoder {// mapping from customize item XML layout
        ImageView foodView;
        TextView foodNameView;
        TextView priceView;
        TextView descriptionView;
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
        FoodItemViewHoder itemViewHoder;
        if(view==null){
            view=inflater.inflate(R.layout.activity_food,null);
            itemViewHoder = new FoodItemViewHoder();
            itemViewHoder.foodView=view.findViewById(R.id.imageView);
            itemViewHoder.foodNameView=view.findViewById(R.id.tv_TenMon);
            itemViewHoder.priceView=view.findViewById(R.id.tv_gia);
            itemViewHoder.descriptionView=view.findViewById(R.id.tv_MoTa);
            view.setTag(itemViewHoder);
        }else{
            itemViewHoder=(FoodItemViewHoder) view.getTag();
        }
        Food food = this.lstDataSource.get(i);
        itemViewHoder.foodNameView.setText(food.getFoodName());
        itemViewHoder.priceView.setText(food.getPrice()+" đ");
        itemViewHoder.descriptionView.setText(food.getDescription());

        //cách 1
        //int imageId=getMipmapResldByName(country.getCountryFlag());
        //cách 2
        itemViewHoder.foodView.setImageResource(food.getFoodFlag());
        return view;
    }

    //My own function for reading image ID from filename in mipmap folder
    //cách 1
    public int getMipmapResldByName(String resName) { // tên của file ru1 ru2 ru3
        String packageName = context.getPackageName();
        int imgId = context.getResources().getIdentifier(resName,"drawable",packageName);
        return imgId;
    };


}
