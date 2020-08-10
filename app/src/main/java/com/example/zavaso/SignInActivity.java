package com.example.zavaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    private Button signinBtn,signupBtn;
    private TextView forgotPassword;
    private EditText emailId,password;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signinBtn=(Button)findViewById(R.id.signin_btn);
        signupBtn=(Button)findViewById(R.id.signup_btn);
        forgotPassword=(TextView)findViewById(R.id.forgot_password);
        emailId=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        auth=FirebaseAuth.getInstance();
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpFragment signUpFragment=new SignUpFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.signIn_activity,signUpFragment).addToBackStack("null").commit();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassword forgotPassword=new ForgotPassword();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.signIn_activity,forgotPassword).addToBackStack("null").commit();
            }
        });
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail=emailId.getText().toString().trim();
                String inputPassword=password.getText().toString().trim();
                if(TextUtils.isEmpty(inputEmail)){
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(inputPassword)){
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"authentication failed"+task.getException(),Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignInActivity.this,SignedinActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}
