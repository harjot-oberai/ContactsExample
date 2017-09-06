package com.sdsmdg.harjot.gmail_lib.interfaces;

import java.util.ArrayList;

public interface GmailContactsFetchListener {
    void onFetchStart();

    void onContactsFetchComplete(ArrayList<String> emails);
}
