package ru.touchin.roboswag.components.utils.spans;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

/**
 * Created by Gavriil Sitnikov on 14/11/2015.
 * Span that is opening phone call intent.
 */
public class PhoneSpan extends URLSpan {

    public PhoneSpan(@NonNull final String phoneNumber) {
        super(phoneNumber);
    }

    @Override
    public void onClick(@NonNull final View widget) {
        super.onClick(widget);
        try {
            final Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(getURL()));
            widget.getContext().startActivity(intent);
        } catch (final ActivityNotFoundException exception) {
            // Do nothing
        }
    }

    @Override
    public void updateDrawState(@NonNull final TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }

}
