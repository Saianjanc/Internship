package com.sai.buddyup;
//Activity for Selecting User Interest.
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FavActivity extends AppCompatActivity {

    private Button fav1,fav2,fav3,fav4,fav5,fav6;
    private String fav;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        fav1 = findViewById(R.id.cricket);
        fav2 = findViewById(R.id.basketball);
        fav3 = findViewById(R.id.football);
        fav4 = findViewById(R.id.formula1);
        fav5 = findViewById(R.id.kabaddi);
        fav6 = findViewById(R.id.tennis);

        fav1.setOnClickListener(new Click());
        fav2.setOnClickListener(new Click());
        fav3.setOnClickListener(new Click());
        fav4.setOnClickListener(new Click());
        fav5.setOnClickListener(new Click());
        fav6.setOnClickListener(new Click());
    }

    public class Click implements View.OnClickListener{
        public void onClick(View v){
            switch (v.getId()){
                case R.id.cricket:
                    fav="Cricket";
                    break;
                case R.id.basketball:
                    fav="Basketball";
                    break;
                case R.id.football:
                    fav="Football";
                    break;
                case R.id.formula1:
                    fav="Formula1";
                    break;
                case R.id.kabaddi:
                    fav="Kabaddi";
                    break;
                case R.id.tennis:
                    fav="Tennis";
                    break;
                default:
                    break;
            }
            setfav();
        }
    }

    public void setfav() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    User userprofile = new User(userProfile.name, userProfile.email, fav, mAuth.getCurrentUser().getUid());
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userprofile).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(FavActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
              }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    @Override
    public void onBackPressed(){ }
}