package com.foobles.kotlinnum.utils

inline fun <T : Any> T?.doExecution(f: (it: T) -> Unit) {
    if (this != null) f(this)
}