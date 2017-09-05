package com.sdsmdg.harjot.gmail_lib.interfaces;

import java.util.List;

public interface GmailContactsFetchListener {
    void onFetchStart();

    void onContactsFetchComplete(List<String> emails);
}
