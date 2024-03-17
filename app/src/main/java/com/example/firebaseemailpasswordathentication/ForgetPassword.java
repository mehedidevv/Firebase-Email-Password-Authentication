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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    EditText editText;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //Connection
        Connection();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=editText.getText().toString();
                if (mail.isEmpty()){
                    Toast.makeText(ForgetPassword.this, "Enter Mail Address", Toast.LENGTH_SHORT).show();
                }else {
                    
                    progressBar.setVisibility(View.VISIBLE);
                    button.setVisibility(View.INVISIBLE);

                    //Add FirebaseAuth
                    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                   firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {

                           progressBar.setVisibility(View.INVISIBLE);
                           button.setVisibility(View.VISIBLE);
                           Toast.makeText(ForgetPassword.this, "Reset Succesfully. Please Cheack Mail:", Toast.LENGTH_SHORT).show();
                           //Intent to Login Activity
                           Intent intent=new Intent(ForgetPassword.this,LoginActivity.class);
                           startActivity(intent);

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           progressBar.setVisibility(View.VISIBLE);
                           button.setVisibility(View.INVISIBLE);
                           Toast.makeText(ForgetPassword.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
                }
            }
        });
    }

    private void Connection() {
        editText=findViewById(R.id.MailEt);
        button=findViewById(R.id.resetbtn);
        progressBar=findViewById(R.id.loadingbar);
    }
}