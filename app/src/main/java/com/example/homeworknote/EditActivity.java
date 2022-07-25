package com.example.homeworknote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.sql.SQLException;

public class EditActivity extends MainActivity {

    private Button buttonSave, buttonCancel;
    private TextView textView_content, textView_title;
    private Intent intent;
    public String textViewContent, textViewTitle;
    private Context context;
    private ListView constraint;
    private String title;
    private String created;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        context = this;
        db = new DB(this);
        db.openDB();

        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);
        textView_content = findViewById(R.id.textView_content);
        textView_title = findViewById(R.id.textView_title);

        intent = getIntent();
        title=intent.getStringExtra("title");
        created=intent.getStringExtra("created");

//        textViewContent=textView_content.getText().toString();
//        textViewTitle=textView_title.getText().toString();
//
//        textView_title.setText(title);
//        textView_content.setText(created);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                textViewContent=textView_content.getText().toString();
                textViewTitle=textView_title.getText().toString();

                Intent intent = new Intent(context,MainActivity.class);
//                intent.putExtra("title",textViewContent);
//                intent.putExtra("created",textViewTitle);

                try {
                    db.insert(textViewContent,textViewTitle);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onResume() { //做還原
//        super.onResume();
//        SharedPreferences sp = getSharedPreferences("Temp_data",0);
//        textView_title.setText(sp.getString("title",""));
//         textView_content.setText(sp.getString("created",""));
//    }

//    @Override
//    protected void onPause() { //做保存
//        SharedPreferences sp = getSharedPreferences("Temp_data",0);
//        sp.edit().putString("title",textView_title.getText().toString()).commit();
//        sp.edit().putString("created",textView_content.getText().toString()).commit();
//        super.onPause();
//    }


}