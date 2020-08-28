package ru.touchin.roboswag.utils.spans

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View

/**
 * Created by Gavriil Sitnikov on 14/11/2015.
 * Span that is opening phone call intent.
 */
class PhoneSpan(phoneNumber: String) : URLSpanWithoutUnderline(phoneNumber) {

    override fun onClick(widget: View) {
        super.onClick(widget)
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(url)
            widget.context.startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            // Do nothing
        }
    }

}
