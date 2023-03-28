package com.sai.buddyup;
//Fragment to display User Profile.
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private Button signout;
    private TextView nameview,mailview,favview;
    private FirebaseUser user;
    private ProgressBar progress;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    nameview.setText(userProfile.name);
                    mailview.setText(userProfile.email);
                    favview.setText(userProfile.fav);
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }
        });
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        // Inflate the layout for this fragment
        signout = view.findViewById(R.id.signout);
        progress = view.findViewById(R.id.progress);
        nameview = view.findViewById(R.id.nameview);
        mailview = view.findViewById(R.id.mailview);
        favview = view.findViewById(R.id.favview);
        signout.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        return view;
    }
}