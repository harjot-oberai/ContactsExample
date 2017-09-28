package com.sdsmdg.harjot.contactstest.mainactivity;

import com.sdsmdg.harjot.contacts_lib.PhoneContactsFactory;
import com.sdsmdg.harjot.contacts_lib.interfaces.PhoneContactsFetchListener;
import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.gmail_lib.GmailContactFactory;
import com.sdsmdg.harjot.gmail_lib.interfaces.GmailContactsFetchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivitySelectionPresenter implements MainActivitySelectionContract.Presenter, PhoneContactsFetchListener, GmailContactsFetchListener {

    private String type;
    private MainActivitySelectionContract.View view;

    private GmailContactFactory gmailContactFactory;
    private PhoneContactsFactory phoneContactsFactory;

    public MainActivitySelectionPresenter(
            MainActivitySelectionContract.View view,
            GmailContactFactory gmailContactFactory,
            PhoneContactsFactory phoneContactsFactory) {
        this.view = view;
        this.gmailContactFactory = gmailContactFactory;
        this.phoneContactsFactory = phoneContactsFactory;

        this.view.setPresenter(this);
        this.gmailContactFactory.addListener(this);
        this.phoneContactsFactory.addListener(this);
    }

    @Override
    public void start(String type) {
        this.type = type;
        if (type.equals("gmail")) {
            gmailContactFactory.authorizeAndFetch();
        } else if (type.equals("phone")) {
            phoneContactsFactory.requestPermissionAndFetch();
        }
    }

    @Override
    public void onFetchStart() {
        view.showProgressDialog(type);
    }

    @Override
    public void onContactsFetchComplete(ArrayList<String> emails) {
        view.hideProgressDialog();
        Collections.sort(emails, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.toLowerCase().compareTo(s2.toLowerCase());
            }
        });
        view.showEmailsRecycler(emails, gmailContactFactory.getSelectedEmails());
    }

    @Override
    public void onFetchComplete(ArrayList<PhoneContact> phoneContactList) {
        view.hideProgressDialog();
        Collections.sort(phoneContactList, new Comparator<PhoneContact>() {
            public int compare(PhoneContact s1, PhoneContact s2) {
                return s1.getName().toLowerCase().compareTo(s2.getName().toLowerCase());
            }
        });
        view.showPhoneContactRecycler(phoneContactList, phoneContactsFactory.getSelectedPhoneContacts());
    }
}
