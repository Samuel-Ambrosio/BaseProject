package com.samuelav.domain.model.extensions

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun Any?.isNull(): Boolean {
    contract { returns(false) implies (this@isNull != null) }
    return this == null
}

@OptIn(ExperimentalContracts::class)
fun Any?.isNotNull(): Boolean {
    contract { returns(true) implies (this@isNotNull != null) }
    return this != null
}
