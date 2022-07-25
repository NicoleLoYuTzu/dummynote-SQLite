package com.example.homeworknote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    public DB db;
    private Intent intent;
    private Context context;
    private Cursor cursor;
    private String title,created;
    public long idNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        context=this;
        db = new DB(this);
        db.openDB();
        setAdapter();



//        intent = getIntent();
//        title = intent.getStringExtra("title");
//        created=intent.getStringExtra("created");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("異動資料")
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, Revise.class);
                                intent.putExtra("id",id);
                                startActivity(intent);

                            }
                        })
                        .setNeutralButton("刪除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.delete(id);
                                setAdapter();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

            }
        });


    }


    public void setAdapter() {
        cursor = db.readAll(); //put words first! then read
        cursor.moveToFirst();
        String col = "";
        for (int i = 0; i < 3; i++) {
            col += cursor.getColumnName(i) + "\t\t";
        }
        Log.d("Cursor", col);
        SimpleCursorAdapter sca = new SimpleCursorAdapter(
                this,
                R.layout.item_view,
                cursor,
                new String[]{"_id", "title", "created"},
                new int[]{R.id.text1, R.id.text2, R.id.text3},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(sca);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                Intent intent = new Intent(this, EditActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}