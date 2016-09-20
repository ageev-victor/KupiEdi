package ru.ageev_victor.kupiedi.Activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ru.ageev_victor.kupiedi.Objects.DataFromDataBase;
import ru.ageev_victor.kupiedi.Objects.DatabaseHelper;
import ru.ageev_victor.kupiedi.Objects.Finder;
import ru.ageev_victor.kupiedi.Objects.Row;
import ru.ageev_victor.kupiedi.R;

public class MainActivity extends AppCompatActivity {

    ImageButton btnAddFood;
    Button foodBtn1;
    Button foodBtn2;
    Button foodBtn3;
    EditText edTxtEnterFood;
    static TableLayout tableListFood;
    public static ArrayList<Row> rows = new ArrayList<>();
    public static Typeface defaultTypeface;
    public static int defaultTextSize = 18;
    LinearLayout buttonsLayout;
    RelativeLayout mainLayout;
    Finder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
        finder = new Finder(this.getApplicationContext());
    }

    private void setupFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableListFood.removeAllViews();
                rows.clear();
                if (mainLayout.getChildCount() == 3) {
                    edTxtEnterFood.setText("");
                    mainLayout.removeView(buttonsLayout);

                }
                Snackbar.make(view, R.string.list_cleared, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTable("tempTable");
        setFontSize();
        setFontStyle();
        DatabaseHelper.getInstance(this).deleteTable("tempTable");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!(rows.isEmpty())) {
            saveList("tempTable");
        }
    }


    private void setFontSize() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        defaultTextSize = (int) Float.parseFloat(prefs.getString(getString(R.string.settings), "16"));
        if (defaultTextSize < 14 | defaultTextSize > 22) {
            Toast.makeText(this, R.string.max_font_size_warning, Toast.LENGTH_SHORT).show();
        } else {
            for (Row row : rows) {
                row.getTxtRowFoodName().setTextSize(defaultTextSize);
                row.getEdTxtFoodCunt().setTextSize(defaultTextSize);
                Log.d("Size", String.valueOf(defaultTextSize));
            }
        }
    }


    private void setFontStyle() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String font = prefs.getString(getString(R.string.font_style), "");
        if (font.contains(getString(R.string.amoderno)))
            defaultTypeface = Typeface.createFromAsset(getResources().getAssets(), "amoderno.ttf");
        if (font.contains(getString(R.string.aalbionic_bold)))
            defaultTypeface = Typeface.createFromAsset(getResources().getAssets(), "aalbionic_bold.ttf");
        if (font.contains(getString(R.string.aalternanr)))
            defaultTypeface = Typeface.createFromAsset(getResources().getAssets(), "aalternanr.ttf");
        if (font.contains(getString(R.string.bauhauslightctt_bold)))
            defaultTypeface = Typeface.createFromAsset(getResources().getAssets(), "bauhauslightctt_bold.ttf");
        for (Row row : rows) {
            edTxtEnterFood.setTypeface(defaultTypeface);
            row.getTxtRowFoodName().setTypeface(defaultTypeface);
            row.getEdTxtFoodCunt().setTypeface(defaultTypeface);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.savelist:
                final EditText dialogSaveEdText = new EditText(this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.enter_name_list))
                        .setView(dialogSaveEdText)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    saveList(dialogSaveEdText.getText().toString());
                                    Toast.makeText(getApplicationContext(), R.string.list_save, Toast.LENGTH_SHORT).show();
                                } catch (RuntimeException r) {
                                    Toast.makeText(getApplicationContext(), R.string.name_must_contain_letters, Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.loadList:
                final CharSequence[] lists = DatabaseHelper.getInstance(this).loadList();
                if (lists.length > 0) {
                    new AlertDialog.Builder(this)
                            .setItems(lists, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tableListFood.removeAllViews();
                                    loadTable(lists[i].toString());
                                    Toast.makeText(getApplicationContext(), R.string.list_is_load, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();
                } else {
                    Toast.makeText(this, R.string.no_saved_lists, Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.settings:
                Intent settingsIntent = new Intent();
                settingsIntent.setClass(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.about:
                Intent aboutIntent = new Intent();
                aboutIntent.setClass(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        foodBtn1 = (Button) findViewById(R.id.btnFood1);
        foodBtn2 = (Button) findViewById(R.id.btnFood2);
        foodBtn3 = (Button) findViewById(R.id.btnFood3);
        btnAddFood = (ImageButton) findViewById(R.id.btnAddFood);
        edTxtEnterFood = (EditText) findViewById(R.id.edTxtEnterFood);
        tableListFood = (TableLayout) findViewById(R.id.tableListFood);
        assert tableListFood != null;
        tableListFood.setShrinkAllColumns(true);
        tableListFood.setColumnStretchable(0, true);
        tableListFood.setColumnStretchable(1, true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFloatingButton();
        addOnClickListenersToButtons();
        addOnClickListenerToEditText();
        setFontSize();
        setFontStyle();
        buttonsLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
    }

    private void addOnClickListenerToEditText() {
        edTxtEnterFood.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                finder.foodMatches.clear();
                doAllButtonsInvisible();
                ArrayList<String> foodMatches = finder.getMatches(s);
                if (s.length() == 0 & (mainLayout.getChildCount() > 2)) {
                    mainLayout.removeView(buttonsLayout);
                } else {
                    if (mainLayout.getChildCount() > 2) {
                        mainLayout.removeView(buttonsLayout);
                    }
                    mainLayout.addView(buttonsLayout);
                    switch (foodMatches.size()) {
                        case 1: {
                            foodBtn1.setVisibility(View.VISIBLE);
                            foodBtn1.setText(foodMatches.get(0));
                            break;
                        }
                        case 2: {
                            foodBtn1.setText(foodMatches.get(0));
                            foodBtn2.setText(foodMatches.get(1));
                            foodBtn1.setVisibility(View.VISIBLE);
                            foodBtn2.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    if (foodMatches.size() >= 3) {
                        foodBtn1.setText(foodMatches.get(0));
                        foodBtn2.setText(foodMatches.get(1));
                        foodBtn3.setText(foodMatches.get(2));
                        doAllButtonsVisible();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void doAllButtonsVisible() {
        foodBtn1.setVisibility(View.VISIBLE);
        foodBtn2.setVisibility(View.VISIBLE);
        foodBtn3.setVisibility(View.VISIBLE);
    }

    private void doAllButtonsInvisible() {
        foodBtn1.setVisibility(View.INVISIBLE);
        foodBtn2.setVisibility(View.INVISIBLE);
        foodBtn3.setVisibility(View.INVISIBLE);
    }

    private void addOnClickListenersToButtons() {
        foodBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Row row = new Row(getApplicationContext(), foodBtn1.getText().toString());
                rows.add(row);
                tableListFood.addView(row);
                edTxtEnterFood.setText("");
                doAllButtonsInvisible();
                mainLayout.removeView(buttonsLayout);
            }
        });
        foodBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Row row = new Row(getApplicationContext(), foodBtn2.getText().toString());
                rows.add(row);
                tableListFood.addView(row);
                edTxtEnterFood.setText("");
                doAllButtonsInvisible();
                mainLayout.removeView(buttonsLayout);

            }
        });
        foodBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Row row = new Row(getApplicationContext(), foodBtn3.getText().toString());
                rows.add(row);
                tableListFood.addView(row);
                edTxtEnterFood.setText("");
                doAllButtonsInvisible();
                mainLayout.removeView(buttonsLayout);

            }
        });
    }

    public static void removeRow(TableRow row) {
        tableListFood.removeView(row);
        rows.remove(row);
    }

    public void saveList(String tableName) {
        DatabaseHelper.getInstance(this).createTable(tableName);
        for (Row row : rows) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.PRODUCT_NAME, (String) row.getTxtRowFoodName().getText());
            values.put(DatabaseHelper.PRODUCT_COUNT, row.getEdTxtFoodCunt().getText().toString());
            DatabaseHelper.getInstance(this).getDataBase().insert(tableName, null, values);
        }
        rows.clear();
    }

    public void addFoodToTable(View view) {
        String foodname = edTxtEnterFood.getText().toString();
        if (!(foodname.equals("") | foodname.equals(" "))) {
            Row row = new Row(this, edTxtEnterFood.getText().toString());
            tableListFood.addView(row);
            edTxtEnterFood.setText(" ");
            rows.add(row);
        }
    }

    private void loadTable(String list) {
        try {
            tableListFood.removeAllViews();
            rows.clear();
            ArrayList<DataFromDataBase> dataArray = DatabaseHelper.getInstance(this).getData(list);
            for (DataFromDataBase dataRow : dataArray) {
                Row row = new Row(this, dataRow.getProductName());
                setFontStyle();
                setFontSize();
                row.setFoodCount(dataRow.getProductCount());
                tableListFood.addView(row);
                rows.add(row);
            }
            dataArray.clear();
        } catch (SQLiteException e) {
        }
    }

    public void delListFromDataBase(MenuItem item) {
        final CharSequence[] lists = DatabaseHelper.getInstance(this).loadList();
        if (lists.length > 0) {
            new AlertDialog.Builder(this)
                    .setItems(lists, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseHelper.getInstance(getApplicationContext()).deleteTable(String.valueOf(lists[i]));
                            Toast.makeText(getApplicationContext(), R.string.list_delete, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create()
                    .show();
        } else {
            Toast.makeText(this, R.string.no_saved_lists, Toast.LENGTH_LONG).show();
        }
    }
}

