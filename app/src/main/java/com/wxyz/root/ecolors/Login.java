package com.wxyz.root.ecolors;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by root on 30/11/16.
 */

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    EditText correo,pass;
    Button registrar,ingresar;
    FirebaseDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();//No se por q
        mAuth = FirebaseAuth.getInstance();

        correo = (EditText) findViewById(R.id.correoe);
        pass = (EditText) findViewById(R.id.passe);

        registrar = (Button) findViewById(R.id.registrar);
        ingresar = (Button) findViewById(R.id.ingresar);



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent i = new Intent(Login.this,MainActivity.class);
                    startActivity(i);
                } else {
                    System.out.println("porfavor registrese");
                      }
            }
        };



        //btn registrar pasa a otra actividad
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this,SignIn.class);
                startActivity(i);


            }
        });

        //btn ingresar
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signInUser(correo.getText().toString(),pass.getText().toString());

            }
        });




    }


    //METHODS
    void signUpUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "Se creo el usuario: " + task.isSuccessful(),Toast.LENGTH_SHORT).show();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "No se si aqui falla, creo q si",
                                    Toast.LENGTH_SHORT).show();
                        } else if(mAuth.getCurrentUser() != null){//si no fallo y se registra
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }
                });
    }

    void signInUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {//Fallo
                            Toast.makeText(getApplicationContext(), "Aqui fallo",
                                    Toast.LENGTH_SHORT).show();
                        }else if(mAuth.getCurrentUser() != null){//si no fallo y se loguea
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
