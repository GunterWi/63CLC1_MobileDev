package vn.nguyenquocthai.vieccanlam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.HashMap;

public class ThemTaskActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_them_task);
            FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton2);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lấy dữ liệu
                    EditText editTextName = findViewById(R.id.editTextName);
                    EditText editTextMess = findViewById(R.id.editTextMessage);
                    EditText editTextDate = findViewById(R.id.editTextDate);
                    EditText editTextPrio = findViewById(R.id.editTextPriority);

                    String tenCV = editTextName.getText().toString() ;
                    String mess= editTextMess.getText().toString() ;
                    String dat = editTextDate.getText().toString() ;
                    String pri = editTextPrio.getText().toString() ;
                    // Gói vào đối tượng TASK
                    Tasks task = new Tasks(tenCV,dat,mess,pri);
                    // Kết nói DB, và thêm
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = database.getReference("TASKS");
                    String key = databaseReference.push().getKey();

                    HashMap<String, Object> item = new HashMap<String,Object>();
                    item.put(key,task.toFirebaseObject() );

                    databaseReference.updateChildren(item, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error==null ) finish();
                        }
                    });


                }
            });
        }
}