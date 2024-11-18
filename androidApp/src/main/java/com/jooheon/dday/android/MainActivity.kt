package com.jooheon.dday.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jooheon.dday.android.edit.EditScreen
import com.jooheon.dday.android.home.HomeScreen
import com.jooheon.dday.domain.ScreenNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenNavigation.Home,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    composable<ScreenNavigation.Home> {
                        HomeScreen(navController)
                    }
                    composable<ScreenNavigation.Edit> {
                        val args = it.toRoute<ScreenNavigation.Edit>()
                        EditScreen(navController, args.id)
                    }
                }
            }
        }
    }
}