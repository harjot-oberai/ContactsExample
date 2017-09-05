package com.sdsmdg.harjot.contactstest;

import android.app.ProgressDialog;
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

    List<String> gmailContacts;
    List<String> selectedGmailContacts;

    List<PhoneContact> phoneContacts;
    List<PhoneContact> selectedPhoneContacts;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        gmailButton = findViewById(R.id.gmail_button);
        contactsButton = findViewById(R.id.contacts_button);
        allGuestsButton = findViewById(R.id.all_guests_button);

        gmailContactFactory = new GmailContactFactory(this);
        phoneContactsFactory = new PhoneContactsFactory(this);

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
        if (gmailContactFactory.getContactsFetchListener() == null) {
            gmailContactFactory.addListener(new GmailContactsFetchListener() {
                @Override
                public void onFetchStart() {
                    // Start Progress Dialog
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getString(R.string.gmail_contacts_progress_dialog_text));
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                }

                @Override
                public void onContactsFetchComplete(List<String> emails) {
                    // Hide Progress Dialog
                    // Show Recycler
                    progressDialog.hide();
                    Log.d("COUNT", emails.size() + " : emails");
                }
            });
        }
        gmailContactFactory.authorizeAndFetch();
    }

    public void startPhoneContactsActivity() {
        // Handle Phone Contacts request
        if (phoneContactsFactory.getPhoneContactsFetchListener() == null) {
            phoneContactsFactory.addListener(new PhoneContactsFetchListener() {
                @Override
                public void onFetchStart() {
                    // Start Progress Dialog
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage(getString(R.string.phone_contacts_progress_dialog_text));
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                }

                @Override
                public void onFetchComplete(List<PhoneContact> phoneContactList) {
                    // Hide Progress Dialog
                    // Show Recycler
                    progressDialog.hide();
                    Log.d("COUNT", phoneContactList.size() + " : phone contacts");
                }
            });
        }
        phoneContactsFactory.requestPermissionAndFetch();
    }

    public void startAllGuestsActivity() {
        // Handle All Guests request
    }

}
