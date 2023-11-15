package vn.nguyenquocthai.viewpager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    ArrayList<Country> dsQG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout =findViewById(R.id.tab_layout);
        dsQG = new ArrayList<Country>();
        Country qg1 = new Country("Vietnam","vn",80);
        Country qg2 = new Country("United State","us",68);
        Country qg3 = new Country("Russia","ru",120);
        dsQG.add(qg1);
        dsQG.add(qg2);
        dsQG.add(qg3);
        CountryPageAdapter countryPageAdapter  = new CountryPageAdapter(this, dsQG);
        viewPager2.setAdapter(countryPageAdapter);







        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText("Tab " + (position + 1))
        ).attach();

    }
}