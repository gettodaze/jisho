package com.example.jishorough2;

import android.content.Context;
import android.widget.Button;

public class UIUtils {
    public static Button textButton(Context context, String text){
        Button b = new Button(context);
        b.setText(text);
        return b;
    }
}
