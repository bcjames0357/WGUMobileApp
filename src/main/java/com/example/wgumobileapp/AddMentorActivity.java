package com.example.wgumobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wgumobileapp.Adapters.CourseRecyclerViewAdapter;
import com.example.wgumobileapp.Models.Mentor;

public class AddMentorActivity extends AppCompatActivity implements CourseRecyclerViewAdapter.ItemClickListener, View.OnClickListener  {

    private Button cancel;
    private Button submit;
    private EditText nameBox;
    private EditText phoneBox;
    private EditText emailBox;
    private Mentor mentor;

    private final String PHONE_FORMAT = "([(]?\\d{3}[)]?[ -]?\\d{3}[ -]?\\d{4})";

    // ReGex to validate email address from https://emailregex.com/
    private final String EMAIL_FORMAT = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?" +
            "^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\" +
            "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
            "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
            "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b" +
            "\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor);
        mentor = new Mentor();

        cancel = (Button) findViewById(R.id.cancel);
        submit = (Button) findViewById(R.id.submit);
        nameBox = (EditText) findViewById(R.id.nameBox);
        phoneBox = (EditText) findViewById(R.id.phoneBox);
        emailBox = (EditText) findViewById(R.id.emailBox);

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
                if(nameBox.getText().toString().trim().length() > 0) {
                    mentor.setName(nameBox.getText().toString());
                } else {
                    nameBox.setError("Please enter a name for the mentor.");
                    break;
                }
                if(phoneBox.getText().toString().trim().length() > 0
                        && phoneBox.getText().toString().matches(PHONE_FORMAT)) {
                    mentor.setPhone(phoneBox.getText().toString());
                } else {
                    phoneBox.setError("Please enter a valid phone number.");
                    break;
                }
                if(emailBox.getText().toString().trim().length() > 0
                        && emailBox.getText().toString().matches(EMAIL_FORMAT)){
                    mentor.setEmail(emailBox.getText().toString());
                } else {
                    emailBox.setError("Please enter a valid email address.");
                    break;
                }
                MainActivity.getDatabase().getMentorDAO().insert(mentor);
                Intent intent = new Intent(this, ViewMentorsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}
