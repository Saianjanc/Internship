package com.sai.buddyup;
//Recycler View for displaying messages.
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatList extends Fragment {

    private RecyclerView recyclerView;
    private listAdapter userAdapter;

    FirebaseUser muser;
    DatabaseReference reference,mref;

    private ArrayList<User> userlist;
    private String senderRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_list, container, false);
        recyclerView = view.findViewById(R.id.listmsgs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ProgressBar progressBar;
        ImageView image;
        progressBar = view.findViewById(R.id.progress);
        image = view.findViewById(R.id.image);

        muser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        userlist = new ArrayList<User>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    image.setVisibility(View.VISIBLE);
                    User userClass = dataSnapshot.getValue(User.class);
                    senderRoom = userClass.uid+muser.getUid();
                    mref = FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom);
                    mref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot msnapshot) {
                            for (DataSnapshot mdataSnapshot:msnapshot.getChildren()) {
                                if(!userlist.contains(userClass)){
                                    userlist.add(userClass);
                                    image.setVisibility(View.GONE);}}
                            userAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);}
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }}); } }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});
        userAdapter = new listAdapter(getContext(),userlist);
        recyclerView.setAdapter(userAdapter);
        return view;
    }
}
