package com.andreypmi.dictionaryforwords

import android.util.Log
import com.andreypmi.logger.Logger
import javax.inject.Inject

class LoggerImpl @Inject constructor() : Logger {
    override fun debug(tag: String, message: String) {
        Log.d(tag,message)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag,message,throwable)
    }
}