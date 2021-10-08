package com.example.login_ex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Modify_User_Info extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText userName, userNickname, userDateOfBirth, userPhoneNumber;
    Button updateInfoButton;
    FirebaseUser CurrentUser = FirebaseAuth.getInstance().getCurrentUser();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        userName = (EditText) findViewById(R.id.userName);
        userNickname = (EditText) findViewById(R.id.userNickname);
        userDateOfBirth = (EditText) findViewById(R.id.userDateOfBirth);
        userPhoneNumber = (EditText) findViewById(R.id.userPhoneNumber);
        updateInfoButton = (Button) findViewById(R.id.updateInfoButton);
        mAuth = FirebaseAuth.getInstance();

        getUserInfo();
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });


    }

    private void getUserInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(CurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String nickname = dataSnapshot.child("nickname").getValue(String.class);
                String birth = dataSnapshot.child("birth").getValue(String.class);
                String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);

                userName.setText(name);
                userNickname.setText(nickname);
                userDateOfBirth.setText(birth);
                userPhoneNumber.setText(phoneNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }

    private void updateInfo() {

        String name = userName.getText().toString();
        String nickname = userNickname.getText().toString();
        String birth = userDateOfBirth.getText().toString();
        String phoneNumber = userPhoneNumber.getText().toString();

        if (name.length() > 1 && nickname.length() > 2 && birth.length() > 7 && phoneNumber.length() > 10) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();

            Member_Info memberInfo = new Member_Info(name, nickname, birth, phoneNumber);

            if (user != null) {
                userDB.child("users").child(user.getUid()).setValue(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                userProfile(name);
                                ToastMessage("정보 수정 완료");
                                Intent intent = new Intent(Modify_User_Info.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                ToastMessage("정보 수정 실패");
                            }
                        });
            }
        }
    }


    // 프로필 이름 설정
    private void userProfile(String name){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.trim()).build();

            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("Testing", "User Profile Updated");
                    }
                }
            });
        }
    }

    private void ToastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
