package com.example.bogda.geekhubandroidgrouplist.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bogda.geekhubandroidgrouplist.R;

public class AddDialog extends AppCompatActivity {
    EditText nameEditText = null;
    EditText phoneEditText = null;
    Button btn = null;
    final Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);
    }

    @Override
    protected void onStart() {
        super.onStart();
        nameEditText = (EditText) findViewById(R.id.add_name_edit_text);
        phoneEditText = (EditText) findViewById(R.id.add_phone_edit_text);
        btn = (Button) findViewById(R.id.ok_add_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(nameEditText.getText().toString().equals("")) || !(phoneEditText.getText().toString().equals(""))) {
                    Intent intent = new Intent();
                    intent.putExtra("name", nameEditText.getText().toString());
                    intent.putExtra("phone", phoneEditText.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    setResult(RESULT_CANCELED);
                    Toast.makeText(ctx,"No entries",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
