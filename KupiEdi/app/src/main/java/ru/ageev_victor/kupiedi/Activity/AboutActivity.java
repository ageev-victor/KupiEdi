package ru.ageev_victor.kupiedi.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import ru.ageev_victor.kupiedi.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void sendMessageToDeveloper(View view) {
        final EditText dialogEmailEdText = new EditText(this);
        final String[] email = new String[1];
        new AlertDialog.Builder(AboutActivity.this)
                .setTitle(getString(R.string.write_your_problem))
                .setView(dialogEmailEdText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        email[0] = dialogEmailEdText.getText().toString();
                        Intent sendMailIntent = new Intent(Intent.ACTION_SEND);
                        sendMailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.my_mail) });
                        sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.question_bug));
                        sendMailIntent.setType("plain/text");
                        sendMailIntent.putExtra(Intent.EXTRA_TEXT, email[0]);
                        startActivity(sendMailIntent);
                    }
                })
                .create()
                .show();
    }
}
