package CardOfMine;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication543543.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddActivityNewCards extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText title_input, author_input, pages_input, appeal_input, organisation_input, phone_input, email_input, adres_input, vk_input, fb_input;
    Button add_button;
    DatabaseReference database;
    DatabaseReference mDatabaseRef;
    StorageReference storageReference;
    ImageView uploadImgs;
    Uri downloadUri;
    private Uri mImageUri=null;
    private StorageTask mUploadTask;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_my_card);

        ImageView arrow = (ImageView) findViewById(R.id.arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        uploadImgs = findViewById(R.id.uploadImgs);
        progressBar= findViewById(R.id.progressBar2);
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



                Intent intent = new Intent(AddActivityNewCards.this, MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        uploadImgs.setOnClickListener(new View.OnClickListener() {
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
            Picasso.get().load(mImageUri).into(uploadImgs);
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

        if (mImageUri != null)
        {
            progressBar.setVisibility(View.VISIBLE);
            storageReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        progressBar.setVisibility(View.GONE);
                        System.out.println("onComplete" + task.getResult());
                         downloadUri = task.getResult();
                       //UserData upload = new UserData(downloadUri.toString(), null, null, null, null, null, null, null, null, null, null, null, null);
                       //mDatabaseRef.push().setValue(upload);
                    }
                }
            });
        }
    }
}