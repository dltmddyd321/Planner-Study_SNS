package com.example.login_ex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class AddPost extends AppCompatActivity {

    ImageView imgView;
    boolean usingCamera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        imgView = findViewById(R.id.imageView);

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_photo, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(AddPost.this);
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button cameraBtn = dialogView.findViewById(R.id.photoBtn);
                Button albumBtn = dialogView.findViewById(R.id.galleryBtn);

                cameraBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        usingCamera = true;
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 0);
                        alertDialog.dismiss();
                    }
                });

                albumBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        usingCamera = false;
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 0);
                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(usingCamera) {
            if (requestCode == 0 && resultCode == RESULT_OK) {

                Bundle extras = data.getExtras();

                Bitmap imageBitmap = (Bitmap) extras.get("data");

                imgView.setImageBitmap(imageBitmap);
            }
        }else {
            if(requestCode == 0) {
                if(resultCode == RESULT_OK) {
                    try {
                        InputStream in = getContentResolver().openInputStream(data.getData());

                        Bitmap img = BitmapFactory.decodeStream(in);
                        in.close();

                        imgView.setImageBitmap(img);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
