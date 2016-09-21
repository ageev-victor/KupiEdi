package ru.ageev_victor.kupiedi.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ru.ageev_victor.kupiedi.Activity.MainActivity;
import ru.ageev_victor.kupiedi.R;

public class Row extends TableRow {
    EditText edTxtFoodCunt;
    ImageButton btnRemoveRow;
    TextView txtRowFoodName;
    double foodCount = 1;

    public Row(Context context, String foodName) {
        super(context);
        initViews(foodName);
        addViewsToRow();
    }

    private void addViewsToRow() {
        this.addView(txtRowFoodName);
        this.addView(edTxtFoodCunt);
        this.addView(btnRemoveRow);
    }

    private void initViews(String foodName) {
        this.setBackgroundResource(R.drawable.row);
        createViews();
        txtRowFoodName.setText(foodName);
        setParamsToViews();
    }

    private void setParamsToViews() {
        initTxtRowFoodName();
        initEdTxtFoodCunt();
        initBtnRemoveRow();
    }

    private void createViews() {
        edTxtFoodCunt = new EditText(getContext().getApplicationContext());
        btnRemoveRow = new ImageButton(getContext().getApplicationContext());
        txtRowFoodName = new TextView(getContext().getApplicationContext());
    }

    private void initEdTxtFoodCunt() {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(4);
        edTxtFoodCunt.setFilters(filterArray);
        edTxtFoodCunt.setMaxLines(1);
        edTxtFoodCunt.setText(String.valueOf(foodCount));
        edTxtFoodCunt.setTextColor(Color.BLACK);
        edTxtFoodCunt.setTextSize(MainActivity.defaultTextSize);
        edTxtFoodCunt.setTypeface(MainActivity.defaultTypeface);
        edTxtFoodCunt.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    private void initBtnRemoveRow() {
        btnRemoveRow.setBackgroundResource(R.drawable.btn_delete_normal);
        btnRemoveRow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.removeRow((TableRow) view.getParent());
            }
        });
    }

    private void initTxtRowFoodName() {
        txtRowFoodName.setTextSize(MainActivity.defaultTextSize);
        txtRowFoodName.setTextColor(Color.BLACK);
        txtRowFoodName.setTypeface(MainActivity.defaultTypeface);
    }

    public EditText getEdTxtFoodCunt() {
        return edTxtFoodCunt;
    }

    public TextView getTxtRowFoodName() {
        return txtRowFoodName;
    }

    public void setFoodCount(double foodCount) {
        edTxtFoodCunt.setTypeface(MainActivity.defaultTypeface);
        edTxtFoodCunt.setText(String.valueOf(foodCount));
        edTxtFoodCunt.setTextSize(MainActivity.defaultTextSize);
    }
}
