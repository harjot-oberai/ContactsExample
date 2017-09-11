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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.sdsmdg.harjot.contactstest.Constants.SELECTION_ACTIVITY_REQUEST_CODE;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_AUTHORIZE_CONTACTS;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_REAUTHORIZE;

public class MainActivity extends AppCompatActivity {

    GmailContactFactory gmailContactFactory;
    PhoneContactsFactory phoneContactsFactory;
    Button gmailButton, contactsButton, allGuestsButton;

    ArrayList<String> gmailContacts;
    ArrayList<String> selectedGmailContacts;

    ArrayList<PhoneContact> phoneContacts;
    ArrayList<PhoneContact> selectedPhoneContacts;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        gmailContacts = new ArrayList<>();
        selectedGmailContacts = new ArrayList<>();
        phoneContacts = new ArrayList<>();
        selectedPhoneContacts = new ArrayList<>();

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

        if (requestCode == SELECTION_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null && data.hasExtra("type")) {
                if (data.getStringExtra("type").equals("gmail")) {
                    selectedGmailContacts = data.getStringArrayListExtra("selectedGmailContacts");
                } else if (data.getStringExtra("type").equals("phone")) {
                    selectedPhoneContacts = data.getParcelableArrayListExtra("selectedPhoneContacts");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.READ_CONTACTS_PERMISSION_REQUEST_CODE) {
            phoneContactsFactory.handleRequestPermissions(permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Collections.sort(selectedPhoneContacts, new Comparator<PhoneContact>() {
            public int compare(PhoneContact s1, PhoneContact s2) {
                return s1.getName().toLowerCase().compareTo(s2.getName().toLowerCase());
            }
        });
        Collections.sort(selectedGmailContacts, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.toLowerCase().compareTo(s2.toLowerCase());
            }
        });
    }

    public void startGmailContactsActivity() {

        // If already fetched, just start the activity
        if (gmailContacts.size() > 0) {
            Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
            intent.putExtra("type", "gmail");
            intent.putStringArrayListExtra("gmailContacts", gmailContacts);
            intent.putStringArrayListExtra("selectedGmailContacts", selectedGmailContacts);
            startActivityForResult(intent, SELECTION_ACTIVITY_REQUEST_CODE);
            return;
        }

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
                public void onContactsFetchComplete(ArrayList<String> emails) {
                    // Hide Progress Dialog
                    // Show Recycler
                    progressDialog.hide();
                    Collections.sort(emails, new Comparator<String>() {
                        public int compare(String s1, String s2) {
                            return s1.toLowerCase().compareTo(s2.toLowerCase());
                        }
                    });
                    gmailContacts = emails;
                    Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
                    intent.putExtra("type", "gmail");
                    intent.putStringArrayListExtra("gmailContacts", gmailContacts);
                    intent.putStringArrayListExtra("selectedGmailContacts", selectedGmailContacts);
                    startActivityForResult(intent, SELECTION_ACTIVITY_REQUEST_CODE);
                    Log.d("COUNT", emails.size() + " : emails");
                }
            });
        }
        gmailContactFactory.authorizeAndFetch();
    }

    public void startPhoneContactsActivity() {

        // If already fetched, just start the activity
        if (phoneContacts.size() > 0) {
            Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
            intent.putExtra("type", "phone");
            intent.putParcelableArrayListExtra("phoneContacts", phoneContacts);
            intent.putParcelableArrayListExtra("selectedPhoneContacts", selectedPhoneContacts);
            startActivityForResult(intent, SELECTION_ACTIVITY_REQUEST_CODE);
            return;
        }

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
                public void onFetchComplete(ArrayList<PhoneContact> fetchedPhoneContactList) {
                    // Hide Progress Dialog
                    // Show Recycler
                    progressDialog.hide();
                    Collections.sort(fetchedPhoneContactList, new Comparator<PhoneContact>() {
                        public int compare(PhoneContact s1, PhoneContact s2) {
                            return s1.getName().toLowerCase().compareTo(s2.getName().toLowerCase());
                        }
                    });
                    phoneContacts = fetchedPhoneContactList;
                    Intent intent = new Intent(MainActivity.this, SelectionActivity.class);
                    intent.putExtra("type", "phone");
                    intent.putParcelableArrayListExtra("phoneContacts", phoneContacts);
                    intent.putParcelableArrayListExtra("selectedPhoneContacts", selectedPhoneContacts);
                    startActivityForResult(intent, SELECTION_ACTIVITY_REQUEST_CODE);
                    Log.d("COUNT", fetchedPhoneContactList.size() + " : phone contacts");
                }
            });
        }
        phoneContactsFactory.requestPermissionAndFetch();
    }

    public void startAllGuestsActivity() {
        // Handle All Guests request
        Intent intent = new Intent(MainActivity.this, AllInvitees.class);
        intent.putStringArrayListExtra("selectedGmailContacts", selectedGmailContacts);
        intent.putParcelableArrayListExtra("selectedPhoneContacts", selectedPhoneContacts);
        startActivity(intent);
    }

}
