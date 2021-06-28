package ru.touchin.roboswag.mvi_arch.marker

/**
 * This interface should be implemented to create your own state change and use it with [MviFragment] and [MviStoreViewModel].
 *
 * Class-marker for atomic state change. State change can come from view (in response to user's action)
 * or as a SideEffect's result.
 *
 * StateChange affects current state: in [Store.reduce] pair <StateChange, ViewState> creates new ViewState and list of SideEffects.
 */

interface StateChange
