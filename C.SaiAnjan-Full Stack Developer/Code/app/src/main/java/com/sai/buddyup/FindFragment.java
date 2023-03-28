package com.sai.buddyup;
//Fragment for Find new User from the database.
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class FindFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String fav="All";
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private UserAdapter userAdapter;
    private ProgressBar progressBar;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
    private MaterialButtonToggleGroup toggleGroup;
    RecyclerView recyclerView;
    ArrayList<User> userlist;

    public FindFragment() {
        // Required empty public constructor
    }


    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    public void getfav() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        userlist = new ArrayList<User>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User userClass = dataSnapshot.getValue(User.class);
                    if (!userClass.email.equals(user.getEmail())) {
                        if (fav.equals("All"))
                            userlist.add(userClass);
                        if (fav.equals("Cricket"))
                            if (userClass.fav.equals("Cricket"))
                                userlist.add(userClass);
                        if (fav.equals("Basketball"))
                            if (userClass.fav.equals("Basketball"))
                                userlist.add(userClass);
                        if (fav.equals("Football"))
                            if (userClass.fav.equals("Football"))
                                userlist.add(userClass);
                        if (fav.equals("Formula1"))
                            if (userClass.fav.equals("Formula1"))
                                userlist.add(userClass);
                        if (fav.equals("Kabaddi"))
                            if (userClass.fav.equals("Kabaddi"))
                                userlist.add(userClass);
                        if (fav.equals("Tennis"))
                            if (userClass.fav.equals("Tennis"))
                                userlist.add(userClass);
                    }
                }
                userAdapter = new UserAdapter(getContext(), userlist);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);

        Context context = view.getContext();
        progressBar = view.findViewById(R.id.fprogress);
        recyclerView = view.findViewById(R.id.recycleView);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn6 = view.findViewById(R.id.btn6);
        btn7 = view.findViewById(R.id.btn7);

        toggleGroup = view.findViewById(R.id.group);
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.btn1:
                            fav = "All";
                            break;
                        case R.id.btn2:
                            fav = "Cricket";
                            break;
                        case R.id.btn3:
                            fav = "Basketball";
                            break;
                        case R.id.btn4:
                            fav = "Football";
                            break;
                        case R.id.btn5:
                            fav = "Formula1";
                            break;
                        case R.id.btn6:
                            fav = "Kabaddi";
                            break;
                        case R.id.btn7:
                            fav = "Tennis";
                            break;
                    }
                    getfav();
                }
            }
        });
        getfav();
        return view;
    }
}