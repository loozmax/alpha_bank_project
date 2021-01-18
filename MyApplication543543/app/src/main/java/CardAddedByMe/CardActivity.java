package CardAddedByMe;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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
import java.util.List;

import CardOfMine.MainActivity;

public class CardActivity extends AppCompatActivity implements CardAdapter.ConfirmListenerInterface {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private static final String VK_APP_PACKAGE_ID = "com.vkontakte.android";
    private static final String FACEBOOK_APP_PACKAGE_ID = "com.facebook.katana";
    RecyclerView recyclerView;
    TextView tvAddByQr;
    TextView showJustFavoriteCutaways;
    private ArrayList<CardData> cardDataArrayList;
    private CardAdapter customAdapter;
    DatabaseReference dRef;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    DatabaseReference database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add_cards);
        recyclerView = findViewById(R.id.rv);
        recyclerView.addItemDecoration(new SpacesItemDecoration(-25));
        tvAddByQr= findViewById(R.id.textView4);

        tvAddByQr.setOnClickListener((v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }else {
                    Intent myIntent = new Intent(this, AddСutawayByQrCode.class);
                    startActivity(myIntent);
                }
            }else {
                Intent myIntent = new Intent(this, AddСutawayByQrCode.class);
                startActivity(myIntent);
            }


        }));
        showJustFavoriteCutaways= findViewById(R.id.textView5);
        showJustFavoriteCutaways.setOnClickListener((v -> {
            customAdapter.setShowJustFavorite(!customAdapter.isShowJustFavorite());
            if(customAdapter.isShowJustFavorite()){
                ArrayList<CardData> filteredCardList= new ArrayList<>();
                for(CardData cardData:cardDataArrayList){
                    if(cardData.isFavorite()) filteredCardList.add(cardData);
                }
                customAdapter.setCardData(filteredCardList);
            }else {
                customAdapter.setCardData(cardDataArrayList);
            }
        }));

        ImageView arrow = (ImageView) findViewById(R.id.arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Card Data");
        userID = user.getUid();

        ImageView form = (ImageView) findViewById(R.id.button_new_form);
        form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ActivityFormAddNewCards.class);
                startActivityForResult(myIntent, 0);
            }
        });


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
    public void showItems(CardData cardData,int position) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
        builder.setTitle("Выберите действие");

        // add a list
        String[] animals = {"Добавить в контакты",(cardData.isFavorite)?"Убрать из избранного":"Добавить в избранное", "Удалить"};
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
                    case 1: {
                        cardData.setFavorite(!cardData.isFavorite);
                        dRef.child(cardData.parentKey).setValue(cardData, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(customAdapter.isShowJustFavorite()){
                                    ArrayList<CardData> filteredCardList= new ArrayList<>();
                                    for(CardData cardData:cardDataArrayList){
                                        if(cardData.isFavorite()) filteredCardList.add(cardData);
                                    }
                                    customAdapter.setCardData(filteredCardList);
                                }
                                customAdapter.notifyItemChanged(position);
                                Toast.makeText(CardActivity.this,"Success!",Toast.LENGTH_SHORT);
                            }
                        });
                    } break;

                    case 2: {
//                        database = FirebaseDatabase.getInstance().getReference("Card Data");
//                        database.child().removeValue();
//                        Intent intent = new Intent(CardActivity.this, CardActivity.class);
//                        startActivity(intent);
                        dRef.child(cardData.parentKey).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                customAdapter.removeItem(position);
                                Toast.makeText(CardActivity.this,"Success!",Toast.LENGTH_SHORT);
                            }
                        });
                    } break;
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void openLink(String uri) {
        if (!uri.startsWith("http://") && !uri.startsWith("https://"))
            uri = "http://" + uri;
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
