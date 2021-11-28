package com.example.firestoretest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<Input> mPostList;
    private Context context;

    public PostAdapter(ArrayList<Input> mPostList, Context context)
    {
        this.mPostList = mPostList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        PostViewHolder holder =new PostViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(mPostList.get(position).getImg())
                .into(holder.img);

        holder.text.setText(mPostList.get(position).message);
    }

    @Override
    public int getItemCount() {
        return (mPostList != null ? mPostList.size() : 0);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView img;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.result);
        }
    }
}
