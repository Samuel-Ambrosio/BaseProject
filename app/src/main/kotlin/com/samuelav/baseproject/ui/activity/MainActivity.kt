package com.samuelav.baseproject.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.samuelav.baseproject.ui.theme.BaseTheme
import com.samuelav.commonandroid.CommandHandler
import org.koin.androidx.compose.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            BaseTheme {
                val splashViewModel: SplashViewModel = get()
                val state by splashViewModel.state.collectAsState()
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    when(state) {
                        is SplashState.Success -> Greeting("Success")
                        is SplashState.Error -> {
                            Greeting("Error")
                        }
                        is SplashState.Loading -> Greeting("Loading")
                    }

                    CommandHandler(viewModel = splashViewModel) { command ->
                        when(command) {
                            is SplashCommand.NavigateToHome -> {}
                            is SplashCommand.NavigateToOnBoarding -> {}
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BaseTheme {
        Greeting("Android")
    }
}