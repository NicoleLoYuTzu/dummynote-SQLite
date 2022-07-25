package com.example.homeworknote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Revise extends MainActivity {
    private TextView textView_content,textView_title;
    private Button buttonSave,buttonCancel;
    private String textViewContent,textViewTitle;
    private Revise context;
    private Intent intent;
    private Cursor cursor;
    private long id;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revise);
        context = this;
        db = new DB(this);
        db.openDB();
        //如何讀取點下去的id?
        intent = getIntent();


        textView_content = findViewById(R.id.textView_content);
        textView_title = findViewById(R.id.textView_title);
        buttonSave=findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);
        textViewContent=textView_content.getText().toString();
        textViewTitle=textView_title.getText().toString();
        id= intent.getExtras().getLong("id");
        restore(id);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewContent=textView_content.getText().toString();
                textViewTitle=textView_title.getText().toString();
                intent = getIntent();
                id= intent.getExtras().getLong("id");
                db.update(textViewTitle,textViewContent,id);


                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("title",textViewTitle);
                intent.putExtra("created",textViewContent);


                startActivity(intent);
            }
        });


    }
    public void restore(long id){
        Cursor cursor =db.readAll();
        cursor.moveToPosition((int) id-1);
        @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
        @SuppressLint("Range") String created = cursor.getString(cursor.getColumnIndex("created"));
        textView_title.setText(title);
        textView_content.setText(created);

    }

    public void setContent() {
        cursor = db.readAll(); //put words first! then read
        cursor.moveToFirst();
        String col = "";
        for (int i = 0; i < 3; i++) {
            col += cursor.getColumnName(i) + "\t\t";
        }
        Log.d("Cursor", col);
//        SimpleCursorAdapter sca = new SimpleCursorAdapter(
//                this,
//                R.layout.item_view,
//                cursor,
//                new String[]{"_id", "title", "created"},
//                new int[]{R.id.text1, R.id.text2, R.id.text3},
//                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//        listView.setAdapter(sca);
    }


}

