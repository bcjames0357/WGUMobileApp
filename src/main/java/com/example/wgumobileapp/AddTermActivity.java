package com.example.wgumobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wgumobileapp.Adapters.TermRecyclerViewAdapter;
import com.example.wgumobileapp.Models.Term;

public class AddTermActivity extends AppCompatActivity implements TermRecyclerViewAdapter.ItemClickListener, View.OnClickListener {

    private Button cancel;
    private Button submit;
    private EditText titleBox;
    private EditText startBox;
    private EditText endBox;
    private Term term;
    private final String DATE_PATTERN = "((1[0-2][-/][0-3]\\d[-/]20\\d\\d)|(0?[1-9]+[-/][0-3][0-9][-/]20\\d\\d))";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        term = new Term();
        cancel = (Button) findViewById(R.id.cancel);
        submit = (Button) findViewById(R.id.submit);
        titleBox = (EditText) findViewById(R.id.titleBox);
        startBox = (EditText) findViewById(R.id.startDateBox);
        endBox = (EditText) findViewById(R.id.endDateBox);

    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.submit:
                if(titleBox.getText().toString().trim().length() > 0){
                    term.setTitle(titleBox.getText().toString());
                } else {
                    titleBox.setError("Please enter a term title.");
                    break;
                }
                if(startBox.getText().toString().trim().length() > 0
                        && startBox.getText().toString().matches(DATE_PATTERN)){
                    term.setStart(startBox.getText().toString());
                } else {
                    startBox.setError("Please enter a valid start date in MM/DD/YYYY format.");
                    break;
                }
                if(endBox.getText().toString().trim().length() > 0
                        && endBox.getText().toString().matches(DATE_PATTERN)){
                    term.setEnd(endBox.getText().toString());
                } else {
                    endBox.setError("Please enter a valid end date in MM/DD/YYYY format.");
                    break;
                }
                MainActivity.getDatabase().getTermDAO().insert(term);
                Intent intent = new Intent(this, ViewTermsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
