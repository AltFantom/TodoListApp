package com.kupriyanov.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private TextView editTextEnterNote;

    private RadioButton radioButtonLowPriority;
    private RadioButton radioButtonMediumPriority;

    private Button buttonSave;

    private AddViewViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel = new ViewModelProvider(this).get(AddViewViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose){
                    finish();
                }
            }
        });
        initViews();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void initViews() {
        editTextEnterNote = findViewById(R.id.editTextEnterNote);
        radioButtonLowPriority = findViewById(R.id.radioButtonLowPriority);
        radioButtonMediumPriority = findViewById(R.id.radioButtonMediumPriority);
        buttonSave = findViewById(R.id.buttonSave);
    }

    private void saveNote() {
        String text = editTextEnterNote.getText().toString().trim();
        if (text.equals("")) {
            Toast.makeText(AddNoteActivity.this,
                    R.string.error_text,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        int priority = getPriority();
        Note note = new Note(text, priority);
        viewModel.saveNote(note);
    }

    private int getPriority() {
        if (radioButtonLowPriority.isChecked()) {
            return 0;
        } else if (radioButtonMediumPriority.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}







