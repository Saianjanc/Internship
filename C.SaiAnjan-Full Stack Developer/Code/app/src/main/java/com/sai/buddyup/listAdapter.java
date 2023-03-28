package com.sai.buddyup;
//Adapter for Chat list Recycler View.
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {

    Context context;
    ArrayList<User> userlist;
    private FirebaseAuth mAuth;

    public listAdapter(Context context, ArrayList<User> userlist) {
        this.context = context;
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public listAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listAdapter.ViewHolder holder, int position) {
        User userClass = userlist.get(position);
        holder.usernam.setText(userClass.name);
        mAuth = FirebaseAuth.getInstance();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userid",userClass.uid);
                intent.putExtra("hello","");
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

        private TextView usernam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernam=itemView.findViewById(R.id.nuser);
        }
    }
}