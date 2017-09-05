package com.sdsmdg.harjot.gmail_lib;


import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import com.sdsmdg.harjot.gmail_lib.Constants.Constants;
import com.sdsmdg.harjot.gmail_lib.interfaces.GmailContactsFetchCompletionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetContactsAsyncTask extends AsyncTask<Void, List<Person>, List<Person>> {

    private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private Context mContext;
    private Account mAccount;
    private GmailContactsFetchCompletionListener mContactsFetchCompletionListener;

    public GetContactsAsyncTask(Context context, Account account, GmailContactsFetchCompletionListener contactsFetchCompletionListener) {
        mContext = context;
        mAccount = account;
        mContactsFetchCompletionListener = contactsFetchCompletionListener;
    }

    @Override
    protected List<Person> doInBackground(Void... params) {
        mContactsFetchCompletionListener.onFetchStart();
        List<Person> result = null;
        try {
            GoogleAccountCredential credential =
                    GoogleAccountCredential.usingOAuth2(mContext, Collections.singleton("https://www.googleapis.com/auth/contacts.readonly"));
            credential.setSelectedAccount(mAccount);
            People service = new People.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName("REST API sample")
                    .build();
            ListConnectionsResponse connectionsResponse = service
                    .people()
                    .connections()
                    .list("people/me")
                    .setRequestMaskIncludeField("person.emailAddresses")
                    .setPageSize(200)
                    .execute();
            result = connectionsResponse.getConnections();
        } catch (UserRecoverableAuthIOException userRecoverableException) {
            ((Activity) mContext).startActivityForResult(userRecoverableException.getIntent(), Constants.RC_REAUTHORIZE);
        } catch (IOException e) {
            // Handle Exception
            Log.e("FETCH", e.getMessage() + ":");
        }

        return result;
    }

    @Override
    protected void onCancelled() {
        // Handle cancel request
    }

    @Override
    protected void onPostExecute(List<Person> connections) {
        mContactsFetchCompletionListener.onContactsFetchComplete(processApiResponse(connections));
    }

    public List<String> processApiResponse(List<Person> persons) {
        List<String> result = new ArrayList<>();
        for (Person p : persons) {
            if (p.getEmailAddresses() != null) {
                for (EmailAddress emailAddress : p.getEmailAddresses()) {
                    Log.d("RESULT", emailAddress.getValue());
                    result.add(emailAddress.getValue());
                }
            }
        }
        return result;
    }

}