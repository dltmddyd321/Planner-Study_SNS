package com.example.mvvmtest1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmtest1.databinding.ListBinding;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyAdapter> {

    List<ListViewModel> data;
    Context context;
    private LayoutInflater inflater;

    public ListAdapter(List<ListViewModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        if(inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ListBinding listBinding = ListBinding.inflate(inflater, parent, false);
        return new MyAdapter(listBinding);
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {

        private ListBinding listBinding;

        public MyAdapter(ListBinding listBinding) {
            super(listBinding.getRoot());
            this.listBinding = listBinding;
        }

        public void bind(ListViewModel listViewModel) {
            this.listBinding.setViewModel(listViewModel);
        }

        public ListBinding getListBinding() {
            return listBinding;
        }
    }
}
