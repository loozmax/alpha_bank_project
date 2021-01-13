package com.example.myapplication543543;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import CardOfMine.MainActivity;

public class RegisterUser extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword, editTextPhone;
    private ProgressBar progressBar;
    Button registerUser;
    ConstraintLayout coordLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        mAuth = FirebaseAuth.getInstance();
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextPhone = (EditText) findViewById(R.id.phone);
        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);
        coordLayout = findViewById(R.id.root_element);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ImageView arrow = (ImageView) findViewById(R.id.arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Login.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registerUser) {
            registerUser();
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();


        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, age, email, phone);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Snackbar.make(
                                                coordLayout,
                                                "Пользователь успешно зарегистрирован",
                                                Snackbar.LENGTH_LONG
                                        ).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent myIntent = new Intent(RegisterUser.this, Login.class);
                                        startActivity(myIntent);

                                    } else progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            Snackbar.make(
                                    coordLayout,
                                    "Ошибка при регистрации. Проверьте правильность введенных данных",
                                    Snackbar.LENGTH_LONG
                            ).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}