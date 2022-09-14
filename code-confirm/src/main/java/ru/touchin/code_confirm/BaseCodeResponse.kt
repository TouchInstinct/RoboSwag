package ru.touchin.code_confirm

abstract class BaseCodeResponse(
        open val codeLifetime: Int,
        open val codeId: String? = null
)
