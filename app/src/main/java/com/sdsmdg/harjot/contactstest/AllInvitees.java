package com.sdsmdg.harjot.contactstest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.adapters.ViewPagerAdapter;
import com.sdsmdg.harjot.contactstest.fragments.SelectedGmailContactsFragment;
import com.sdsmdg.harjot.contactstest.fragments.SelectedPhoneContactsFragment;

import java.util.ArrayList;

public class AllInvitees extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    ArrayList<String> selectedGmailContacts;
    ArrayList<PhoneContact> selectedPhoneContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_invitees);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("selectedGmailContacts")) {
            selectedGmailContacts = getIntent().getStringArrayListExtra("selectedGmailContacts");
        } else {
            selectedGmailContacts = new ArrayList<>();
        }

        if (getIntent().hasExtra("selectedPhoneContacts")) {
            selectedPhoneContacts = getIntent().getParcelableArrayListExtra("selectedPhoneContacts");
        } else {
            selectedPhoneContacts = new ArrayList<>();
        }

        viewPager = findViewById(R.id.view_pager);
        setupViewPager();

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(getFragment("gmail"), "GMAIL");
        viewPagerAdapter.addFragment(getFragment("phone"), "PHONE");
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
