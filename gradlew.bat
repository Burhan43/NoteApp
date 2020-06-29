package note.app.poweredBy.BurhanAhmad.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import note.app.poweredBy.BurhanAhmad.Adapters.NoteAdapter;
import note.app.poweredBy.BurhanAhmad.Classes.AppRater;
import note.app.poweredBy.BurhanAhmad.Common.CommonVariables;
import note.app.poweredBy.BurhanAhmad.DBClasses.Note;
import note.app.poweredBy.BurhanAhmad.DBClasses.NoteViewModel;
import note.app.R;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton addNoteBtn;
    public static final int ADD_NOTE_REQ = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private Toolbar mToolbar;
    private  List<Note> notes = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notes.notify();


// Rate it dialogue
        AppRater appRater= new AppRater(this);

        AppRater.AppLaunched(this);


        mToolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("My Notes");
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
       