package vn.nguyenquocthai.viewpager2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class CountryFragment extends Fragment {

    private Country data;

    private static int counter = 0;

    public CountryFragment(Country country) {
        data =country;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        counter++;
        if(counter % 2 == 0) {
            view.setBackgroundColor(Color.parseColor("#ebdef0"));
        } else  {
            view.setBackgroundColor(Color.parseColor("#e8f8f5"));
        }
        TextView textViewTenQG = view.findViewById(R.id.textViewCountryName);
        TextView textViewDanSo = view.findViewById(R.id.textViewPopulation);
        ImageView imageViewFlag = view.findViewById(R.id.imageViewFlag);

        textViewTenQG.setText(data.getCountryName());
        textViewDanSo.setText("Số dân: " + String.valueOf( data.getPopulation() ) );
        int resID  = view.getResources().getIdentifier(data.getCountryFlag(),"mipmap", view.getContext().getPackageName()   );
        imageViewFlag.setImageResource( resID );
        // Inflate the layout for this fragment
        return view;
    }
}