package CardOfMine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication543543.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivityNewCards extends AppCompatActivity {

    EditText title_input, author_input, pages_input, appeal_input, organisation_input, phone_input, email_input, adres_input, vk_input, fb_input;
    Button add_button;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_my_card);

        ImageView arrow = (ImageView) findViewById(R.id.arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        title_input = findViewById(R.id.author_input);
        author_input = findViewById(R.id.title_input);
        pages_input = findViewById(R.id.pages_input);
        appeal_input = findViewById(R.id.author_input4);
        organisation_input = findViewById(R.id.author_input5);
        phone_input = findViewById(R.id.author_input6);
        email_input = findViewById(R.id.author_input7);
        adres_input = findViewById(R.id.author_input8);
        vk_input = findViewById(R.id.author_input9);
        fb_input = findViewById(R.id.author_input10);

        add_button = findViewById(R.id.add_button);
        database = FirebaseDatabase.getInstance().getReference().child("User Data");

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId, userName, userLastName, userOtchestvo, userAppeal, userOrganisation, userPhone, userEmail, userVK, userFB;
                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                userName = title_input.getText().toString();
                userLastName = author_input.getText().toString();
                userOtchestvo = pages_input.getText().toString();
                userAppeal = appeal_input.getText().toString();
                userOrganisation = organisation_input.getText().toString();
                userPhone = phone_input.getText().toString();
                userEmail = email_input.getText().toString();
                userVK = vk_input.getText().toString();
                userFB = fb_input.getText().toString();

                UserData userData = new UserData(userId, userName, userLastName, userOtchestvo, userAppeal, userOrganisation, userPhone, userEmail, userVK, userFB);
                database.child(database.push().getKey()).setValue(userData);

            }
        });
    }
}