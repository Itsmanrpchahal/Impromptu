package com.mandywebdesign.impromptu.BusinessRegisterLogin.BusinessAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mandywebdesign.impromptu.R;
import com.mandywebdesign.impromptu.Retrofit.RetroGetMessages;
import com.mandywebdesign.impromptu.messages.ChatBox;

import java.util.ArrayList;

public class ChatBox_Adapter extends RecyclerView.Adapter<ChatBox_Adapter.ViewHolder> {


    Context context;
    String userID;
    ArrayList<RetroGetMessages.Datum> arrayList ;

    public ChatBox_Adapter(Context context, String userID, ArrayList<RetroGetMessages.Datum> arrayList) {
        this.context = context;
        this.userID = userID;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ChatBox_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_chat,  viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RetroGetMessages.Datum datum = arrayList.get(i);
        String id = datum.getUserId().toString();
        Log.e("USERID", id );
        String mesg = arrayList.get(i).getMessage();


        if (id.equals(userID)) {
            viewHolder.right.setText(arrayList.get(i).getMessage());
            Glide.with(context).load(arrayList.get(i).getUserAvatar()).into(viewHolder.rightUserImage);
            viewHolder.rightUserName.setText(arrayList.get(i).getUsername() );
            viewHolder.leftUsername.setVisibility(View.GONE);
            viewHolder.leftUserImage.setVisibility(View.GONE);
            viewHolder.left.setVisibility(View.GONE);
            viewHolder.leftLayout.setVisibility(View.GONE);
        } else {
            viewHolder.left.setText(arrayList.get(i).getMessage());
            Glide.with(context).load(arrayList.get(i).getUserAvatar()).into(viewHolder.leftUserImage);
            viewHolder.leftUsername.setText(arrayList.get(i).getUsername());
            viewHolder.rightUserName.setVisibility(View.GONE);
            viewHolder.rightUserImage.setVisibility(View.GONE);
            viewHolder.right.setVisibility(View.GONE);
            viewHolder.rightLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView message_text,messageUser;
        TextView leftUsername, rightUserName,left,right;
        LinearLayout  leftLayout;
        ConstraintLayout rightLayout;
        ImageView leftUserImage, rightUserImage;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            left = itemView.findViewById(R.id.left_text);
            right = itemView.findViewById(R.id.right_text);
            leftUsername = itemView.findViewById(R.id.lft_username);
            rightUserName = itemView.findViewById(R.id.right_username);
            leftUserImage = itemView.findViewById(R.id.left_iamge);
            rightUserImage = itemView.findViewById(R.id.right_iamge);
            rightLayout = itemView.findViewById(R.id.right);
            leftLayout = itemView.findViewById(R.id.left);

        }
    }
}