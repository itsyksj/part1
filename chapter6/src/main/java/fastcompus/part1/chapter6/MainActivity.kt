package fastcompus.part1.chapter6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fastcompus.part1.chapter6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //앱 실행 시에 처음으로 보여질 홈화면(stopwatch) 설정하기
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, StopwatchFragment()).commit()
        }

        setBottomNavigationView()
    }

    //네비게이션바에 아이콘을 클릭할 경우 해당 화면으로 이동하기
    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_stopwatch -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, StopwatchFragment()).commit()
                    true
                }
                R.id.fragment_timer -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, TimerFragment()).commit()
                    true
                }
                else -> false
            }
        }

    }
}