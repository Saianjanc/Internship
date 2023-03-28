package com.sai.buddyup;
//Activity for logging in and registering Users.

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {

    private static final String err = "ERROR_EMAIL_ALREADY_IN_USE";
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView disp;
    private EditText name;
    private String fav="none";

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        disp = findViewById(R.id.disp);
        name = findViewById(R.id.name);
        TextView forgot = findViewById(R.id.forgot);
        EditText forgotmail = findViewById(R.id.forgotmail);
        EditText emailText = findViewById(R.id.username);
        EditText passwordText = findViewById(R.id.password);
        EditText mail = findViewById(R.id.email);
        EditText npass = findViewById(R.id.npassword);
        EditText cpass = findViewById(R.id.cpassword);
        Button loginButton = findViewById(R.id.login);
        Button signupButton = findViewById(R.id.register);
        Button regButton = findViewById(R.id.signup);
        Button logButton = findViewById(R.id.already);
        Button reset = findViewById(R.id.reset);
        Button back = findViewById(R.id.back);
        progressBar = findViewById(R.id.progressBar);
        RelativeLayout loginpage = findViewById(R.id.loginPage);
        RelativeLayout signuppage = findViewById(R.id.signupPage);
        RelativeLayout forgotpage = findViewById(R.id.forgotPage);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailText.getText().toString().isEmpty()) {
                    emailText.setError("Email is required!");
                    emailText.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()) {
                    emailText.setError("Please provide vaild email!");
                    emailText.requestFocus();
                    return;
                }
                if(passwordText.getText().toString().isEmpty()){
                    passwordText.setError("Password is required!");
                    passwordText.requestFocus();
                    return;
                    }
                progressBar.setVisibility(View.VISIBLE);
                login(emailText.getText().toString(),
                        passwordText.getText().toString());
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disp.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                loginpage.setVisibility(View.INVISIBLE);
                forgotpage.setVisibility(View.VISIBLE);
                signuppage.setVisibility(View.INVISIBLE);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forgotmail.getText().toString().isEmpty()) {
                    forgotmail.setError("Email is required!");
                    forgotmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(forgotmail.getText().toString()).matches()) {
                    forgotmail.setError("Please provide vaild email!");
                    forgotmail.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(forgotmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            disp.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            disp.setText("Password Rest Link has been sent to your Mail!!");
                        }
                        else{
                            disp.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            disp.setText("Something Went Worng!");
                        }
                    }
                });
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disp.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                loginpage.setVisibility(View.VISIBLE);
                forgotpage.setVisibility(View.GONE);
                signuppage.setVisibility(View.INVISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disp.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                loginpage.setVisibility(View.VISIBLE);
                forgotpage.setVisibility(View.GONE);
                signuppage.setVisibility(View.INVISIBLE);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disp.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                loginpage.setVisibility(View.INVISIBLE);
                forgotpage.setVisibility(View.GONE);
                signuppage.setVisibility(View.VISIBLE);
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    name.setError("Name is required!");
                    name.requestFocus();
                    return;
                }
                if (mail.getText().toString().isEmpty()) {
                    mail.setError("Email is required!");
                    mail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches()) {
                    mail.setError("Please provide vaild email!");
                    mail.requestFocus();
                    return;
                }
                if (npass.getText().toString().isEmpty()) {
                    npass.setError("Password is required!");
                    npass.requestFocus();
                    return;
                }
                if (npass.getText().toString().length() < 6) {
                    npass.setError("Password length should be Min 6 Characters!");
                    npass.requestFocus();
                    return;
                }
                if (!npass.getText().toString().equals(cpass.getText().toString())) {
                    cpass.setError("Password does not match!");
                    cpass.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                register(mail.getText().toString(),
                        npass.getText().toString());
            }
        });
    }

    //Login activity check username and password in the database.
    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            disp.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            disp.setText("Authentication failed!");
                        }
                    }
                });
    }

    //Register activity push and add new users into the database.
    private void register(String mail, String npass) {
            mAuth.createUserWithEmailAndPassword(mail, npass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(name.getText().toString(), mail, fav, mAuth.getCurrentUser().getUid());
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressBar.setVisibility(View.GONE);
                                            Intent intent = new Intent(LoginActivity.this, FavActivity.class);
                                            startActivity(intent);
                                        } else {
                                            disp.setVisibility(View.VISIBLE);
                                            disp.setText("Failed to register! Try Again!");
                                        }
                                    }
                                });
                            }
                            if(!task.isSuccessful() && task.getException() instanceof FirebaseAuthUserCollisionException){
                                FirebaseAuthUserCollisionException exception = (FirebaseAuthUserCollisionException)task.getException();
                                if(exception.getErrorCode() == err){
                                    disp.setVisibility(View.VISIBLE);
                                    disp.setText("Email Already exists!");
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }
                    });
    }

    @Override
    public void onBackPressed(){

    }

}