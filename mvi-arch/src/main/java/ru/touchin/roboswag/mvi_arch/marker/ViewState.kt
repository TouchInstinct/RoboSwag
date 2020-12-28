package ru.touchin.roboswag.mvi_arch.marker

/**
 * This interface should be implemented to create your own view state and use it with [MviFragment] and [MviViewModel].
 *
 * Usually it's a data class that presents full state of view.
 *
 * You should not use mutable values here. All values should be immutable.
 *
 * @author Created by Max Bachinsky and Ivan Vlasov at Touch Instinct.
 */
interface ViewState
