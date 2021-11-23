package com.example.login_ex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_ex.R;
import com.example.login_ex.model.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<Post> mPostList = null;

    public PostAdapter(ArrayList<Post> dataList)
    {
        mPostList = dataList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(mPostList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    void addItem(Post post) {
        mPostList.add(post);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView profile;
        private TextView userName;
        private ImageView imageView;
        private TextView mainText;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.profileName);
            imageView = itemView.findViewById(R.id.mainImage);
            mainText = itemView.findViewById(R.id.mainText);

        }

        void onBind(Post post){
            profile.setImageResource(post.getProfileImg());
            userName.setText(post.getUserName());
            imageView.setImageResource(post.getImg());
            mainText.setText(post.getMessage());
        }
    }


}
