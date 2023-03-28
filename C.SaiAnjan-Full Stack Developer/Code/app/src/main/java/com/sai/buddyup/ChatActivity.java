package com.sai.buddyup;
//This Activity is for sending and receiving messeage.
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText msgbox;
    private ImageButton send;
    private MyAdapter myAdapter;
    ArrayList<Message> msglist;
    private String senderRoom;
    private String receiverRoom;
    private String senderUid;
    private String receiverUid;
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        recyclerView = findViewById(R.id.chatview);
        msgbox = findViewById(R.id.msgbox);
        send = findViewById(R.id.send);

        //Getting User UIDs.
        senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        receiverUid = getIntent().getStringExtra("userid");

        msgbox.setText(getIntent().getStringExtra("hello"));

        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));

        //Acessing Firebase Database.
        reference = FirebaseDatabase.getInstance().getReference("Chats");

        //Creating and storing Messages in private rooms.
        senderRoom = receiverUid+senderUid;
        receiverRoom = senderUid+receiverUid;

            send.setOnClickListener(v -> {
                String msg = msgbox.getText().toString();
                if(!msg.isEmpty()) {
                Message message = new Message(msg, senderUid, receiverUid);
                reference.child(senderRoom).child("Msgs").push()
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(receiverRoom).child("Msgs").push().setValue(message);
                        msgbox.setText("");
                    }
                });}
            });

        msglist = new ArrayList<Message>();
        myAdapter = new MyAdapter(this,msglist);

        Context context = getBaseContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(myAdapter);


        //Retriving Messages(data) from the database.
        reference.child(senderRoom).child("Msgs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                msglist.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    msglist.add(message);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}