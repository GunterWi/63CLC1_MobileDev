package vn.nguyenquocthai.a63131236_nguyenquocthai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.nguyenquocthai.a63131236_nguyenquocthai.R;
import vn.nguyenquocthai.a63131236_nguyenquocthai.model.Food;
import vn.nguyenquocthai.a63131236_nguyenquocthai.model.FoodArrayAdapter;

public class Cau3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau3);
        dsMA=new ArrayList<Food>();
        Food ma1= new Food(R.drawable.dish1,"Củ cải",80000,"Củ cải ngon");
        Food ma2= new Food(R.drawable.sushi,"Sushi",800000,"Sushi ngon");
        Food ma3= new Food(R.drawable.tom,"Tôm",20000,"Tôm ngon");

        dsMA.add(ma1);
        dsMA.add(ma2);
        dsMA.add(ma3);

        ListView lvQG=findViewById(R.id.listViewAdvance);
        FoodArrayAdapter adapter= new FoodArrayAdapter(dsMA,this);
        lvQG.setAdapter(adapter);
        lvQG.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Toast currentToast = null; // Biến để lưu trạng thái của thông báo hiện tại

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                //cách 1
                //String mucChon = adapter.getItem(i);
                //cách 2
                String mucChon = dsMA.get(i).getFoodName();
                if (currentToast != null) {
                    currentToast.cancel(); // Tắt thông báo cũ nếu có
                }

                currentToast = Toast.makeText(Cau3.this, mucChon, Toast.LENGTH_LONG);
                currentToast.show();
            }
        });
    }
    private ArrayList<Food>dsMA;

}