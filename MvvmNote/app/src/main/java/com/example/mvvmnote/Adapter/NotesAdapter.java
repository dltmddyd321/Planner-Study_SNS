package com.example.mvvmnote.Adapter;

import static android.os.Build.VERSION_CODES.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.AttachedSurfaceControl;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmnote.MainActivity;
import com.example.mvvmnote.Model.Notes;
import com.example.mvvmnote.R;
import com.example.mvvmnote.UpdateNote;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        allNotesItem = new ArrayList<>(notes);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchNotes(List<Notes> filteredName) {
        this.notes = filteredName;
        notifyDataSetChanged();
    }

    @Override
    public notesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(com.example.mvvmnote.R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(notesViewHolder holder, int position) {

        Notes note = notes.get(position);

        try {
            switch (note.notesPriority) {
                case "1":
                    holder.notesPriority.setBackgroundResource(com.example.mvvmnote.R.drawable.red_shape);
                    break;
                case "2":
                    holder.notesPriority.setBackgroundResource(com.example.mvvmnote.R.drawable.green_shape);
                    break;
                case "3":
                    holder.notesPriority.setBackgroundResource(com.example.mvvmnote.R.drawable.yellow_shape);
                    break;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        holder.title.setText(note.notesTitle);
        holder.subTitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v -> {

            //기존에 저장된 값을 메모 수정 액티비티로 호출
            Intent intent = new Intent(mainActivity, UpdateNote.class);
            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subtitle", note.notesSubtitle);
            intent.putExtra("priority",note.notesPriority);
            intent.putExtra("note",note.notes);
            mainActivity.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class notesViewHolder extends RecyclerView.ViewHolder {

        TextView title, subTitle, notesDate;
        View notesPriority;

        public notesViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(com.example.mvvmnote.R.id.notesTitle);
            subTitle = itemView.findViewById(com.example.mvvmnote.R.id.notesSubtitle);
            notesDate = itemView.findViewById(com.example.mvvmnote.R.id.notesDate);
            notesPriority = itemView.findViewById(com.example.mvvmnote.R.id.notesPriority);
        }
    }
}
