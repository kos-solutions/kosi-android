package com.kosi.app

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kosi.app.api.KosiApi
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TTS INIT
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale("ro", "RO")
                tts.setPitch(1.15f)      // Vocea lui KOSI: ușor jucăușă
                tts.setSpeechRate(0.93f) // Ritm cald și calm
            }
        }

        setContent {
            KosiHomeScreen { text ->
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }
}

@Composable
fun KosiHomeScreen(onStoryReceived: (String) -> Unit) {
    val scope = rememberCoroutineScope()
    var story by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Avatar placeholder
        Surface(
            modifier = Modifier.size(180.dp),
            shape = CircleShape,
            color = Color(0xFFE8E8E8)
        ) {}

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            loading = true
            scope.launch {
                story = KosiApi.getStory(
                    name = "Matei",
                    age = 7,
                    prompt = "o aventură cu un dragon prietenos"
                )
                loading = false
                onStoryReceived(story)
            }
        }) {
            Text("Talk to Kosi")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = if (loading) "Kosi se gândește la o poveste..." else story,
            fontSize = 18.sp
        )
    }
}
