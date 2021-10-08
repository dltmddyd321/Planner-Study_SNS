package com.example.login_ex.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.login_ex.R;
import com.example.login_ex.memo.MemoActivity;
import com.example.login_ex.memo.UpdateMemo;
import com.example.login_ex.model.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MemoActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;

    public NotesAdapter(MemoActivity mainActivity, List<Notes> notes) {
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
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(notesViewHolder holder, int position) {

        //리스트에 등록된 노트 한 개씩 호출
        Notes note = notes.get(position);

        //각 테마 색상이 선택되었을 시 아이콘 배치 설정
        try {
            switch (note.notesPriority) {
                case "1":
                    holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
                    break;
                case "2":
                    holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
                    break;
                case "3":
                    holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
                    break;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        //Adapter를 통한 값 매칭
        holder.title.setText(note.notesTitle);
        holder.subTitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v -> {

            //기존에 저장된 값을 메모 수정 액티비티로 호출
            Intent intent = new Intent(mainActivity, UpdateMemo.class);
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

            //Adapter 매칭을 위해 실제 오브젝트 id 연결
            title = itemView.findViewById(R.id.notesTitle);
            subTitle = itemView.findViewById(R.id.notesSubtitle);
            notesDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
}