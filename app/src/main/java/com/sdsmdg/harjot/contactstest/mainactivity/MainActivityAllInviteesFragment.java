package com.sdsmdg.harjot.contactstest.mainactivity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.R;
import com.sdsmdg.harjot.contactstest.adapters.ViewPagerAdapter;
import com.sdsmdg.harjot.contactstest.fragments.SelectedGmailContactsFragment;
import com.sdsmdg.harjot.contactstest.fragments.SelectedPhoneContactsFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityAllInviteesFragment extends Fragment implements MainActivityAllInviteesContract.View {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ArrayList<String> selectedGmailContacts;
    private ArrayList<PhoneContact> selectedPhoneContacts;

    private MainActivityAllInviteesContract.Presenter presenter;

    public static MainActivityAllInviteesFragment getInstance() {
        return new MainActivityAllInviteesFragment();
    }

    public MainActivityAllInviteesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main_activity_all_invitees, container, false);
        toolbar = root.findViewById(R.id.toolbar);
        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tabs);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(MainActivityAllInviteesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setupViewPager(ArrayList<String> selectedEmails, ArrayList<PhoneContact> selectedPhoneContacts) {
        this.selectedGmailContacts = selectedEmails;
        this.selectedPhoneContacts = selectedPhoneContacts;
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(getFragment("gmail"), "GMAIL");
        viewPagerAdapter.addFragment(getFragment("phone"), "PHONE");
        viewPager.setAdapter(viewPagerAdapter);
    }

    public Fragment getFragment(String type) {
        if (type.equals("gmail")) {
            SelectedGmailContactsFragment selectedGmailContactsFragment = new SelectedGmailContactsFragment();
            Bundle args = new Bundle();
            args.putStringArrayList("selectedGmailContacts", selectedGmailContacts);
            selectedGmailContactsFragment.setArguments(args);
            return selectedGmailContactsFragment;
        } else if (type.equals("phone")) {
            SelectedPhoneContactsFragment selectedPhoneContactsFragment = new SelectedPhoneContactsFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList("selectedPhoneContacts", selectedPhoneContacts);
            selectedPhoneContactsFragment.setArguments(args);
            return selectedPhoneContactsFragment;
        }
        return null;
    }

}