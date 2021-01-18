package SettingActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.myapplication543543.Login;
import com.example.myapplication543543.R;
import com.google.firebase.auth.FirebaseAuth;

import CardAddedByMe.CardActivity;
import CardOfMine.MainActivity;

public class Settings extends AppCompatActivity {

    DrawerLayout drawerLayout;
    SwitchCompat btnChangeTheme;
    boolean themeLight = true;
    ImageView about, night;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        drawerLayout = findViewById(R.id.drawer_layout);
        about = findViewById(R.id.imageView4);
        night = findViewById(R.id.imageView5);
        btnChangeTheme = findViewById(R.id.switch1);
        btnChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                night.setImageResource(R.drawable.night);
                if (themeLight) {
                    themeLight = false;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                } else {
                    themeLight = true;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });


        ImageView arrow = (ImageView) findViewById(R.id.arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), CardActivity.class);
                startActivity(myIntent);
            }
        });

        Button next = (Button) findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(view.getContext(), Login.class);
                startActivity(myIntent);
            }
        });

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
    }

}