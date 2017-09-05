package com.sdsmdg.harjot.contactstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sdsmdg.harjot.contacts_lib.Constants;
import com.sdsmdg.harjot.contacts_lib.PhoneContactsFactory;
import com.sdsmdg.harjot.contacts_lib.interfaces.PhoneContactsFetchListener;
import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.gmail_lib.GmailContactFactory;
import com.sdsmdg.harjot.gmail_lib.interfaces.GmailContactsFetchListener;

import java.util.List;

import static com.sdsmdg.harjot.gmail_lib.Constants.RC_AUTHORIZE_CONTACTS;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_REAUTHORIZE;

public class MainActivity extends AppCompatActivity {

    GmailContactFactory gmailContactFactory;
    PhoneContactsFactory phoneContactsFactory;
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
            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.READ_CONTACTS_PERMISSION_REQUEST_CODE) {
            phoneContactsFactory.handleRequestPermissions(permissions, grantResults);
            return;
        }
    }

    public void startGmailContactsActivity() {
        // Handle Gmail Contacts request
        gmailContactFactory = new GmailContactFactory(this);
        gmailContactFactory.authorizeAndFetch();
        gmailContactFactory.addListener(new GmailContactsFetchListener() {
            @Override
            public void onFetchStart() {
                // Start Progress Dialog
            }

            @Override
            public void onContactsFetchComplete(List<String> emails) {
                // Hide Progress Dialog
                // Show Recycler
                Log.d("COUNT", emails.size() + " : emails");
            }
        });
    }

    public void startPhoneContactsActivity() {
        // Handle Phone Contacts request
        phoneContactsFactory = new PhoneContactsFactory(this);
        phoneContactsFactory.requestPermissionAndFetch();
        phoneContactsFactory.addListener(new PhoneContactsFetchListener() {
            @Override
            public void onFetchStart() {
                // Start Progress Dialog
            }

            @Override
            public void onFetchComplete(List<PhoneContact> phoneContactList) {
                // Hide Progress Dialog
                // Show Recycler
                Log.d("COUNT", phoneContactList.size() + " : phone contacts");
            }
        });
    }

    public void startAllGuestsActivity() {
        // Handle All Guests request
    }

}
