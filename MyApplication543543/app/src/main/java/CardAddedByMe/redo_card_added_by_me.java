package CardAddedByMe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication543543.R;
import com.example.myapplication543543.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import CardOfMine.ProfileRedo;
import CardOfMine.UserData;
import CardOfMine.activity_edit;

public class redo_card_added_by_me extends AppCompatActivity {

//    EditText name1;
//    Button save;
//
//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;
//    FirebaseUser user;
//    String userID;
//    StorageReference storageReference;
//    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redo_card_added_by_me);

//        ImageView arrow = (ImageView) findViewById(R.id.arrow_back);
//        arrow.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent myIntent = new Intent(view.getContext(), CardActivity.class);
//                startActivity(myIntent);
//            }
//        });

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        userID = user.getUid();
//        storageReference = FirebaseStorage.getInstance().getReference();
//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//
//        save = findViewById(R.id.save);
//        name1 = findViewById(R.id.fieldName);
//
//        Intent data = getIntent();
//        String name = data.getStringExtra("name");
//
//        name1.setText(name);

//        database = FirebaseDatabase.getInstance().getReference().child("Card Data");
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String delete, uName, userId;
////                delete = database.push().getKey();
////                uName = name1.getText().toString();
//
////                CardData userData = new CardData(null, delete, userId, uName, null, null, null, null, null, null, null, null);
////                database.child(delete).setValue(userData);
////                Intent intent = new Intent(redo_card_added_by_me.this, CardActivity.class);
////                startActivity(intent);
//            }
//        });
    }
}