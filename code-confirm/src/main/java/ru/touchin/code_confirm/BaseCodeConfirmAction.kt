package ru.touchin.code_confirm

/**
 * [CodeConfirmAction] is interface for the action that will call
 * the confirmation request with entered code
 */
interface CodeConfirmAction

/**
 * [UpdatedCodeInputAction] is interface for the action, that should be called
 * after each update of codeInput
 * @param code Updated string with code from codeInput
 */
interface UpdatedCodeInputAction {
    val code: String?
}

/**
 * [GetRefreshCodeAction] is interface for the action that will call
 * the request of a repeat code after it's expired
 */
interface GetRefreshCodeAction
