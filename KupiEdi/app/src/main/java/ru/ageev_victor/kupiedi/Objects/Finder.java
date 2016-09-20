package ru.ageev_victor.kupiedi.Objects;

import android.content.Context;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import ru.ageev_victor.kupiedi.R;

public class Finder {

    private Context context;
    public ArrayList<String> foodNames = new ArrayList<>();
    public ArrayList<String> foodMatches = new ArrayList<>();

    public Finder(Context context) {
        this.context = context;
        initFoodDB();
    }

    public ArrayList<String> getMatches(CharSequence ch) {
        for (String name : foodNames) {
            if (name.startsWith(String.valueOf(ch))) {
                foodMatches.add(name);
            }
        }
        return foodMatches;
    }

    private void initFoodDB() {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.foodbase);
            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                while ((line = reader.readLine()) != null) {
                    foodNames.add(line);
                }
                inputStream.close();
            }
        } catch (Throwable t) {
            Toast.makeText(context.getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
