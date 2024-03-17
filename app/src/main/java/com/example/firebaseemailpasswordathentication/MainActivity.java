package com.example.firebaseemailpasswordathentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button varifybtn;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection();
        //Cheack Email Varification
        CheckVarification();
        //VarifyBtn ONClick
        varifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(MainActivity.this, "Varification Link Send Succesfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void CheckVarification() {

        if (!firebaseUser.isEmailVerified()){
            textView.setVisibility(View.VISIBLE);
            varifybtn.setVisibility(View.VISIBLE);
        }else {
            textView.setText("Varified");
            varifybtn.setVisibility(View.INVISIBLE);
        }
    }

    private void connection() {
        textView=findViewById(R.id.emailvariftET);
        varifybtn=findViewById(R.id.varifybtn);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    }

    public void logoutbtn(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckVarification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckVarification();
    }
}