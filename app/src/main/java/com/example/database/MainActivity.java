package com.example.database;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/* fill up the form and sending it to data base and then showing the information */
public class MainActivity extends AppCompatActivity {


    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.textView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                @SuppressLint("InflateParams") final View view1 = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.view_dialog, null);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Create new realm")
                        .setView(view1)
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final RealmDatabaseItem item = new RealmDatabaseItem();
                                EditText edtName = view1.findViewById(R.id.editName);
                                item.name = edtName.getText().toString();

                                EditText edtParent = view1.findViewById(R.id.edtParent);
                                final String parentName = edtParent.getText().toString();
                              Realm realm = Realm.getDefaultInstance();
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(@NonNull Realm realm) {
                                        RealmParentItem parentItem =
                                                realm.where(RealmParentItem.class)
                                                        .equalTo("name", parentName)
                                                        .findFirst();

                                        if (parentItem == null) {
                                            RealmParentItem newItem = new RealmParentItem();
                                            newItem.name = parentName;
                                            newItem.items = new RealmList<>();
                                            newItem.items.add(item);
                                            realm.copyToRealmOrUpdate(newItem);
                                        } else {
                                            parentItem.items.add(item);
                                            realm.copyToRealmOrUpdate(parentItem);
                                        }
                                        realm.copyToRealmOrUpdate(item);
                                    }
                                });

                            }
                        }).create().show();
            }
        });
    }

    private void showDatabase() {
      Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmResults<RealmDatabaseItem> result;
                result = realm.where(RealmDatabaseItem.class).findAll();

                /*
                  Examples of requests with segregation
                 */
//                realm.where(RealmDatabaseItem.class)
//                        .equalTo("amount", 10)
//                .findFirst();
//                realm.where(RealmDatabaseItem.class)
//                        .between("amount", 5, 50)
//                        .findAll();
                realm.where(RealmDatabaseItem.class)
                        .beginsWith("name", "_")
                        .findAll();

                RealmResults parentResults =
                        realm.where(RealmParentItem.class)
                                .findAll();


//                List<RealmDatabaseItem> result = realm.where(RealmDatabaseItem.class).findAll();
                StringBuilder textResult = new StringBuilder(" ");

                for (int i = 0; i < parentResults.size(); i++) {
                    if (parentResults.get(i) != null) {
                        textResult.append(Objects.requireNonNull(parentResults.get(i)).toString());
                    }
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
