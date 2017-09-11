package com.sdsmdg.harjot.contactstest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sdsmdg.harjot.contactstest.Constants;
import com.sdsmdg.harjot.contactstest.R;

import java.util.ArrayList;

public class SelectedGmailContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> emails;

    public SelectedGmailContactsAdapter(ArrayList<String> emails) {
        this.emails = emails;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.VIEW_TYPE_GMAIL_CONTACT: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gmail_contact_recycler_item, parent, false);
                return new EmailViewHolder(view);
            }
            case Constants.VIEW_TYPE_GMAIL_CONTACT_WITH_HEADER: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gmail_contact_recycler_item_with_header, parent, false);
                return new EmailViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EmailViewHolder) holder).alphabetHeader.setText(String.valueOf(emails.get(position).toUpperCase().charAt(0)));
        ((EmailViewHolder) holder).gmailContactText.setText(emails.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constants.VIEW_TYPE_GMAIL_CONTACT_WITH_HEADER;
        } else if (position > 0) {
            if (emails.get(position).toLowerCase().charAt(0) != emails.get(position - 1).toLowerCase().charAt(0)) {
                return Constants.VIEW_TYPE_GMAIL_CONTACT_WITH_HEADER;
            } else {
                return Constants.VIEW_TYPE_GMAIL_CONTACT;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    private class EmailViewHolder extends RecyclerView.ViewHolder {

        TextView alphabetHeader, gmailContactText;
        CheckBox selectionCheckbox;

        EmailViewHolder(View itemView) {
            super(itemView);
            alphabetHeader = itemView.findViewById(R.id.alphabet_header);
            gmailContactText = itemView.findViewById(R.id.gmail_contact_text);
            selectionCheckbox = itemView.findViewById(R.id.selection_checkbox);
            selectionCheckbox.setVisibility(View.GONE);
        }
    }

}
