package ru.ageev_victor.kupiedi.Objects;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;

import ru.ageev_victor.kupiedi.Activity.MainActivity;
import ru.ageev_victor.kupiedi.R;

public class Row extends TableRow {
    EditText edTxtFoodCunt;
    ImageButton btnRemoveRow;
    TextView txtRowFoodName;
    static int btn_delete_drawableRes = (R.drawable.btn_delete_normal);
    private String foodname;

    public Row(Context context, String foodName) {
        super(context);
        this.foodname = foodName;
        initViews();
        addViewsToRow();
    }

    public Row(Context context) {
        super(context);
    }

    private void addViewsToRow() {
        this.addView(txtRowFoodName);
        this.addView(edTxtFoodCunt);
        this.addView(btnRemoveRow);
    }

    private void initViews() {
        createViews();
        setParamsToViews();
    }

    private void setParamsToViews() {
        initTxtRowFoodName();
        initEdTxtFoodCunt();
        initBtnRemoveRow();
        this.setBackgroundResource(R.drawable.row);
    }

    private void createViews() {
        edTxtFoodCunt = new EditText(getContext().getApplicationContext());
        btnRemoveRow = new ImageButton(getContext().getApplicationContext());
        txtRowFoodName = new TextView(getContext().getApplicationContext());
    }

    private void initEdTxtFoodCunt() {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(6);
        edTxtFoodCunt.setFilters(filterArray);
        edTxtFoodCunt.setGravity(Gravity.CENTER_HORIZONTAL);
        edTxtFoodCunt.setMaxLines(1);
        edTxtFoodCunt.setText("1");
        edTxtFoodCunt.setTextColor(Color.BLACK);
        edTxtFoodCunt.setTextSize(MainActivity.defaultTextSize);
        edTxtFoodCunt.setTypeface(MainActivity.defaultTypeface);
        edTxtFoodCunt.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    public void initBtnRemoveRow() {
        btnRemoveRow.setBackgroundResource(btn_delete_drawableRes);
        btnRemoveRow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.removeRow((TableRow) view.getParent());
            }
        });
    }

    private void initTxtRowFoodName() {
        txtRowFoodName.setText(foodname);
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

    public void setFoodCount(float foodCount) {
        if (String.valueOf(foodCount).contains(".0")) {
            edTxtFoodCunt.setText(String.valueOf((int) foodCount));
        } else {
            edTxtFoodCunt.setText(String.valueOf(foodCount));
        }
        edTxtFoodCunt.setTypeface(MainActivity.defaultTypeface);
        edTxtFoodCunt.setTextSize(MainActivity.defaultTextSize);
    }
}
