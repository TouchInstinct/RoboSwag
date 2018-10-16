package ru.touchin.roboswag.components.navigation;

import android.os.Bundle;
import android.os.Parcel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Gavriil Sitnikov on 6/10/2016.
 * Wrapper over bundle to allow serialization logic for it.
 * Use it to save states of views like WebView, MapView etc.
 */
public class SerializableBundle implements Serializable {

    private static final long serialVersionUID = 0L;

    @Nullable
    private Bundle bundle;

    /**
     * Returns {@link Bundle} which should be serializable.
     *
     * @return Serializable Bundle.
     */
    @Nullable
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Sets {@link Bundle} which should be serializable.
     *
     * @param bundle Bundle to serialize.
     */
    public void setBundle(@Nullable final Bundle bundle) {
        this.bundle = bundle;
    }

    private void writeObject(@NonNull final ObjectOutputStream outputStream) throws IOException {
        if (bundle != null) {
            final Parcel parcel = Parcel.obtain();
            parcel.writeBundle(bundle);
            final byte[] bytes = parcel.marshall();
            outputStream.writeInt(bytes.length);
            outputStream.write(bytes);
            parcel.recycle();
        } else {
            outputStream.writeInt(0);
        }
    }

    private void readObject(@NonNull final ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        final int size = inputStream.readInt();
        if (size > 0) {
            final byte[] bytes = new byte[size];
            inputStream.read(bytes);
            final Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);
            bundle = parcel.readBundle(Thread.currentThread().getContextClassLoader());
            parcel.recycle();
        }
    }

}
