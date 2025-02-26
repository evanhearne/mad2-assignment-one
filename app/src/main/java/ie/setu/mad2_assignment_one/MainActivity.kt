package ie.setu.mad2_assignment_one

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ie.setu.mad2_assignment_one.ui.main.MainScreen
import ie.setu.mad2_assignment_one.ui.theme.Mad2assignmentoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mad2assignmentoneTheme {
                MainScreen()
            }
        }
    }
}