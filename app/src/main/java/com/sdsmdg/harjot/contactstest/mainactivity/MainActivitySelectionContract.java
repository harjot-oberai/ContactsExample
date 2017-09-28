package com.sdsmdg.harjot.contactstest.mainactivity;


import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.BasePresenter;
import com.sdsmdg.harjot.contactstest.BaseView;

import java.util.ArrayList;

public interface MainActivitySelectionContract {

    interface View extends BaseView<MainActivitySelectionContract.Presenter> {
        void showProgressDialog(String message);

        void hideProgressDialog();

        void showEmailsRecycler(ArrayList<String> emails, ArrayList<String> selectedEmails);

        void showPhoneContactRecycler(ArrayList<PhoneContact> phoneContacts, ArrayList<PhoneContact> selectedPhoneContacts);
    }

    interface Presenter extends BasePresenter {
        void selectEmail(String email);

        void deselectEmail(String email);

        void selectContact(PhoneContact phoneContact);

        void deselectContact(PhoneContact phoneContact);
    }
}
