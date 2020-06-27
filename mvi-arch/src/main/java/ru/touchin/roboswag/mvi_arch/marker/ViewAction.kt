package ru.touchin.roboswag.mvi_arch.marker

/**
 * This interface should be implemented to create your own view actions and use it with [MviFragment] and [MviViewModel].
 *
 * Usually it's sealed class with nested classes and objects representing view actions.
 *
 * Quite common cases:
 * 1. View contains simple button:
 *  object OnButtonClicked : YourViewAction()
 *
 * 2. View contains button with parameter:
 *  data class OnButtonWithParamClicked(val param: Param): YourViewAction()
 *
 * 3. View contains text input field:
 *  data class OnInputChanged(val input: String): YourViewAction()
 *
 * Exemplars of this classes used to generate new [ViewState] in [MviViewModel].
 *
 * @author Created by Max Bachinsky and Ivan Vlasov at Touch Instinct.
 */
interface ViewAction
