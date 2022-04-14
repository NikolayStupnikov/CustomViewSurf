package ru.nukolay.stupnikov.customviewsurf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.nukolay.stupnikov.customviewsurf.view.CustomView

class MainActivity : AppCompatActivity() {

    private lateinit var customView: CustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customView = findViewById(R.id.custom_view)
      //  customView.colorResArray = arrayOf(R.color.purple_500, R.color.teal_200, R.color.black, R.color.green)
        customView.colorHexArray = arrayOf("#FF6200EE", "#FF03DAC5", "#FF000000", "#3EC639")
    }
}