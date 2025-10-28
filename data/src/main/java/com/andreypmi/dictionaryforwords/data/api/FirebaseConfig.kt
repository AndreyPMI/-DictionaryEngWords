package com.andreypmi.dictionaryforwords.data.api

import javax.inject.Inject

class FirebaseConfig @Inject constructor() {
    val databaseUrl: String = "https://dictionaryforwords-default-rtdb.europe-west1.firebasedatabase.app"
}
