package com.example.jishorough2;

import org.json.JSONException;

public class UnexpectedJSONException extends JSONException {
        public UnexpectedJSONException(String s, Throwable err) {
            super(s,err);
        }
    }
