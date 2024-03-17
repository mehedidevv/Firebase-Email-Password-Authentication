package com.example.firebaseemailpasswordathentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText logmailET,logpassword;
    Button logbtn,registerbtn;
    ProgressBar progressBar;
    String email,password;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Connection
        connection();

        //LogInButton OnclickListner
        logbtn.setOnClickListener(new View.OnClickListener() {
            /** @noinspection SingleStatementInBlock*/
            @Override
            public void onClick(View v) {
                email=logmailET.getText().toString();
                password=logpassword.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }else {
                    login();

                    //AutoLogIN
                    autologin();
                }
            }
        });
        //RegisterBtn ONClickListner
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    private void autologin() {
        if (firebaseAuth.getCurrentUser() != null){
            
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        logbtn.setVisibility(View.GONE);

        //Signing Check For Email And Password

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBar.setVisibility(View.GONE);
                logbtn.setVisibility(View.VISIBLE);

                Toast.makeText(LoginActivity.this, "Error:" +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void connection() {
        logmailET=findViewById(R.id.logmailEt);
        logpassword=findViewById(R.id.logpassword);
        logbtn=findViewById(R.id.logbtn);
        registerbtn=findViewById(R.id.registerbtn);
        progressBar=findViewById(R.id.loadingbar);

        firebaseAuth=FirebaseAuth.getInstance();
    }


    public void forgetPassword(View view) {
        Intent intent=new Intent(LoginActivity.this,ForgetPassword.class);
        startActivity(intent);
    }
}