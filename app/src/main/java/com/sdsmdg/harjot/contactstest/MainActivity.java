package com.sdsmdg.harjot.contactstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sdsmdg.harjot.contacts_lib.Constants;
import com.sdsmdg.harjot.contacts_lib.PhoneContactsFactory;
import com.sdsmdg.harjot.contactstest.allinvitees.AllInviteesFragment;
import com.sdsmdg.harjot.contactstest.allinvitees.AllInviteesPresenter;
import com.sdsmdg.harjot.contactstest.selection.SelectionFragment;
import com.sdsmdg.harjot.contactstest.selection.SelectionPresenter;
import com.sdsmdg.harjot.gmail_lib.GmailContactFactory;

import static com.sdsmdg.harjot.contactstest.Constants.ALL_INVITEES_FRAGMENT_TAG;
import static com.sdsmdg.harjot.contactstest.Constants.SELECTION_FRAGMENT_TAG;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_AUTHORIZE_CONTACTS;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_REAUTHORIZE;

public class MainActivity extends AppCompatActivity {

    GmailContactFactory gmailContactFactory;
    PhoneContactsFactory phoneContactsFactory;
    Button gmailButton, contactsButton, allGuestsButton;

    SelectionFragment selectionFragment;
    SelectionPresenter selectionPresenter;

    AllInviteesFragment allInviteesFragment;
    AllInviteesPresenter allInviteesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gmailButton = findViewById(R.id.gmail_button);
        contactsButton = findViewById(R.id.contacts_button);
        allGuestsButton = findViewById(R.id.all_guests_button);

        gmailContactFactory = new GmailContactFactory(this);
        phoneContactsFactory = new PhoneContactsFactory(this);

        selectionFragment = (SelectionFragment) getSupportFragmentManager().findFragmentByTag(SELECTION_FRAGMENT_TAG);
        if (selectionFragment == null) {
            selectionFragment = SelectionFragment.getInstance();
        }
        selectionPresenter = new SelectionPresenter(
                selectionFragment,
                gmailContactFactory,
                phoneContactsFactory);

        allInviteesFragment = (AllInviteesFragment) getSupportFragmentManager().findFragmentByTag(ALL_INVITEES_FRAGMENT_TAG);
        if (allInviteesFragment == null) {
            allInviteesFragment = AllInviteesFragment.getInstance();
        }
        allInviteesPresenter = new AllInviteesPresenter(
                allInviteesFragment,
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
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.READ_CONTACTS_PERMISSION_REQUEST_CODE) {
            phoneContactsFactory.handleRequestPermissions(permissions, grantResults);
        }
    }

    public void startGmailContactsActivity() {
        Bundle args = new Bundle();
        args.putString("type", "gmail");
        selectionFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, selectionFragment, SELECTION_FRAGMENT_TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void startPhoneContactsActivity() {
        Bundle args = new Bundle();
        args.putString("type", "phone");
        selectionFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, selectionFragment, SELECTION_FRAGMENT_TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void startAllGuestsActivity() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, allInviteesFragment, ALL_INVITEES_FRAGMENT_TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

}
