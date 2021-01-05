package CardAddedByMe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplication543543.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import CardOfMine.UserData;

public class ActivityFormAddNewCards extends Activity {

    EditText title_input, author_input, pages_input, appeal_input, organisation_input, phone_input, email_input, adres_input, vk_input, fb_input;
    Button add_button;
    ImageButton imageButton;
    DatabaseReference database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        ImageView back = (ImageView) findViewById(R.id.arrow_back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CardActivity.class);
                startActivity(myIntent);
            }
        });

        imageButton = findViewById(R.id.imageButton);
        title_input = findViewById(R.id.nameInput);
        author_input = findViewById(R.id.firstNameText);
        pages_input = findViewById(R.id.lastNameText);
        appeal_input = findViewById(R.id.editTextTextPersonName1);
        organisation_input = findViewById(R.id.editTextTextPersonName6);
        phone_input = findViewById(R.id.editTextTextPersonName7);
        email_input = findViewById(R.id.editTextTextPersonName8);
        adres_input = findViewById(R.id.editTextTextPersonName9);
        vk_input = findViewById(R.id.editTextTextPersonName10);
        fb_input = findViewById(R.id.editTextTextPersonName11);

        add_button = findViewById(R.id.saveForm);
        database = FirebaseDatabase.getInstance().getReference().child("Card Data");
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delete, userId, userName, userLastName, userOtchestvo, userAppeal, userOrganisation, userPhone, userAdres, userEmail, userVK, userFB;
                delete = database.push().getKey();
                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                userName = title_input.getText().toString();
                userLastName = author_input.getText().toString();
                userOtchestvo = pages_input.getText().toString();
                userAppeal = appeal_input.getText().toString();
                userOrganisation = organisation_input.getText().toString();
                userPhone = phone_input.getText().toString();
                userAdres = adres_input.getText().toString();
                userEmail = email_input.getText().toString();
                userVK = vk_input.getText().toString();
                userFB = fb_input.getText().toString();

                UserData userData = new UserData(delete, userId, userName, userLastName, userOtchestvo, userAppeal, userOrganisation, userPhone, userAdres, userEmail, userVK, userFB);
                database.child(database.push().getKey()).setValue(userData);
            }
        });

    }
}