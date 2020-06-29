package app.powered.by.burrhan.noteapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import app.powered.by.burrhan.noteapp.DBClasses.Note;
import app.powered.by.burrhan.noteapp.R;


public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private onRecyclerItemClickListener listener;
    public static int position1;



    public NoteAdapter() {
        super(DIFF_CALLBACK);

    }


    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();



        }


    };





    @NonNull
    @Override
    public NoteHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View itemView;


        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(itemView);


    }


    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {



        Note currentNote = getItem(position);

        if (currentNote != null) {
            holder.title_tv.setText(currentNote.getTitle());
            holder.priority_tv.setText(String.valueOf(currentNote.getPriority()));
            holder.desc_tv.setText(currentNote.getDescription());

            position1= position;
        }
    }



    public Note getNoteAt(int position) {
        return getItem(position);


    }




    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView priority_tv;
        private TextView title_tv;
        private TextView desc_tv;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            priority_tv = itemView.findViewById(R.id.priority_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
            desc_tv = itemView.findViewById(R.id.desc_tv);



            itemView.setOnClickListener(view -> {

                int position = getAdapterPosition();




                if (listener != null && position != RecyclerView.NO_POSITION) {

                    listener.onItemClick(getItem(position));


                }

            });
        }
    }



    public interface onRecyclerItemClickListener {
        void onItemClick(Note note);
    }


    public void setOnItemClickListenerForRecyclerViewItems(onRecyclerItemClickListener listener) {
        this.listener = listener;
    }

}
