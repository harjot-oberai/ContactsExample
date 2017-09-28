package com.sdsmdg.harjot.contactstest.mainactivity;


import com.sdsmdg.harjot.contacts_lib.PhoneContactsFactory;
import com.sdsmdg.harjot.gmail_lib.GmailContactFactory;

public class MainActivityAllInviteesPresenter implements MainActivityAllInviteesContract.Presenter {

    private MainActivityAllInviteesContract.View view;
    private GmailContactFactory gmailContactFactory;
    private PhoneContactsFactory phoneContactsFactory;

    public MainActivityAllInviteesPresenter(MainActivityAllInviteesContract.View view, GmailContactFactory gmailContactFactory, PhoneContactsFactory phoneContactsFactory) {
        this.view = view;
        this.gmailContactFactory = gmailContactFactory;
        this.phoneContactsFactory = phoneContactsFactory;

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        gmailContactFactory.sortSelectedItems();
        phoneContactsFactory.sortSelectedItems();
        view.setupViewPager(gmailContactFactory.getSelectedEmails(), phoneContactsFactory.getSelectedPhoneContacts());
    }
}
