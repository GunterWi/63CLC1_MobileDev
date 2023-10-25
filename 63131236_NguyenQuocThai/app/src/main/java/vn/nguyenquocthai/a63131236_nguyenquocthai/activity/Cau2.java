package vn.nguyenquocthai.a63131236_nguyenquocthai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.nguyenquocthai.a63131236_nguyenquocthai.R;

public class Cau2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau2);
        getWidget();
        lst.add("Red");
        lst.add("Green");
        lst.add("Blue");
        lst.add("Yellow");
        lst.add("Orange");
        lst.add("Purple");
        lst.add("Pink");
        lst.add("Brown");
        lst.add("Gray");
        lst.add("Black");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lst);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Toast currentToast = null; // Biến để lưu trạng thái của thông báo hiện tại

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                String selectedColor = lst.get(i);

                // Chuyển màu nền của ListView dựa trên màu được chọn
                switch (selectedColor) {
                    case "Red":
                        listview.setBackgroundColor(Color.RED);
                        break;
                    case "Green":
                        listview.setBackgroundColor(Color.GREEN);
                        break;
                    case "Blue":
                        listview.setBackgroundColor(Color.BLUE);
                        break;
                    case "Yellow":
                        listview.setBackgroundColor(Color.YELLOW);
                        break;
                    case "Orange":
                        listview.setBackgroundColor(Color.parseColor("#FFA500")); // Màu cam
                        break;
                    case "Purple":
                        listview.setBackgroundColor(Color.parseColor("#800080")); // Màu tím
                        break;
                    case "Pink":
                        listview.setBackgroundColor(Color.parseColor("#FFC0CB")); // Màu hồng
                        break;
                    case "Brown":
                        listview.setBackgroundColor(Color.parseColor("#A52A2A")); // Màu nâu
                        break;
                    case "Gray":
                        listview.setBackgroundColor(Color.GRAY);
                        break;
                    case "Black":
                        listview.setBackgroundColor(Color.BLACK);
                        break;
                }

                if (currentToast != null) {
                    currentToast.cancel(); // Tắt thông báo cũ nếu có
                }

                currentToast = Toast.makeText(getApplicationContext(), selectedColor, Toast.LENGTH_LONG);
                currentToast.show();
            }
        });
    }
    public void getWidget(){
        listview=findViewById(R.id.listView);
    }
    ListView listview;
    List<String> lst=new ArrayList<>();
}
