package com.sdsmdg.harjot.contactstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sdsmdg.harjot.contactstest.adapters.SelectionAdapter;

public class SelectionActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SelectionAdapter selectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.selection_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (getIntent().getStringExtra("type").equals("gmail")) {
            setTitle(getString(R.string.email_selection_toolbar_title));
            selectionAdapter = new SelectionAdapter(this, MainActivity.gmailContacts, null);
        } else if (getIntent().getStringExtra("type").equals("phone")) {
            setTitle(getString(R.string.phone_contact_selection_toolbar_title));
            selectionAdapter = new SelectionAdapter(this, null, MainActivity.phoneContacts);
        }

        recyclerView.setAdapter(selectionAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
