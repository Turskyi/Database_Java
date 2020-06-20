package com.example.room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RoomItemsDatabase database;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = Room.databaseBuilder(this, RoomItemsDatabase.class, "roomDB")
                .allowMainThreadQueries()
                .build();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.textView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View view1 = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.view_dialog, null);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Create new room")
                        .setView(view1)
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                EditText edtName = view1.findViewById(R.id.editName);
                                EditText edtDescription = view1.findViewById(R.id.edtDescription);
                                EditText edtAmount = view1.findViewById(R.id.amount);

                                EditText edtParent = view1.findViewById(R.id.edtParent);
                                final String parentName = edtParent.getText().toString();

                                RoomDataBaseItem item = new RoomDataBaseItem();
                                item.name = edtName.getText().toString();
                                item.description = edtDescription.getText().toString();
                                item.amount = Integer.parseInt(edtAmount.getText().toString());

                                database.getRoomItemDao().addItem(item);
                            }
                        }).create().show();
            }
        });
    }

    private void showDatabase() {

        StringBuilder textResult = new StringBuilder(" ");
        List<RoomDataBaseItem> items = database.getRoomItemDao().getAllItems();
        for (int i = 0; i < items.size(); i++) {
            textResult.append(items.get(i).toString());
        }


//                for (int i = 0; i < result.size(); i++) {
//                    textResult += result.get(i).toString();
//                }

        final String finalTextResult = textResult.toString();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(finalTextResult);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showDatabase();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
