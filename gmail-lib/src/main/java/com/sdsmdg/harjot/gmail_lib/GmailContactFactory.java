package com.sdsmdg.harjot.gmail_lib;


import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.sdsmdg.harjot.gmail_lib.interfaces.GmailContactsFetchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.app.Activity.RESULT_OK;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_AUTHORIZE_CONTACTS;
import static com.sdsmdg.harjot.gmail_lib.Constants.RC_REAUTHORIZE;

public class GmailContactFactory implements GmailContactsFetchListener {

    private Context context;
    private Account mAuthorizedAccount;
    private GmailContactsFetchListener contactsFetchListener;

    private boolean isFetched = false;
    private ArrayList<String> fetchedEmails;
    private ArrayList<String> selectedEmails;

    public GmailContactFactory(Context context) {
        this.context = context;
        fetchedEmails = new ArrayList<>();
        selectedEmails = new ArrayList<>();
    }

    public void authorizeAndFetch() {
        if (isFetched && contactsFetchListener != null) {
            contactsFetchListener.onContactsFetchComplete(fetchedEmails);
            return;
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope("https://www.googleapis.com/auth/contacts.readonly"))
                .build();

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity) context).startActivityForResult(signInIntent, RC_AUTHORIZE_CONTACTS);
    }

    public void handleResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_AUTHORIZE_CONTACTS) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
                if (googleSignInAccount != null) {
                    mAuthorizedAccount = googleSignInAccount.getAccount();
                }
                getContacts(mAuthorizedAccount);
            }
        } else if (requestCode == RC_REAUTHORIZE) {
            if (resultCode == RESULT_OK) {
                getContacts(mAuthorizedAccount);
            }
        }
    }

    private void getContacts(Account account) {
        if (account == null) {
            return;
        }
        GetContactsAsyncTask getContactsAsyncTask = new GetContactsAsyncTask(context, account, this);
        getContactsAsyncTask.execute();
    }

    public void addListener(GmailContactsFetchListener contactsFetchListener) {
        this.contactsFetchListener = contactsFetchListener;
    }

    public GmailContactsFetchListener getContactsFetchListener() {
        return contactsFetchListener;
    }

    public ArrayList<String> getFetchedEmails() {
        return fetchedEmails;
    }

    public ArrayList<String> getSelectedEmails() {
        return selectedEmails;
    }

    public boolean isFetched() {
        return isFetched;
    }

    public void sortSelectedItems(){
        Collections.sort(selectedEmails, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.toLowerCase().compareTo(s2.toLowerCase());
            }
        });
    }

    @Override
    public void onFetchStart() {
        if (contactsFetchListener != null)
            contactsFetchListener.onFetchStart();
    }

    @Override
    public void onContactsFetchComplete(ArrayList<String> emails) {
        isFetched = true;
        fetchedEmails = emails;
        if (contactsFetchListener != null)
            contactsFetchListener.onContactsFetchComplete(emails);
    }
}