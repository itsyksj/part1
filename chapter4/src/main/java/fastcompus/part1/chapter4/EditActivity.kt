package fastcompus.part1.chapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //화면이동 시 전달받은 데이터(메시지) 확인하기
        val message = intent.getStringExtra("intentMessage") ?: "전달 데이터(메시지) 없음"
        Log.d("intentMessage", message)
    }
}