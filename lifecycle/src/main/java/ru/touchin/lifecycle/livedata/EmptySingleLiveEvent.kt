package ru.touchin.lifecycle.livedata

import androidx.annotation.MainThread

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that only one observer is going to be notified of changes.
 *
 * This version of SingleLiveEvent supports empty events
 */
class EmptySingleLiveEvent : SingleLiveEvent<Void?>() {
    @MainThread
    fun call() {
        value = null
    }

    fun postCall() {
        super.postValue(null)
    }
}
