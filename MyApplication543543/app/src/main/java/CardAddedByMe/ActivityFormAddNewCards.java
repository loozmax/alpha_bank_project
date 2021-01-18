package CardAddedByMe;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication543543.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import CardOfMine.UserData;


public class ActivityFormAddNewCards extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText title_input, author_input, pages_input, appeal_input, organisation_input, phone_input, email_input, adres_input, vk_input, fb_input;
    Button add_button;
    ImageView imageButton;
    DatabaseReference database;
    Uri downloadUri;
    DatabaseReference mDatabaseRef;
    StorageReference storageReference;
    private Uri mImageUri;
    private StorageTask mUploadTask;
    ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        ImageView back = (ImageView) findViewById(R.id.arrow_back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CardActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        imageButton = findViewById(R.id.imageButton);
        title_input = findViewById(R.id.nameInput);
        progressBar= findViewById(R.id.progressBar3);
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

                UserData userData = new UserData((downloadUri!=null)?downloadUri.toString():null, delete, userId, userName, userLastName, userOtchestvo, userAppeal, userOrganisation, userPhone, userAdres, userEmail, userVK, userFB);
                database.child(delete).setValue(userData);
                Intent intent = new Intent(ActivityFormAddNewCards.this, CardActivity.class);
                startActivity(intent);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("uploads2");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads2");

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imageButton);
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                //TODO
            } else {
                uploadFile();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            progressBar.setVisibility(View.VISIBLE);
            storageReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                         downloadUri = task.getResult();
                         progressBar.setVisibility(View.GONE);
                       }
                }
            });
        }
    }
}