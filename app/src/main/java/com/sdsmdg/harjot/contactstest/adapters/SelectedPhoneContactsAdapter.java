package com.sdsmdg.harjot.contactstest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.Constants;
import com.sdsmdg.harjot.contactstest.R;

import java.util.ArrayList;

public class SelectedPhoneContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PhoneContact> phoneContacts;

    public SelectedPhoneContactsAdapter(ArrayList<PhoneContact> phoneContacts) {
        this.phoneContacts = phoneContacts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.VIEW_TYPE_PHONE_CONTACT: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_contact_recycler_item, parent, false);
                return new PhoneContactViewHolder(view);
            }
            case Constants.VIEW_TYPE_PHONE_CONTACT_WITH_HEADER: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_contact_recycler_item_with_header, parent, false);
                return new PhoneContactViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhoneContact phoneContact = phoneContacts.get(position);
        ((PhoneContactViewHolder) holder).alphabetHeader.setText(String.valueOf(phoneContact.getName().toUpperCase().charAt(0)));
        ((PhoneContactViewHolder) holder).nameText.setText(phoneContact.getName());
        String phoneNumbers = phoneContact.getPhoneNumber().get(0);
//            for (int i = 1; i < phoneContact.getPhoneNumber().size(); i++) {
//                phoneNumbers += "\n" + phoneContact.getPhoneNumber().get(i);
//            }
        ((PhoneContactViewHolder) holder).phoneNumberText.setText(phoneNumbers);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constants.VIEW_TYPE_PHONE_CONTACT_WITH_HEADER;
        } else if (position > 0) {
            if (phoneContacts.get(position).getName().toLowerCase().charAt(0) != phoneContacts.get(position - 1).getName().toLowerCase().charAt(0)) {
                return Constants.VIEW_TYPE_PHONE_CONTACT_WITH_HEADER;
            } else {
                return Constants.VIEW_TYPE_PHONE_CONTACT;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return phoneContacts.size();
    }

    private class PhoneContactViewHolder extends RecyclerView.ViewHolder {

        TextView alphabetHeader, nameText, phoneNumberText;
        CheckBox selectionCheckbox;

        PhoneContactViewHolder(View itemView) {
            super(itemView);
            alphabetHeader = itemView.findViewById(R.id.alphabet_header);
            nameText = itemView.findViewById(R.id.phone_contact_text);
            phoneNumberText = itemView.findViewById(R.id.phone_contact_number);
            selectionCheckbox = itemView.findViewById(R.id.selection_checkbox);
            selectionCheckbox.setVisibility(View.GONE);
        }
    }

}
