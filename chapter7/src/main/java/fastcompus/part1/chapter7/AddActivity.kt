package fastcompus.part1.chapter7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fastcompus.part1.chapter7.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var addBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)
    }
}