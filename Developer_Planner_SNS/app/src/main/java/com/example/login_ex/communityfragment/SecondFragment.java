package com.example.login_ex.communityfragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.login_ex.FullImage;
import com.example.login_ex.R;
import com.example.login_ex.communityadapter.SearchImageAdapter;

public class SecondFragment extends Fragment {

    GridView gridView;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.second_fragment, container, false);

        gridView = (GridView) v.findViewById(R.id.gridView);
        gridView.setAdapter(new SearchImageAdapter(getContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), FullImage.class);
                intent.putExtra("id", i);
                startActivity(intent);
            }
        });

        return v;
    }

}