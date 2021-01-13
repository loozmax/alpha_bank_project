package CardOfMine;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication543543.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import CardAddedByMe.CardActivity;

public class RedoCard extends AppCompatActivity {

    EditText title_input, author_input, pages_input, appeal_input, organisation_input, phone_input, email_input, adres_input, vk_input, fb_input;
    Button redo;
    String delete, id, title, author, pages, appeal, organisation, phone, email, adres, vk, fb;
    ImageView deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redo_my_card);

        ImageView back = (ImageView) findViewById(R.id.arrow_back);
        back.setOnClickListener(new View.OnClickListener() {
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

        redo = findViewById(R.id.redo_button);

        Intent intent = getIntent();
        delete = intent.getStringExtra("delete");
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("lastname");
        author = intent.getStringExtra("fullName");
        pages = intent.getStringExtra("otchestvo");
        appeal = intent.getStringExtra("appeal");
        organisation = intent.getStringExtra("organisation");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        adres = intent.getStringExtra("adres");
        vk = intent.getStringExtra("vk");
        fb = intent.getStringExtra("fb");

        title_input.setText(author);
        author_input.setText(title);
        pages_input.setText(pages);
        appeal_input.setText(appeal);
        organisation_input.setText(organisation);
        phone_input.setText(phone);
        email_input.setText(email);
        adres_input.setText(adres);
        vk_input.setText(vk);
        fb_input.setText(fb);


        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uTitle, uAuthor, uPages, uAppeal, uOrganisation, uPhone, uEmail, uAdres, uVk, uFb;
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data").child(delete);
                uTitle = title_input.getText().toString();
                uAuthor = author_input.getText().toString();
                uPages = pages_input.getText().toString();
                uAppeal = appeal_input.getText().toString();
                uOrganisation = organisation_input.getText().toString();
                uPhone = phone_input.getText().toString();
                uEmail = email_input.getText().toString();
                uAdres = adres_input.getText().toString();
                uVk = vk_input.getText().toString();
                uFb = fb_input.getText().toString();

                UserData userData = new UserData(null, delete, id, uAuthor, uTitle, uPages, uAppeal, uOrganisation, uPhone, uAdres, uEmail, uVk, uFb);
                databaseReference.setValue(userData);

                Intent intent = new Intent(RedoCard.this, MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        deleteBtn = findViewById(R.id.deleteThisCard);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User Data").child(delete);
                databaseReference.removeValue();
                Intent intent = new Intent(RedoCard.this, MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}