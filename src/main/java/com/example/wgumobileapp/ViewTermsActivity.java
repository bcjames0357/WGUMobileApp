package com.example.wgumobileapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wgumobileapp.Adapters.TermRecyclerViewAdapter;
import com.example.wgumobileapp.Models.Term;

import java.util.ArrayList;

public class ViewTermsActivity extends AppCompatActivity implements TermRecyclerViewAdapter.ItemClickListener, View.OnClickListener {
    TermRecyclerViewAdapter adapter;
    private FloatingActionButton addTerm;

    private static ArrayList<Term> terms = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_terms);

        terms.clear();
        terms.addAll(MainActivity.getDatabase().getTermDAO().getTerms());

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.termsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TermRecyclerViewAdapter(this, terms);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        addTerm = (FloatingActionButton) findViewById(R.id.addTerm);
    }

    @Override
    public void onItemClick(View view, int position) {
        Term details = terms.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("termDetails", details);
        Intent cartIntent = new Intent(ViewTermsActivity.this, TermDetailsActivity.class);
        cartIntent.putExtras(bundle);
        this.startActivity(cartIntent); }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.addTerm:
                intent = new Intent(this, AddTermActivity.class);
                startActivity(intent);
                break;
        }
    }


}

