package CardOfMine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import CardAddedByMe.CardActivity;
import com.example.myapplication543543.R;
import SettingActivity.Settings;
import SettingActivity.SpacesItemDecoration;
import de.hdodenhof.circleimageview.CircleImageView;
import utils.QRCodeEncoder;

import com.example.myapplication543543.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        UserAdapter.UserActionInterface, UserListAdapter.UserListActionInterface {

    Context context = this;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    FirebaseAuth fAuth;
    StorageReference storageReference;
    CircleImageView userImg;

    DrawerLayout drawerLayout;
    RecyclerView recyclerView,rvUsers;
    ImageView add_button;
    ConstraintLayout usersLayout;
    TextView tvCutawayDescription;

    private ArrayList<UserData> userData;
    private ArrayList<User> users;
    private UserAdapter userAdapter;
    private UserListAdapter userListAdapter;
    DatabaseReference dRefUserData;
    DatabaseReference dRefCardData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersLayout = findViewById(R.id.usesrLayout);
        usersLayout.setOnClickListener((v -> {usersLayout.setVisibility(View.GONE);}));
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.rvUsers);
        tvCutawayDescription= findViewById(R.id.tvCutawayDescription);
        rvUsers = findViewById(R.id.rvUsersList);
        rvUsers.setLayoutManager(new GridLayoutManager(this,4));
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView.addItemDecoration(new SpacesItemDecoration(50));

        fAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        userImg = findViewById(R.id.uploadImgUser);

        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileRedo.class);
                startActivity(intent);
            }
        });

        users = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()) {
                   // System.out.println("dataSnapshot1 is "+ new Gson().toJson(dataSnapshot1));

                    User uData = dataSnapshot1.getValue(User.class);
                    uData.setId(dataSnapshot1.getKey());
                    System.out.println("dataSnapshot1 key is"+ dataSnapshot1.getKey());
                        users.add(uData);
                }
                userListAdapter = new UserListAdapter(users,MainActivity.this::shareCutaway);
                System.out.println("userListAdapter.getItemCount() is "+userListAdapter.getItemCount());
                rvUsers.setAdapter(userListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.nameActivity);
        final TextView phoneTextView = (TextView) findViewById(R.id.phone);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String fullName = userProfile.fullName;
                    String phone = userProfile.phone;
                    fullNameTextView.setText(fullName);
                    phoneTextView.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(userImg);
            }
        });

        TextView next = (TextView) findViewById(R.id.settings);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Settings.class);
                startActivity(myIntent);
            }
        });

        ImageView hamburger = (ImageView) findViewById(R.id.menu_ham);
        hamburger.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        TextView add = (TextView) findViewById(R.id.add_new_card);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CardActivity.class);
                startActivity(myIntent);
            }
        });

        TextView home = (TextView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivityNewCards.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userData = new ArrayList<UserData>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    UserData uData = dataSnapshot1.getValue(UserData.class);
                    if (uData.getId().trim().equals(userID)) {
                        userData.add(uData);
                    }
                }
                userAdapter = new UserAdapter(context, userData, MainActivity.this);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };


        //      dRef = FirebaseDatabase.getInstance().getReference().child("User Data").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dRefCardData=FirebaseDatabase.getInstance().getReference().child("Card Data");
        dRefUserData = FirebaseDatabase.getInstance().getReference().child("User Data");
        dRefUserData.addListenerForSingleValueEvent(valueEventListener);
    }

    private Bitmap generateQRCode(String encryptData) {
        // here encryptData data will be your data
        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        // Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(encryptData, null, BarcodeFormat.QR_CODE.toString(), smallerDimension);
        Bitmap bitmap = null;
        Bitmap bitMerged = null;
        try {
            bitmap = qrCodeEncoder.encodeAsBitmap();
            Bitmap myLogo = BitmapFactory.decodeResource(getResources(), R.drawable.girl_selfie);
            bitMerged = mergeBitmaps(bitmap,myLogo);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitMerged;

    }

    public static Bitmap mergeBitmaps(Bitmap qrCode, Bitmap myLogo) {
        Bitmap bmOverlay = Bitmap.createBitmap(qrCode.getWidth(), qrCode.getHeight(), qrCode.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(qrCode, new Matrix(), null);
        myLogo=Bitmap.createScaledBitmap(myLogo,qrCode.getWidth()/5,qrCode.getHeight()/5,false);
        canvas.drawBitmap(myLogo, (qrCode.getWidth() - myLogo.getWidth()) / 2, (qrCode.getHeight() - myLogo.getHeight()) / 2, null);

        return bmOverlay;
    }

    @Override
    public void showQrCode(UserData userData) {
        ImageView image = new ImageView(this);
        image.setImageBitmap(generateQRCode(new Gson().toJson(userData)));
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).
                        setMessage("Отсканируйте QR-код чтобы добавить визитку").
                        setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).
                        setView(image);
        builder.create().show();
    }

    @Override
    public void showUsersForshareCutaway(UserData userData) {
        userListAdapter.setUserData(userData);
        tvCutawayDescription.setText(userData.getUserName());
        tvCutawayDescription.setText("Поделиться визиткой '"+userData.getUserName()+"' c пользователем:");
        usersLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void showItems(UserData userData) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                        intent .putExtra(ContactsContract.Intents.Insert.PHONE, userData.userPhone);
                        intent .putExtra(ContactsContract.Intents.Insert.NAME, userData.userName);
                        startActivity(intent);
                    } break;

                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void shareCutaway(User user, UserData userData) {
        userData.setNeedToConfirm(true);
        userData.setId(user.getId());
        System.out.println("userData is "+new Gson().toJson(userData));
        dRefCardData.child(dRefCardData.push().getKey()).setValue(userData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                usersLayout.setVisibility(View.GONE);
            }
        });

    }
}