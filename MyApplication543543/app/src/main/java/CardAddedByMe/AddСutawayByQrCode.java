package CardAddedByMe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AddСutawayByQrCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {
        private ZXingScannerView mScannerView;
        private DatabaseReference database;
        CardData cardData;
        @Override
        public void onCreate(Bundle state) {
            super.onCreate(state);
            // Programmatically initialize the scanner view
            mScannerView = new ZXingScannerView(this);
            // Set the scanner view as the content view
            setContentView(mScannerView);
            database = FirebaseDatabase.getInstance().getReference().child("Card Data");
        }

        @Override
        public void onResume() {
            super.onResume();
            // Register ourselves as a handler for scan results.
            mScannerView.setResultHandler(this);
            // Start camera on resume
            mScannerView.startCamera();
        }

        @Override
        public void onPause() {
            super.onPause();
            // Stop camera on pause
            mScannerView.stopCamera();
        }

        @Override
        public void handleResult(Result rawResult) {
            // Do something with the result here
            // Prints scan results
            if(cardData!=null) return;
            Log.d("result", rawResult.getText());
            try {
                cardData =new Gson().fromJson(rawResult.getText(),CardData.class);
                if(cardData!=null){
                    addCutaway(cardData);
                }else {
                    mScannerView.setResultHandler(this);
                    mScannerView.startCamera();
                    Toast.makeText(AddСutawayByQrCode.this,"Не верный формат QR-кода",Toast.LENGTH_SHORT).show();
                }

            }catch (JsonSyntaxException e){
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
                Toast.makeText(AddСutawayByQrCode.this,"Не верный формат QR-кода",Toast.LENGTH_SHORT).show();
            }


            // Prints the scan format (qrcode, pdf417 etc.)
            Log.d("result", rawResult.getBarcodeFormat().toString());
            //If you would like to resume scanning, call this method below:
            //mScannerView.resumeCameraPreview(this);
        }
        private  void addCutaway(CardData cardData){
            cardData.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            database.child(database.push().getKey()).setValue(cardData, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(AddСutawayByQrCode.this,"Success!",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }