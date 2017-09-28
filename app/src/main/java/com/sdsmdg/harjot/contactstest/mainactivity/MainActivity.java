package com.sdsmdg.harjot.contactstest.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sdsmdg.harjot.contacts_lib.Constants;
import com.sdsmdg.harjot.contacts_lib.PhoneContactsFactory;
import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.AllInvitees;
import com.sdsmdg.harjot.contactstest.R;
import com.sdsmdg.harjot.gmail_lib.GmailContactFactory;

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

    MainActivitySelectionFragment mainActivitySelectionFragment;

    MainActivitySelectionPresenter mainActivitySelectionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gmailContacts = new ArrayList<>();
        selectedGmailContacts = new ArrayList<>();
        phoneContacts = new ArrayList<>();
        selectedPhoneContacts = new ArrayList<>();

        gmailButton = findViewById(R.id.gmail_button);
        contactsButton = findViewById(R.id.contacts_button);
        allGuestsButton = findViewById(R.id.all_guests_button);

        gmailContactFactory = new GmailContactFactory(this);
        phoneContactsFactory = new PhoneContactsFactory(this);

        mainActivitySelectionFragment = (MainActivitySelectionFragment) getSupportFragmentManager().findFragmentById(R.id.frag_container);

        if (mainActivitySelectionFragment == null) {
            mainActivitySelectionFragment = MainActivitySelectionFragment.getInstance();
        }

        mainActivitySelectionPresenter = new MainActivitySelectionPresenter(
                mainActivitySelectionFragment,
                gmailContactFactory,
                phoneContactsFactory);

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
        Bundle args = new Bundle();
        args.putString("type", "gmail");
        mainActivitySelectionFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, mainActivitySelectionFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void startPhoneContactsActivity() {
        Bundle args = new Bundle();
        args.putString("type", "phone");
        mainActivitySelectionFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, mainActivitySelectionFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void startAllGuestsActivity() {
        // Handle All Guests request
        Intent intent = new Intent(MainActivity.this, AllInvitees.class);
        intent.putStringArrayListExtra("selectedGmailContacts", selectedGmailContacts);
        intent.putParcelableArrayListExtra("selectedPhoneContacts", selectedPhoneContacts);
        startActivity(intent);
    }

}
