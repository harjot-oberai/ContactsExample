package com.sdsmdg.harjot.contactstest.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.Constants;
import com.sdsmdg.harjot.contactstest.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> gmailList;
    private ArrayList<PhoneContact> phoneList;

    private ArrayList<String> selectedGmailContacts;
    private ArrayList<PhoneContact> selectedPhoneContacts;

    private static final String DONT_CALL_LISTENER = "dont_call_listener";

    public SelectionAdapter(Context context,
                            ArrayList<String> gmailList,
                            ArrayList<PhoneContact> phoneList,
                            ArrayList<String> selectedGmailContacts,
                            ArrayList<PhoneContact> selectedPhoneContacts) {
        this.context = context;
        this.gmailList = gmailList;
        this.phoneList = phoneList;
        this.selectedGmailContacts = selectedGmailContacts;
        this.selectedPhoneContacts = selectedPhoneContacts;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (gmailList != null) {
            final String email = gmailList.get(position);
            ((GmailContactViewHolder) holder).alphabetHeader.setText(String.valueOf(email.toUpperCase().charAt(0)));
            ((GmailContactViewHolder) holder).selectionCheckbox.setText(email);

            ((GmailContactViewHolder) holder).selectionCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (((GmailContactViewHolder) holder).selectionCheckbox.getTag() != null) {
                        ((GmailContactViewHolder) holder).selectionCheckbox.setTag(null);
                        return;
                    }
                    if (b) {
                        selectedGmailContacts.add(email);
                        ((GmailContactViewHolder) holder).selectionCheckbox.setTag(DONT_CALL_LISTENER);
                        ((GmailContactViewHolder) holder).selectionCheckbox.setChecked(true);
                    } else {
                        selectedGmailContacts.remove(email);
                        ((GmailContactViewHolder) holder).selectionCheckbox.setTag(DONT_CALL_LISTENER);
                        ((GmailContactViewHolder) holder).selectionCheckbox.setChecked(false);
                    }
                }
            });
            if (selectedGmailContacts.contains(email)) {
                ((GmailContactViewHolder) holder).selectionCheckbox.setTag(DONT_CALL_LISTENER);
                ((GmailContactViewHolder) holder).selectionCheckbox.setChecked(true);
            } else {
                ((GmailContactViewHolder) holder).selectionCheckbox.setTag(DONT_CALL_LISTENER);
                ((GmailContactViewHolder) holder).selectionCheckbox.setChecked(false);
            }

        } else if (phoneList != null) {
            final PhoneContact phoneContact = phoneList.get(position);

            ((PhoneContactViewHolder) holder).alphabetHeader.setText(String.valueOf(phoneContact.getName().toUpperCase().charAt(0)));
            ((PhoneContactViewHolder) holder).nameText.setText(phoneContact.getName());
            String phoneNumbers = phoneContact.getPhoneNumber().get(0);
//            for (int i = 1; i < phoneContact.getPhoneNumber().size(); i++) {
//                phoneNumbers += "\n" + phoneContact.getPhoneNumber().get(i);
//            }
            ((PhoneContactViewHolder) holder).phoneNumberText.setText(phoneNumbers);

            ((PhoneContactViewHolder) holder).selectionCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (((PhoneContactViewHolder) holder).selectionCheckbox.getTag() != null) {
                        ((PhoneContactViewHolder) holder).selectionCheckbox.setTag(null);
                        return;
                    }
                    if (b) {
                        selectedPhoneContacts.add(phoneContact);
                        ((PhoneContactViewHolder) holder).selectionCheckbox.setTag(DONT_CALL_LISTENER);
                        ((PhoneContactViewHolder) holder).selectionCheckbox.setChecked(true);
                    } else {
                        selectedPhoneContacts.remove(phoneContact);
                        ((PhoneContactViewHolder) holder).selectionCheckbox.setTag(DONT_CALL_LISTENER);
                        ((PhoneContactViewHolder) holder).selectionCheckbox.setChecked(false);
                    }
                }
            });

            if (isPresent(selectedPhoneContacts, phoneContact)) {
                ((PhoneContactViewHolder) holder).selectionCheckbox.setTag(DONT_CALL_LISTENER);
                ((PhoneContactViewHolder) holder).selectionCheckbox.setChecked(true);
            } else {
                ((PhoneContactViewHolder) holder).selectionCheckbox.setTag(DONT_CALL_LISTENER);
                ((PhoneContactViewHolder) holder).selectionCheckbox.setChecked(false);
            }
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
        } else if (phoneList != null) {
            return phoneList.size();
        }
        return 0;
    }

    private class GmailContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView alphabetHeader;
        CheckBox selectionCheckbox;

        GmailContactViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            alphabetHeader = itemView.findViewById(R.id.alphabet_header);
            selectionCheckbox = itemView.findViewById(R.id.selection_checkbox);
            selectionCheckbox.setClickable(false);
        }

        @Override
        public void onClick(View view) {
            boolean isChecked = selectionCheckbox.isChecked();
            selectionCheckbox.setTag(null);
            selectionCheckbox.setChecked(!isChecked);
        }
    }

    private class PhoneContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView alphabetHeader, nameText, phoneNumberText;
        CheckBox selectionCheckbox;

        PhoneContactViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            alphabetHeader = itemView.findViewById(R.id.alphabet_header);
            nameText = itemView.findViewById(R.id.phone_contact_text);
            phoneNumberText = itemView.findViewById(R.id.phone_contact_number);
            selectionCheckbox = itemView.findViewById(R.id.selection_checkbox);
            selectionCheckbox.setClickable(false);
        }

        @Override
        public void onClick(View view) {
            boolean isChecked = selectionCheckbox.isChecked();
            selectionCheckbox.setTag(null);
            selectionCheckbox.setChecked(!isChecked);
        }
    }

    // We will have to compare `contact` with every list item as due to passing
    // a parcelable list, the object references change.
    public boolean isPresent(ArrayList<PhoneContact> list, PhoneContact contact) {
        for (PhoneContact phoneContact : list) {
            if (contact.getName().equals(phoneContact.getName())) {
                if (Arrays.deepEquals(contact.getPhoneNumber().toArray(), phoneContact.getPhoneNumber().toArray())) {
                    return true;
                }
            }
        }
        return false;
    }

}
