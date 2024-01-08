package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.core.Tag;

public class login extends AppCompatActivity {
    TextView logsignup;
    Button button;
    EditText email, password;
    FirebaseAuth auth;

    String emailPattern = ("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    android.app.ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logbutton);
        email = findViewById(R.id.editTextlogEmail);
        password = findViewById(R.id.editTextlogPassword);
        logsignup= findViewById(R.id.logsignup);

        logsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(login.this,Registration.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();

               if((TextUtils.isEmpty(Email))){
                   Toast.makeText(login.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();

               }else if (TextUtils.isEmpty(pass)){
                   progressDialog.dismiss();
                   Toast.makeText(login.this, "Enter the Password", Toast.LENGTH_SHORT).show();
               }else if (!Email.matches(emailPattern)){
                   progressDialog.dismiss();
                   email.setError("Give Proper Email Address");
               }else if (password.length()<6){
                   progressDialog.dismiss();
                   password.setError("More than six characters");
                   Toast.makeText(login.this, "Password needs to be longer then six characters", Toast.LENGTH_SHORT).show();
               }else{
                   auth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               progressDialog.show();
                               try{
                                   Intent intent = new Intent(login.this ,MainActivity.class);
                                   startActivity(intent);
                                   finish();
                               }catch (Exception e){
                                   Toast.makeText(login.this , e.getMessage(),Toast.LENGTH_SHORT).show();

                               }
                           }else {
                               Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }


            }
        });

    }
}