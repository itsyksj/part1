package fastcompus.part1.chapter5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import fastcompus.part1.chapter5.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //입력값을 받기 위한 데이터
    private val firstNumber = StringBuilder("")
    private val secondNumber = StringBuilder("")
    private val operator = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //(0 ~ 9)숫자버튼을 눌렀을 때 화면에 표시하기
    fun numberClicked(view: View) {
        //버튼을 통한 숫자입력이 아니면 "" 전달하기
        val numberString = (view as? Button)?.text.toString() ?:  ""

        //연산자를 기준으로 숫자 입력하기
        //- 연산자가 없다면 첫번쨰 숫자로, 연산자가 있다면 두번째 숫자로 지정하기
        val numberText = if(operator.isEmpty()) firstNumber else secondNumber

        numberText.append(numberString)
        updateInputNumber()
        Log.d("numberClicked", "숫자 클릭")
    }

    //입력값 기본형태
    private fun updateInputNumber() {
        binding.inputNumber.text = "$firstNumber $operator $secondNumber"
    }

    //(+, -)연산자를 눌렀을 때 화면에 표시하기
    fun operatorClicked(view: View) {
        //버튼을 통한 연산자입력이 아니면 "" 전달하기
        val operatorString = (view as? Button)?.text.toString()

        //첫번째 숫자가 입력되지 않은 상태에서 연산자를 입력받는 경우
        if(firstNumber.isEmpty()) {
            Toast.makeText(this, "숫자가 입력되지 않았습니다", Toast.LENGTH_SHORT).show()
            return
        }

        //두번째 숫자가 입력된 상태에서 연산자를 입력받는 경우
        if(secondNumber.isNotEmpty()) {
            Toast.makeText(this, "더 이상 연산자를 입력할 수 없습니다", Toast.LENGTH_SHORT).show()
            return
        }

        operator.append(operatorString)
        updateInputNumber()
        Log.d("operatorClicked", "$operator 클릭")
    }

    //(=)연산자를 눌렀을 때 연산결과 출력하기
    fun equalClicked(view : View) {
        //연산결과를 출력하기 전 입력값에 대해 확인하기
        if(firstNumber.isEmpty() || secondNumber.isEmpty() || operator.isEmpty()) {
            Toast.makeText(this, "올바르지 않은 수식입니다", Toast.LENGTH_SHORT).show()
            return
        }

        //에러발생(IllegalStateException): 표현가능한 범위를 넘어선 경우
        //- toInt() 대신 toBigDecimal()를 사용해 표현범위 확장하기
        val firstNumber = firstNumber.toString().toBigDecimal()
        val secondNumber = secondNumber.toString().toBigDecimal()

        //연산하기
        val output = when(operator.toString()) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            else -> "올바르지 않은 수식입니다"
        }.toString()

        //전달받은 결과를 출력값으로 내보내기
        binding.outputNumber.text = output
        Log.d("equalClicked", "= 클릭")
    }

    //c 버튼을 눌렀을 때 입력값, 출력값 모두 지우기
    fun clearClicked(view: View) {
        firstNumber.clear()
        secondNumber.clear()
        operator.clear()

        updateInputNumber()
        binding.outputNumber.text = ""
        Log.d("clearClicked", "지우기 클릭")
    }
}