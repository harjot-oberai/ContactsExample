package com.sdsmdg.harjot.contactstest.selection;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdsmdg.harjot.contacts_lib.models.PhoneContact;
import com.sdsmdg.harjot.contactstest.R;

import java.util.ArrayList;

public class SelectionFragment extends Fragment implements SelectionContract.View {

    private String type;

    private SelectionContract.Presenter presenter;
    private TextView title;
    private ImageView backBtn;
    private RecyclerView selectionRecycler;

    private SelectionAdapter selectionAdapter;

    private ProgressDialog progressDialog;

    private boolean hasStarted = false;

    public static SelectionFragment getInstance() {
        return new SelectionFragment();
    }

    public SelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_selection, container, false);
        title = root.findViewById(R.id.title_text);
        backBtn = root.findViewById(R.id.back_btn);
        selectionRecycler = root.findViewById(R.id.selection_recycler);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        type = args.getString("type");

        if (type != null && type.equals("gmail")) {
            title.setText(getString(R.string.email_selection_toolbar_title));
        } else {
            title.setText(getString(R.string.phone_contact_selection_toolbar_title));
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        selectionRecycler.setLayoutManager(linearLayoutManager);
        selectionRecycler.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasStarted) {
            presenter.start(type);
            hasStarted = true;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hasStarted = false;
    }

    @Override
    public void setPresenter(SelectionContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgressDialog(String type) {
        progressDialog.setMessage(
                (type.equals("gmail"))
                        ? getString(R.string.gmail_contacts_progress_dialog_text)
                        : getString(R.string.phone_contacts_progress_dialog_text));
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showEmailsRecycler(ArrayList<String> emails, ArrayList<String> selectedEmails) {
        selectionAdapter = new SelectionAdapter(getContext(), emails, null, selectedEmails, null);
        selectionRecycler.setAdapter(selectionAdapter);
    }

    @Override
    public void showPhoneContactRecycler(ArrayList<PhoneContact> phoneContacts, ArrayList<PhoneContact> selectedPhoneContacts) {
        selectionAdapter = new SelectionAdapter(getContext(), null, phoneContacts, null, selectedPhoneContacts);
        selectionRecycler.setAdapter(selectionAdapter);
    }
}
