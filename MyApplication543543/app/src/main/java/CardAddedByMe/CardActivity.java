package CardAddedByMe;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication543543.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import CardOfMine.UserData;
import SettingActivity.SpacesItemDecoration;

import java.util.ArrayList;

import CardOfMine.MainActivity;

public class CardActivity extends AppCompatActivity implements CardAdapter.ConfirmListenerInterface {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    RecyclerView recyclerView;
    TextView tvAddByQr, favourites;
    private ArrayList<CardData> cardDataArrayList;
    private CardAdapter customAdapter;
    DatabaseReference dRef;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add_cards);
        favourites = findViewById(R.id.favourites);
        user = FirebaseAuth.getInstance().getCurrentUser();
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardActivity.this, Favourites.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.rv);
        recyclerView.addItemDecoration(new SpacesItemDecoration(-73));
        tvAddByQr= findViewById(R.id.textView4);
        tvAddByQr.setOnClickListener((v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }else {
                    Intent myIntent = new Intent(this, AddСutawayByQrCode.class);
                    startActivityForResult(myIntent, 0);
                }
            }else {
                Intent myIntent = new Intent(this, AddСutawayByQrCode.class);
                startActivityForResult(myIntent, 0);
            }

        }));
        ImageView arrow = (ImageView) findViewById(R.id.arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Card Data");
        if (user != null) {
            userID = user.getUid();
            Toast.makeText(CardActivity.this, userID, Toast.LENGTH_SHORT).show();
        }

        ImageView form = (ImageView) findViewById(R.id.button_new_form);
        form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(CardActivity.this, ActivityFormAddNewCards.class);
                startActivity(myIntent);
            }
        });
        recyclerView.addItemDecoration(new SpacesItemDecoration(110));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent myIntent = new Intent(this, AddСutawayByQrCode.class);
                startActivityForResult(myIntent, 0);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardDataArrayList = new ArrayList<CardData>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    CardData uData = dataSnapshot1.getValue(CardData.class);
                    uData.setParentKey(dataSnapshot1.getKey());
                    if (uData.getId().trim().equals(userID)) {
                        cardDataArrayList.add(uData);
                    }
                }

                customAdapter = new CardAdapter(CardActivity.this, cardDataArrayList,CardActivity.this);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        //      dRef = FirebaseDatabase.getInstance().getReference().child("User Data").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dRef = FirebaseDatabase.getInstance().getReference().child("Card Data");
        dRef.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void confirmCardData(CardData cardData,int position) {
        cardData.needToConfirm=false;
        dRef.child(cardData.parentKey).setValue(cardData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                customAdapter.notifyItemChanged(position);
                Toast.makeText(CardActivity.this,"Success!",Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void deleteCardData(CardData cardData,int position) {
            dRef.child(cardData.parentKey).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    customAdapter.removeItem(position);
                    Toast.makeText(CardActivity.this,"Success!",Toast.LENGTH_SHORT);
                }
            });
    }
    @Override
    public void showItems(CardData cardData) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
        builder.setTitle("Выберите действие");

        // add a list
        String[] animals = {"Добавить в контакты"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        intent .putExtra(ContactsContract.Intents.Insert.PHONE, cardData.userPhone);
                        intent .putExtra(ContactsContract.Intents.Insert.NAME, cardData.userName);
                        startActivity(intent);
                    } break;

                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
