package com.sdsmdg.harjot.contactstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sdsmdg.harjot.gmail_lib.GmailContactFactory;
import com.sdsmdg.harjot.gmail_lib.interfaces.GmailContactsFetchCompletionListener;

import java.util.List;

import static com.sdsmdg.harjot.gmail_lib.Constants.Constants.RC_AUTHORIZE_CONTACTS;
import static com.sdsmdg.harjot.gmail_lib.Constants.Constants.RC_REAUTHORIZE;

public class MainActivity extends AppCompatActivity {

    GmailContactFactory gmailContactFactory;
    Button gmailButton, contactsButton, allGuestsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gmailButton = findViewById(R.id.gmail_button);
        contactsButton = findViewById(R.id.contacts_button);
        allGuestsButton = findViewById(R.id.all_guests_button);

        gmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGmailContactsActivity();
            }
        });

        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPhoneContactsActivity();
            }
        });

        allGuestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAllGuestsActivity();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_AUTHORIZE_CONTACTS || requestCode == RC_REAUTHORIZE) {
            gmailContactFactory.handleResult(requestCode, resultCode, data);
        }

    }

    public void startGmailContactsActivity() {
        // Handle Gmail Contacts request
        gmailContactFactory = new GmailContactFactory(this);
        gmailContactFactory.authorize();
        gmailContactFactory.addListener(new GmailContactsFetchCompletionListener() {
            @Override
            public void onFetchStart() {
                // Start Progress Dialog
            }

            @Override
            public void onContactsFetchComplete(List<String> emails) {
                // Hide Progress Dialog
                // Show Recycler
            }
        });
    }

    public void startPhoneContactsActivity() {
        // Handle Phone Contacts request
    }

    public void startAllGuestsActivity() {
        // Handle All Guests request
    }

}
