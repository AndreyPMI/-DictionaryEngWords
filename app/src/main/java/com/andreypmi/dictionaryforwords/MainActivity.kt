package com.andreypmi.dictionaryforwords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryForWordsTheme
import com.andreypmi.dictionaryforwords.presentation.DictionaryNavHost
import com.andreypmi.dictionaryforwords.presentation.features.words.WordsViewModel

class MainActivity : ComponentActivity() {

    private val vm by viewModels<WordsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryNavApp()
        }
    }
}

@Composable
fun DictionaryNavApp() {
    DictionaryForWordsTheme {
        DictionaryNavHost()
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DictionaryForWordsTheme {

    }
}