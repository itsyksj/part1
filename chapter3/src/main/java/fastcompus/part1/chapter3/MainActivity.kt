package fastcompus.part1.chapter3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import fastcompus.part1.chapter3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //viewBinding 선언하기
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //findViewById를 통해 UI요소 가져오기 : 해당 화면에 속하지 않은 id값을 모두 가져올 수 있음
        //화면에 속하지 않는 UI요소는 해당 화면실행 시 null값을 가지면서 NullPointerException 발생시킴
        //val ExampleTextview = findViewById<TextView>(R.id.example)

        //ViewBinding을 통해 UI요소 가져오기 (사용권장) : 해당 화면에 속한 id값만 가져옴
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //해당 화면에 속하는 id값 (사용가능)
        val inputNumberEditText = binding.inputNumber
        val inputUnitTextView = binding.inputUnit
        val outputNumberTextView = binding.outputNumber
        val outputUnitTextView = binding.outputUnit
        val swapImageButton = binding.swapButton

        //해당 화면에 속하지 않는 id값 (사용불가)
        //val exampleTextView = binding.example

        //기본값 지정
        var inputNumber: Int = 0
        var cmToM = true

        //입력값(숫자)은 화면표시를 위해 문자로 변환 후, 출력값을 위해 숫자로 재변환
        inputNumberEditText.addTextChangedListener { text ->

            //1.예외발생(NumberFormatException) : 입력값이 너무 큰 경우
            //android:maxLength="7"를 통해 입력값은 7자리 이하로 지정

            //2.예외발생(NumberFormatException) : 입력값이 공백인 경우
            //입력값을 변환하기 전 공백, null여부 확인시키기
            inputNumber = if (text.isNullOrEmpty()) {
                0
            } else {
                text.toString().toInt()
            }

            //3.예외발생(NumberFormatException) : 입력값에 소수점이 들어가는 경우
            //4.문제점 발생 : 특정 숫자를 입력할 경우 소수점이 과하게 출력됨 (ex) 556cm -> 5.5600000000000005m)
            Log.d("inputNumber", inputNumber.toString())

            //출력값 설정(cm -> m)
            if (cmToM) {
                outputNumberTextView.text = inputNumber.times(0.01).toString()
            } else {
                outputNumberTextView.text = inputNumber.times(100).toString()
            }
        }

        //swap버튼을 누른 경우
        swapImageButton.setOnClickListener {
            cmToM = cmToM.not()
            if (cmToM) {
                inputUnitTextView.text = "cm"
                outputUnitTextView.text = "m"
                outputNumberTextView.text = inputNumber.times(0.01).toString()
            } else {
                inputUnitTextView.text = "m"
                outputUnitTextView.text = "cm"
                outputNumberTextView.text = inputNumber.times(100).toString()
            }
        }
    }
}