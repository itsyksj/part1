package fastcompus.part1.chapter3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}