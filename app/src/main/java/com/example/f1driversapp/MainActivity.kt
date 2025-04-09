package com.example.f1driversapp


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.f1driversapp.navigation.BottomNavBar
import com.example.f1driversapp.navigation.NavItemList
import com.example.f1driversapp.screens.AddScreen
import com.example.f1driversapp.screens.HomeScreen
import com.example.f1driversapp.ui.theme.F1DriversAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            F1DriversAppTheme {
                BottomNavScreen()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavScreen(){
    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                navItemList = NavItemList.NavItemList,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    ){
        ContentScreen(selectedIndex)
    }
}

@Composable
fun ContentScreen(selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomeScreen()
        1 -> AddScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DriverAppPreview() {
    F1DriversAppTheme {
        BottomNavScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkDriverAppPreview() {
    F1DriversAppTheme(darkTheme = true) {
        BottomNavScreen()
    }
}