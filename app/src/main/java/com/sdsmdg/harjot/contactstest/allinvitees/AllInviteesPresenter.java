package com.sdsmdg.harjot.contactstest.allinvitees;


import com.sdsmdg.harjot.contacts_lib.PhoneContactsFactory;
import com.sdsmdg.harjot.gmail_lib.GmailContactFactory;

public class AllInviteesPresenter implements AllInviteesContract.Presenter {

    private AllInviteesContract.View view;
    private GmailContactFactory gmailContactFactory;
    private PhoneContactsFactory phoneContactsFactory;

    public AllInviteesPresenter(AllInviteesContract.View view, GmailContactFactory gmailContactFactory, PhoneContactsFactory phoneContactsFactory) {
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
