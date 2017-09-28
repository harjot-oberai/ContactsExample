package com.sdsmdg.harjot.contactstest.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sdsmdg.harjot.contacts_lib.Constants;
import com.sdsmdg.harjot.contacts_lib.PhoneContactsFactory;
import com.sdsmdg.harjot.contactstest.R;
import com.sdsmdg.harjot.gmail_lib.GmailContactFactory;

import static com.sdsmdg.harjot.contactstest.Constants.ALL_INVITEES_FRAGMENT_TAG;
import static com.sdsmdg.harjot.contactstest.Constants.SELECTION_FRAGMENT_TAG;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_AUTHORIZE_CONTACTS;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_REAUTHORIZE;

public class MainActivity extends AppCompatActivity {

    GmailContactFactory gmailContactFactory;
    PhoneContactsFactory phoneContactsFactory;
    Button gmailButton, contactsButton, allGuestsButton;

    MainActivitySelectionFragment mainActivitySelectionFragment;
    MainActivitySelectionPresenter mainActivitySelectionPresenter;

    MainActivityAllInviteesFragment mainActivityAllInviteesFragment;
    MainActivityAllInviteesPresenter mainActivityAllInviteesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gmailButton = findViewById(R.id.gmail_button);
        contactsButton = findViewById(R.id.contacts_button);
        allGuestsButton = findViewById(R.id.all_guests_button);

        gmailContactFactory = new GmailContactFactory(this);
        phoneContactsFactory = new PhoneContactsFactory(this);

        mainActivitySelectionFragment = (MainActivitySelectionFragment) getSupportFragmentManager().findFragmentByTag(SELECTION_FRAGMENT_TAG);
        if (mainActivitySelectionFragment == null) {
            mainActivitySelectionFragment = MainActivitySelectionFragment.getInstance();
        }
        mainActivitySelectionPresenter = new MainActivitySelectionPresenter(
                mainActivitySelectionFragment,
                gmailContactFactory,
                phoneContactsFactory);

        mainActivityAllInviteesFragment = (MainActivityAllInviteesFragment) getSupportFragmentManager().findFragmentByTag(ALL_INVITEES_FRAGMENT_TAG);
        if (mainActivityAllInviteesFragment == null) {
            mainActivityAllInviteesFragment = MainActivityAllInviteesFragment.getInstance();
        }
        mainActivityAllInviteesPresenter = new MainActivityAllInviteesPresenter(
                mainActivityAllInviteesFragment,
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
        mainActivitySelectionFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, mainActivitySelectionFragment, SELECTION_FRAGMENT_TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void startPhoneContactsActivity() {
        Bundle args = new Bundle();
        args.putString("type", "phone");
        mainActivitySelectionFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, mainActivitySelectionFragment, SELECTION_FRAGMENT_TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void startAllGuestsActivity() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_container, mainActivityAllInviteesFragment, ALL_INVITEES_FRAGMENT_TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

}
