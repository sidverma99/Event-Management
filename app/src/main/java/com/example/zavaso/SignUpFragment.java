package com.example.zavaso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {
    public SignUpFragment(){

    }
    private EditText username,password,confirmPassword;
    private Button signUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_sign_up, container, false);
        username=(EditText)v.findViewById(R.id.signup_email);
        password=(EditText)v.findViewById(R.id.signup_password);
        confirmPassword=(EditText)v.findViewById(R.id.signup_confirm_password);
        signUp=(Button)v.findViewById(R.id.sign_up_btn);
        auth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar)v.findViewById(R.id.progressBar);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail=username.getText().toString().trim();
                String inputPassword=password.getText().toString().trim();
                String inputConfirmPassword=confirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(inputEmail)){
                    Toast.makeText(getContext(),"Enter Email Address",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputPassword)){
                    Toast.makeText(getContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputConfirmPassword)){
                    Toast.makeText(getContext(),"Confirm Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                int l1=password.length(),l2=confirmPassword.length(),u_case=0,digit=0;
                for (int i=0;i<l1;i++){
                    char x,y;
                    x=inputPassword.charAt(i);
                    y=inputConfirmPassword.charAt(i);
                    if(x!=y){
                        Toast.makeText(getContext(),"Password do not match",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                for (int i=0;i<l1;i++){
                    char x;
                    x=inputPassword.charAt(i);
                    if(Character.isUpperCase(x)) u_case++;
                    if (Character.isDigit(x)) digit++;
                }
                if(l1<8){
                    Toast.makeText(getContext(),"Password length is short",Toast.LENGTH_LONG).show();
                    return;
                }
                if(u_case==0 && digit==0){
                    Toast.makeText(getContext(),"Password neither have numeric nor uppercase",Toast.LENGTH_LONG).show();
                    return;
                }
                if (u_case==0){
                    Toast.makeText(getContext(),"Password should have at least one uppercase character",Toast.LENGTH_LONG).show();
                    return;
                }
                if(digit==0){
                    Toast.makeText(getContext(),"Password should have at least one numeric value",Toast.LENGTH_LONG).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(inputEmail,inputConfirmPassword).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getContext(),"Authentication failed"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent=new Intent(getActivity(),SignedinActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                });
            }
        });
        return v;
    }
}
