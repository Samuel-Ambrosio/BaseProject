package com.samuelav.baseproject.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.samuelav.commonandroid.extensions.setAppThemeContent
import com.samuelav.commonandroid.ui.theme.AppTheme
import com.samuelav.commonandroid.ui.theme.AppTheme.colors
import com.samuelav.commonandroid.ui.theme.AppTheme.icons
import com.samuelav.commonandroid.ui.base.CommandHandler
import org.koin.androidx.compose.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setAppThemeContent {
            val splashViewModel: SplashViewModel = get()
            val state by splashViewModel.state.collectAsState()

            CommandHandler(viewModel = splashViewModel) { command ->
                when (command) {
                    is SplashCommand.NavigateToHome -> {}
                    is SplashCommand.NavigateToOnBoarding -> {}
                }
            }

            Surface(color = colors.background) {
                when(state) {
                    is SplashState.Success -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = icons.chevronLeft.painter, contentDescription = null)
                            Greeting("Success")
                        }

                    }
                    is SplashState.Error -> {
                        Greeting("Error")
                    }
                    is SplashState.Loading -> Greeting("Loading")
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
    AppTheme {
        Greeting("Android")
    }
}