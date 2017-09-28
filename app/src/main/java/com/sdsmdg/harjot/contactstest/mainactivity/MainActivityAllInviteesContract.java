package com.sdsmdg.harjot.contactstest.mainactivity;


import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.BaseView;

import java.util.ArrayList;

public interface MainActivityAllInviteesContract {
    interface View extends BaseView<Presenter> {
        void setupViewPager(ArrayList<String> selectedEmails, ArrayList<PhoneContact> selectedPhoneContacts);
    }

    interface Presenter {
        void start();
    }
}
