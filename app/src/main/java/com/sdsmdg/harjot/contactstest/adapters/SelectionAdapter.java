package com.sdsmdg.harjot.contactstest.adapters;


import android.content.Context;
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

public class SelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> gmailList;
    private ArrayList<PhoneContact> phoneList;

    public SelectionAdapter(Context context, ArrayList<String> gmailList, ArrayList<PhoneContact> phoneList) {
        this.context = context;
        this.gmailList = gmailList;
        this.phoneList = phoneList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.VIEW_TYPE_GMAIL_CONTACT: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gmail_contact_recycler_item, parent, false);
                return new GmailContactViewHolder(view);
            }
            case Constants.VIEW_TYPE_GMAIL_CONTACT_WITH_HEADER: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gmail_contact_recycler_item_with_header, parent, false);
                return new GmailContactViewHolder(view);
            }
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
        if (gmailList != null) {
            String email = gmailList.get(position);
            ((GmailContactViewHolder) holder).alphabetHeader.setText(String.valueOf(email.toUpperCase().charAt(0)));
            ((GmailContactViewHolder) holder).selectionCheckbox.setText(email);
        } else if (phoneList != null) {
            PhoneContact phoneContact = phoneList.get(position);
            ((PhoneContactViewHolder) holder).alphabetHeader.setText(String.valueOf(phoneContact.getName().toUpperCase().charAt(0)));
            ((PhoneContactViewHolder) holder).nameText.setText(phoneContact.getName());
            String phoneNumbers = phoneContact.getPhoneNumber().get(0);
            for (int i = 1; i < phoneContact.getPhoneNumber().size(); i++) {
                phoneNumbers += "\n" + phoneContact.getPhoneNumber().get(i);
            }
            ((PhoneContactViewHolder) holder).phoneNumberText.setText(phoneNumbers);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            if (gmailList != null) {
                return Constants.VIEW_TYPE_GMAIL_CONTACT_WITH_HEADER;
            } else if (phoneList != null) {
                return Constants.VIEW_TYPE_PHONE_CONTACT_WITH_HEADER;
            }
        } else if (position > 0) {
            if (gmailList != null) {
                if (gmailList.get(position).toLowerCase().charAt(0) != gmailList.get(position - 1).toLowerCase().charAt(0)) {
                    return Constants.VIEW_TYPE_GMAIL_CONTACT_WITH_HEADER;
                } else {
                    return Constants.VIEW_TYPE_GMAIL_CONTACT;
                }
            } else if (phoneList != null) {
                if (phoneList.get(position).getName().toLowerCase().charAt(0) != phoneList.get(position - 1).getName().toLowerCase().charAt(0)) {
                    return Constants.VIEW_TYPE_PHONE_CONTACT_WITH_HEADER;
                } else {
                    return Constants.VIEW_TYPE_PHONE_CONTACT;
                }
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (gmailList != null) {
            return gmailList.size();
        } else {
            return phoneList.size();
        }
    }

    private class GmailContactViewHolder extends RecyclerView.ViewHolder {

        TextView alphabetHeader;
        CheckBox selectionCheckbox;

        GmailContactViewHolder(View itemView) {
            super(itemView);
            alphabetHeader = itemView.findViewById(R.id.alphabet_header);
            selectionCheckbox = itemView.findViewById(R.id.selection_checkbox);
        }
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
        }
    }

}
