package com.sai.buddyup;
//Adapter for displaying Messages in the Recycler View.
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ChatActivity context;
    ArrayList<Message> msglist;

    int ITEM_RECVIVE = 1;
    int ITEM_SENT = 2;

    public MyAdapter(ChatActivity context, ArrayList<Message> msglist) {
        this.context = context;
        this.msglist = msglist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receive, parent, false);
            return new ReceiveViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent, parent, false);
            return new SentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Message currentmsg = msglist.get(position);
        if (holder.getClass() == SentViewHolder.class) {
           ((SentViewHolder) holder).bindData(currentmsg);

        }else {
            ((ReceiveViewHolder) holder).bindData(currentmsg);
        }
    }

    @Override
    public int getItemViewType(int position) {

        Message currentmsg = msglist.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(currentmsg.suid))
            return ITEM_SENT;
        else
            return ITEM_RECVIVE;
    }

    @Override
    public int getItemCount() {
        return msglist.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {
        private TextView msgsent;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            msgsent = itemView.findViewById(R.id.msgsent);
        }

        public void bindData(Message currentmsg) {
            msgsent.setText(currentmsg.msg);
        }
    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder {
        private TextView msgreceived;
        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            msgreceived = itemView.findViewById(R.id.msgreceived);
        }

        public void bindData(Message currentmsg) {
            msgreceived.setText(currentmsg.msg);
        }
    }
}