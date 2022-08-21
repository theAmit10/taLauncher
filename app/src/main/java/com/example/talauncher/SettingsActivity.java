package com.example.talauncher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    ImageView mHomeScreenImage;
    EditText mNumRow, mNumColumn;
    int REQUEST_CODE_IMAGE = 1;
    String PREFS_NAME = "NovaPrefs";
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button mHomeScreenButton = findViewById(R.id.homeScreenButton);
        Button mGridSizeButton = findViewById(R.id.gridSizeButton);

        mHomeScreenImage = findViewById(R.id.homeScreenImage);
        mNumColumn = findViewById(R.id.numColumn);
        mNumRow = findViewById(R.id.numRow);


        // setting the custum grid size
        mGridSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        // opening galley to add image on the screen
        mHomeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);

            }
        });

        getData();
    }

    private void getData(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String ImageUriString = sharedPreferences.getString("uri",null);
        int numRow = sharedPreferences.getInt("numRow", 7);
        int numColumn = sharedPreferences.getInt("numColumn", 5);

        if(ImageUriString != null){
            uri = Uri.parse(ImageUriString);
            mHomeScreenImage.setImageURI(uri);
        }
        mNumRow.setText(String.valueOf(numRow));
        mNumColumn.setText(String.valueOf(numColumn));
    }

    private void saveData(){
        SharedPreferences.Editor sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE).edit();
        if(uri != null)
            sharedPreferences.putString("uri", uri.toString());

        sharedPreferences.putInt("numRow", Integer.valueOf(mNumRow.getText().toString()));
        sharedPreferences.putInt("numColumn", Integer.valueOf(mNumColumn.getText().toString()));
        sharedPreferences.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            mHomeScreenImage.setImageURI(uri);
            saveData();
        }
    }

}