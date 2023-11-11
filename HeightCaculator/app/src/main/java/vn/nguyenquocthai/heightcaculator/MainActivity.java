package vn.nguyenquocthai.heightcaculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText heightInput;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightInput = findViewById(R.id.heightInput);
        calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndDisplayHeight();
            }
        });
    }

    private void calculateAndDisplayHeight() {
        String heightStr = heightInput.getText().toString();
        if (!heightStr.isEmpty()) {
            int height = Integer.parseInt(heightStr);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result")
                    .setMessage("Your height is " + height + "cm!")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            Toast.makeText(MainActivity.this, "Please enter your height", Toast.LENGTH_SHORT).show();
        }
    }
}
