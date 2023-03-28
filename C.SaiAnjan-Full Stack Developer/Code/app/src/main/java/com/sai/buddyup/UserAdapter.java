package com.sai.buddyup;
//Adapter for displaying Users in Recycler View.
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    ArrayList<User> userlist;
    private FirebaseAuth mAuth;

    public UserAdapter(Context context, ArrayList<User> userlist) {
        this.context = context;
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User userClass = userlist.get(position);
        holder.usernam.setText(userClass.name);
        holder.userfav.setText(userClass.fav);
        mAuth = FirebaseAuth.getInstance();
        holder.usersend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userid",userClass.uid);
                intent.putExtra("hello","Hello\uD83D\uDC4B");
                intent.putExtra("name",userClass.name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView usernam,userfav;
        private Button usersend;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernam=itemView.findViewById(R.id.userlist);
            userfav=itemView.findViewById(R.id.fav);
            usersend=itemView.findViewById(R.id.back);
        }
    }
}