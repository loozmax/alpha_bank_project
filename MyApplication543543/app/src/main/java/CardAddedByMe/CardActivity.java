package CardAddedByMe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
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
import SettingActivity.SpacesItemDecoration;

import java.util.ArrayList;

import CardOfMine.MainActivity;

public class CardActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    RecyclerView recyclerView;

    private ArrayList<CardData> cardData;
    private CardAdapter customAdapter;
    DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add_cards);
        recyclerView = findViewById(R.id.rv);
        recyclerView.addItemDecoration(new SpacesItemDecoration(-73));

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardData = new ArrayList<CardData>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    CardData uData = dataSnapshot1.getValue(CardData.class);
                    if (uData.getId().trim().equals(userID)) {
                        cardData.add(uData);
                    }
                }

                customAdapter = new CardAdapter(CardActivity.this, cardData);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };

        //      dRef = FirebaseDatabase.getInstance().getReference().child("User Data").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dRef = FirebaseDatabase.getInstance().getReference().child("Card Data");
        dRef.addListenerForSingleValueEvent(valueEventListener);
    }
}
