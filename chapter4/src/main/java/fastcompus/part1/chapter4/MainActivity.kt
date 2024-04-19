package fastcompus.part1.chapter4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fastcompus.part1.chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //createButton을 누를 때 화면 이동하기 (MainActivity -> EditActivity)
        binding.createButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)

            //화면이동 시 데이터(메시지) 전달하기
            intent.putExtra("intentMessage", "EditActivity로 이동")
            startActivity(intent)
        }
    }
}