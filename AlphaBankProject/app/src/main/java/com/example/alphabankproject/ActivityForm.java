package com.example.alphabankproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityForm extends Activity {

    String name, firstName, lastName;
    EditText nameInput, firstNameText, lastNameText;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_form);

        nameInput = (EditText) findViewById(R.id.nameInput);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);

        Button submitButton = (Button) findViewById(R.id.saveForm);

        Button next = (Button) findViewById(R.id.Button02);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                name = nameInput.getText().toString();
                firstName = firstNameText.getText().toString();
                lastName = lastNameText.getText().toString();
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(ActivityForm.this, text, Toast.LENGTH_SHORT).show();
    }
}