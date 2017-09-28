package com.sdsmdg.harjot.contactstest.viewpagerfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.harjot.contactstest.R;
import com.sdsmdg.harjot.contactstest.adapters.SelectedGmailContactsAdapter;

import java.util.ArrayList;

public class SelectedGmailContactsFragment extends Fragment {

    RecyclerView recyclerView;
    SelectedGmailContactsAdapter selectedGmailContactsAdapter;
    ArrayList<String> selectedGmailContacts;


    public SelectedGmailContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_gmail_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedGmailContacts = getArguments().getStringArrayList("selectedGmailContacts");

        if (selectedGmailContacts == null) {
            selectedGmailContacts = new ArrayList<>();
        }

        recyclerView = view.findViewById(R.id.selected_gmail_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        selectedGmailContactsAdapter = new SelectedGmailContactsAdapter(selectedGmailContacts);
        recyclerView.setAdapter(selectedGmailContactsAdapter);

    }
}
