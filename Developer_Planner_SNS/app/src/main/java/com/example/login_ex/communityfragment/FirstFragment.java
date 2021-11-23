package com.example.login_ex.communityfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_ex.AddPost;
import com.example.login_ex.R;
import com.example.login_ex.adapter.PostAdapter;
import com.example.login_ex.model.Post;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private PostAdapter adapter;
    private ArrayList<Post> postList;
    ImageButton addPostBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.first_fragment, container, false);

        addPostBtn = (ImageButton) v.findViewById(R.id.addPostBtn);
        RecyclerView recyclerView = v.findViewById(R.id.feedRecyclerView);

        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPost.class);
                startActivity(intent);
            }
        });

        postList = new ArrayList<>();
        postList.add(new Post(R.drawable.weather,"choy2736", R.drawable.travel,"안녕하세요!!"));
        postList.add(new Post(R.drawable.weather,"aaa", R.drawable.travel,"반갑습니다!!"));
        postList.add(new Post(R.drawable.weather,"bbb", R.drawable.travel,"날씨가 좋네요!!"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PostAdapter(postList);
        recyclerView.setAdapter(adapter);

        return v;
    }
}