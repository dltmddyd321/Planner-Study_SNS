package com.example.mvvmnote.Adapter;

import static android.os.Build.VERSION_CODES.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmnote.MainActivity;
import com.example.mvvmnote.Model.Notes;
import com.example.mvvmnote.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MainActivity mainActivity;
    List<Notes> notes;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
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
