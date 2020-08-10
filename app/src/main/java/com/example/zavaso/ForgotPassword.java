package com.example.zavaso;

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
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends Fragment {
    private EditText emailId;
    private Button sendLink;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public ForgotPassword() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.forgot_password, container, false);
        emailId=(EditText)v.findViewById(R.id.email);
        sendLink=(Button)v.findViewById(R.id.send_link);
        progressBar=(ProgressBar)v.findViewById(R.id.progressBar);
        auth=FirebaseAuth.getInstance();
        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail=emailId.getText().toString().trim();
                if (TextUtils.isEmpty(inputEmail)){
                    Toast.makeText(getActivity(),"Enter EmailId",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Password Reset LInk is send",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(),"Password Reset LInk is not send",Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
        return v;
    }
}
