package com.example.fooding.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Search")
public class Search extends ParseObject {
    public static final String SEARCH_KEY = "searchName";
    public static final String USER_KEY = "user";

    public String getSearchName() {
        return getString(SEARCH_KEY);
    }

    public void setSearchName(String searchName) {
        put(SEARCH_KEY, searchName);
    }

    public ParseUser getUser() {
        return getParseUser(USER_KEY);
    }

    public void setUser(ParseUser user) {
        put(USER_KEY, user);
    }
}

