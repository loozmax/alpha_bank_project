package CardOfMine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication543543.R;
import com.example.myapplication543543.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import CardAddedByMe.CardActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class activity_edit extends AppCompatActivity {

    EditText name1, age1, phone1, email1;
    CircleImageView profileImageView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    Button saveBtn;
    String userID;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        profileImageView = findViewById(R.id.imageView2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

        ImageView arrow = (ImageView) findViewById(R.id.arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ProfileRedo.class);
                startActivity(myIntent);
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        saveBtn = findViewById(R.id.saveProfile);

        name1 = findViewById(R.id.profileName);
        age1 = findViewById(R.id.profileAge);
        phone1 = findViewById(R.id.profilePhone);
        email1 = findViewById(R.id.profileEmail);

        Intent data = getIntent();
        String fullName = data.getStringExtra("fullName");
        String age = data.getStringExtra("age");
        String phone = data.getStringExtra("phone");
        String email = data.getStringExtra("email");

        name1.setText(fullName);
        age1.setText(age);
        phone1.setText(phone);
        email1.setText(email);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uPhone, uName, uAge, uEmail;
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                uPhone = phone1.getText().toString();
                uName = name1.getText().toString();
                uAge = age1.getText().toString();
                uEmail = email1.getText().toString();

                User user = new User(uName, uAge, uEmail, uPhone);
                databaseReference.setValue(user);

                Intent intent = new Intent(activity_edit.this, ProfileRedo.class);
                v.getContext().startActivity(intent);
            }
        });

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        final StorageReference fileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });
            }
        });
    }
}