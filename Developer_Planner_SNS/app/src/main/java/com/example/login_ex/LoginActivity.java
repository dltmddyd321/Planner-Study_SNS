package com.example.login_ex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "LoginActivity";
    private long backBtnTime = 0;

    private FirebaseAuth mAuth;
    private int loginCnt;
    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView tv1;

    // ???????????? ?????? ?????????
    private CallbackManager callbackManager;

    private LoginButton btn_facebook;
    private SignInButton btn_google; // ?????? ????????? ??????
    private FirebaseAuth auth; // ????????? ????????? ?????? ??????
    private GoogleApiClient googleApiClient; // ?????? API ??????????????? ??????
    private static final int REQ_SIGN_GOOGLE = 100; // ?????? ????????? ?????? ??????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // ???????????? ?????? ??????
        callbackManager = CallbackManager.Factory.create();

        findViewById(R.id.singUpButton).setOnClickListener(onClickListener);
        findViewById(R.id.loginButton).setOnClickListener(onClickListener);

        // ?????? ????????? ??????
        btn_facebook = findViewById(R.id.btn_facebook);
        btn_facebook.setReadPermissions("email", "public_profile");
        btn_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }
            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance(); // ?????????????????? ?????? ?????? ?????????

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() { // ?????? ????????? ????????? ???????????? ??? ????????? ??????
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        } else {
            backBtnTime = curTime;
            ToastMessage("?????? ??? ????????? ???????????????.");
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // ?????? ????????? ????????? ???????????? ??? ???????????? ????????? ?????? ???
        super.onActivityResult(requestCode, resultCode, data);
        // ???????????? ?????? ??????
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SIGN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) { // ??????????????? ???????????????..
                GoogleSignInAccount account = result.getSignInAccount(); // account ?????? ???????????? ??????????????? ????????? ?????? ??????(?????????, ???????????????, ??????????????? ??????)
                resultLogin(account);// ????????? ????????? ?????? ??????????????? ?????????
            }
        }
    }
    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) { // ???????????? ???????????????..
                            Toast.makeText(LoginActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                            intent.putExtra("nickName", account.getDisplayName());
                            intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl())); // String.valueOf() ?????? ???????????? String ????????? ??????
                            startActivity(intent);
                        } else { // ???????????? ???????????????..
                            Toast.makeText(LoginActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.singUpButton:
                    goSignUp();
                    break;

                case R.id.loginButton:
                    login();
                    break;
            }
        }
    };

    private void goSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


    private void login(){
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
        // ????????? ?????? ????????????
        if (email.length() > 0 && password.length() > 0){
            // Firebase Auth ????????? ???????????? ????????? ???????????? ??????????????? ??????????????? ??????
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                {
                                    if ( user != null){
                                        if(user.getDisplayName() == null){
                                            Intent intent = new Intent(LoginActivity.this, Member_InitInfo.class);
                                            startActivity(intent);
                                        }else {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                        SharedPreferences sharedPreferences = getSharedPreferences("pFile",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("password",password);
                                        editor.apply();
                                    }
                                };
                            }else {
                                ToastMessage("????????? ?????? ??????????????? ???????????? ????????????!!");
                            }
                        }
                    });
        } else {
            ToastMessage("????????? ?????? ??????????????? ????????? ?????????!!");
        }
    }



    private void ToastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startMainActivity();
                            Toast.makeText(LoginActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                        } else {
                            // ????????? ??????
                            Toast.makeText(LoginActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

