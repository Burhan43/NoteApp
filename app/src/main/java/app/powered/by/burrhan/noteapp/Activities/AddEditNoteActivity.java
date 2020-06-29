package app.powered.by.burrhan.noteapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import app.powered.by.burrhan.noteapp.R;


public class AddEditNoteActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText titleET, descET;

    NumberPicker priorityNP;

    public static final String EXTRA_TITLE =
            "note.app.EXTRA_TITLE";
    public static final String EXTRA_ID =
            "note.app.EXTRA_ID";
    public static final String EXTRA_DESCRIPTION =
            "note.app.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "note.app.EXTRA_PRIORITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        mToolbar = findViewById(R.id.note_activity_toolbar);
        setSupportActionBar(mToolbar);
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.backgroundActionBar));


        titleET = findViewById(R.id.edit_text_title);
        descET = findViewById(R.id.edit_text_description);
        priorityNP = findViewById(R.id.number_picker_priority);

        int size = getIntent().getIntExtra("size", 0);

        priorityNP.setMinValue(1);
        priorityNP.setMaxValue(size + 1);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {

            int size2 = intent.getIntExtra("sizeBy", 1);
//            priorityNP.setValue(intent.getIntExtra("sizeBy", 1));

            Toast.makeText(this, "" + size2, Toast.LENGTH_SHORT).show();
            getSupportActionBar().setTitle("Edit Note");
            titleET.setText(intent.getStringExtra(EXTRA_TITLE));
            descET.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priorityNP.setMinValue(1);
            priorityNP.setMaxValue(intent.getIntExtra("sizeBy", size + 1));
            priorityNP.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            priorityNP.setWrapSelectorWheel(true);
//

        } else {
            getSupportActionBar().setTitle("Add Note");
        }


    }

    private void saveNote() {
        String title = titleET.getText().toString();
        String description = descET.getText().toString();
        int priority = priorityNP.getValue();
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

