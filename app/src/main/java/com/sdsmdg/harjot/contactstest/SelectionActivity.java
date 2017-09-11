package com.sdsmdg.harjot.contactstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.adapters.SelectionAdapter;

import java.util.ArrayList;

public class SelectionActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SelectionAdapter selectionAdapter;

    ArrayList<String> gmailContacts;
    ArrayList<PhoneContact> phoneContacts;

    ArrayList<String> selectedGmailContacts;
    ArrayList<PhoneContact> selectedPhoneContacts;

    String type = "";

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

        type = getIntent().getStringExtra("type");

        if (type.equals("gmail")) {
            setTitle(getString(R.string.email_selection_toolbar_title));
            gmailContacts = getIntent().getStringArrayListExtra("gmailContacts");
            selectedGmailContacts = getIntent().getStringArrayListExtra("selectedGmailContacts");
            selectionAdapter = new SelectionAdapter(this, gmailContacts, null, selectedGmailContacts, null);
        } else if (type.equals("phone")) {
            setTitle(getString(R.string.phone_contact_selection_toolbar_title));
            phoneContacts = getIntent().getParcelableArrayListExtra("phoneContacts");
            selectedPhoneContacts = getIntent().getParcelableArrayListExtra("selectedPhoneContacts");
            selectionAdapter = new SelectionAdapter(this, null, phoneContacts, null, selectedPhoneContacts);
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

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("type", type);
        resultIntent.putStringArrayListExtra("selectedGmailContacts", selectedGmailContacts);
        resultIntent.putParcelableArrayListExtra("selectedPhoneContacts", selectedPhoneContacts);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}
