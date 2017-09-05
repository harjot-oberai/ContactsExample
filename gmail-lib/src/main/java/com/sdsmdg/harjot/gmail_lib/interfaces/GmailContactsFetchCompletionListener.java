package com.sdsmdg.harjot.gmail_lib.interfaces;


import com.google.api.services.people.v1.model.Person;

import java.util.List;

public interface GmailContactsFetchCompletionListener {
    void onFetchStart();
    void onContactsFetchComplete(List<String> emails);
}
