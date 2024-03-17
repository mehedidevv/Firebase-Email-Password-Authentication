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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText nameET,emailET,passwordET,confpasswordET;
    Button signbtn;
    ProgressBar progressBar;
    String name,email,password;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //connection
        connection();
        signbtn.setOnClickListener(new View.OnClickListener() {
            /** @noinspection SingleStatementInBlock*/
            @Override
            public void onClick(View v) {
                 name=nameET.getText().toString();
                 email=emailET.getText().toString();
                 password=passwordET.getText().toString();
                String confpassword=confpasswordET.getText().toString();

                if (name.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Entar Name", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (confpassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confpassword)) {
                    confpasswordET.setError("Confirm Password Does not Match");
                } else {

                    signupuser();
                }

            }
        });
    }

    private void signupuser() {
        //Progressbar
        progressBar.setVisibility(View.VISIBLE);
        signbtn.setVisibility(View.GONE);

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    //Email Varification Link Sent
                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(RegisterActivity.this, "Varification Link Send Succesfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(RegisterActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Account Create
                    Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(RegisterActivity.this, "Error :" +e.getMessage(), Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
                signbtn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void connection() {
        nameET=findViewById(R.id.nameET);
        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        confpasswordET=findViewById(R.id.confpasswordET);
        signbtn=findViewById(R.id.signupbtn);

        progressBar=findViewById(R.id.loadingbar);

        //FirebaseAuth Get Instances korte hobe
        firebaseAuth=FirebaseAuth.getInstance();
    }
}