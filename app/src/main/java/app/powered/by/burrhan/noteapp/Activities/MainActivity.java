package app.powered.by.burrhan.noteapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.service.Common;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import app.powered.by.burrhan.noteapp.Adapters.NoteAdapter;
import app.powered.by.burrhan.noteapp.Classes.AppRater;
import app.powered.by.burrhan.noteapp.Common.CommonVariables;
import app.powered.by.burrhan.noteapp.DBClasses.Note;
import app.powered.by.burrhan.noteapp.DBClasses.NoteViewModel;
import app.powered.by.burrhan.noteapp.R;


public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton addNoteBtn;
    public static final int ADD_NOTE_REQ = 1;
    private TextView noNotesYet;
    public static final int EDIT_NOTE_REQUEST = 2;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noNotesYet = findViewById(R.id.no_notes_yet);


// Rate it dialogue
        AppRater appRater = new AppRater(this);

        AppRater.AppLaunched(this);


        mToolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("My Notes");
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.backgroundActionBar));


        addNoteBtn = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.note_rv);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);


        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, adapter::submitList);


        noteViewModel.getAllNotes().observe(this, notes -> {

            CommonVariables.size = notes.size();
//                recyclerView.setVisibility(View.GONE);

            if (notes.size() < 1) {
                recyclerView.setVisibility(View.GONE);
                noNotesYet.setVisibility(View.VISIBLE);
                SpannableStringBuilder ssb = new SpannableStringBuilder();
                ssb.append("You done't have created any Note yet to show, Please click    ");
                ssb.setSpan
                        (
                                new ImageSpan
                                        (
                                                this,
                                                R.drawable.ic_add
                                        ),
                                ssb.length() - 1,
                                ssb.length(), 0
                        );

                ssb.append("   at bottom right corner to create new Note.");

                noNotesYet.setText(ssb);


            } else {
                recyclerView.setVisibility(View.VISIBLE);
                noNotesYet.setVisibility(View.GONE);

            }

        });


        addNoteBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AddEditNoteActivity.class);
            i.putExtra("size", adapter.getItemCount());

            startActivityForResult(i, ADD_NOTE_REQ);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListenerForRecyclerViewItems(note -> {

            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
            intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
            intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
            intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
            intent.putExtra("sizeBy", CommonVariables.size);

            startActivityForResult(intent, EDIT_NOTE_REQUEST);

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ADD_NOTE_REQ && resultCode == RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all_notes) {
            noteViewModel.deleteAllNotes();
            Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
