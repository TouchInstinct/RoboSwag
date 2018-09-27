package ru.touchin.defaults;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;

public class DefaultTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(@NonNull final CharSequence oldText, final int start, final int count, final int after) {
        // Do nothing
    }

    @Override
    public void onTextChanged(@NonNull final CharSequence inputText, final int start, final int before, final int count) {
        // Do nothing
    }

    @Override
    public void afterTextChanged(@NonNull final Editable editable) {
        // Do nothing
    }

}
