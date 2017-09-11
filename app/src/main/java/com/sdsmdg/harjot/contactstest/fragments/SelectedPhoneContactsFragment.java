package com.sdsmdg.harjot.contactstest.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.MainActivity;
import com.sdsmdg.harjot.contactstest.R;
import com.sdsmdg.harjot.contactstest.adapters.SelectedPhoneContactsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectedPhoneContactsFragment extends Fragment {

    RecyclerView recyclerView;
    SelectedPhoneContactsAdapter selectedPhoneContactsAdapter;
    ArrayList<PhoneContact> selectedPhoneContacts;

    public SelectedPhoneContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_phone_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedPhoneContacts = getArguments().getParcelableArrayList("selectedPhoneContacts");

        if(selectedPhoneContacts == null){
            selectedPhoneContacts = new ArrayList<>();
        }

        recyclerView = view.findViewById(R.id.selected_phone_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        selectedPhoneContactsAdapter = new SelectedPhoneContactsAdapter(selectedPhoneContacts);
        recyclerView.setAdapter(selectedPhoneContactsAdapter);
    }
}
