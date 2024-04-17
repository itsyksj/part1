package fastcompus.part1.chapter2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

//id값을 제대로 인식하지 못하는 경우 : Build -> Clean Project -> Rebuild Project
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //findViewById을 통해 UI요소 가져오기
        val numberText = findViewById<TextView>(R.id.textNumber)
        val initalizationButton = findViewById<Button>(R.id.initalizationButton)
        val plusButton = findViewById<Button>(R.id.plusButton)

        //기본값 지정하기
        var number = 0

        //setOnClickListener을 통해 버튼클릭 시 이벤트 발생시키기
        //- 초기화(Initialization) 버튼을 클릭하는 경우
        initalizationButton.setOnClickListener {
            //숫자 초기화 시키기
            number = 0

            //초기화 된 숫자 문자열로 변환하기
            numberText.text = number.toString()
            Log.d("onClick", "초기화된 숫자: $number")
        }

        //- 숫자 증가(Number Plus) 버튼을 클릭하는 경우
        plusButton.setOnClickListener {
            //숫자 증가시키기
            number += 1

            //증가된 숫자 문자열로 변환하기
            numberText.text = number.toString()
            Log.d("onClick", "현재 숫자: $number")
        }
    }
}