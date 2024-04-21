package fastcompus.part1.chapter4

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import fastcompus.part1.chapter4.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //화면이동 시 전달받은 데이터(메시지) 확인하기
        val message = intent.getStringExtra("intentMessage") ?: "전달 데이터(메시지) 없음"
        Log.d("intentMessage", message)

        //Adapter를 이용해 res/arrays/rhType에 속한 값만 선택하도록 설정하기
        binding.bodyRhType.adapter = ArrayAdapter.createFromResource(
            this, R.array.rhType, android.R.layout.simple_spinner_item
        )

        //Adapter를 이용해 res/arrays/bloodType에 속한 값만 선택하도록 설정하기
        binding.bodyBloodType.adapter = ArrayAdapter.createFromResource(
            this, R.array.bloodType, android.R.layout.simple_spinner_item
        )

        //생년월일 입력락은 달력을 사용하도록 설정하기
        binding.bodyBirthday.setOnClickListener {
            val listener = OnDateSetListener { _, year, month, datyOfMonth ->
                binding.bodyBirthday.text = "$year.${month.inc()}.$datyOfMonth"  }

            //달력 기본날짜 설정하기
            DatePickerDialog(this, listener, 2000, 0, 1).show()
        }

        //체크박스 눌렀을 때만 주의사항 작성내용이 보이도록 설정하기
        binding.cautionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.bodyCaution.isVisible = isChecked
        }

        //화면이 시작될 때부터 주의사항 작성내용을 보이지 않게 설정하기
        binding.bodyCaution.isVisible = binding.cautionCheckBox.isChecked

        //작성한 데이터 저장하고 해당 페이지 종료하기(EditActivity -> MainActivity)
        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.bodyName.text.toString())
            putString(BIRTHDATE, binding.bodyBirthday.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(PHONE_NUMBER, binding.bodyPhoneNumber.text.toString())
            putString(CAUTION, getCaution())
            apply()
        }

        //사용자에게 저장여부 알리기
        Toast.makeText(this, "입력정보가 저장되었습니다", Toast.LENGTH_SHORT).show()
    }

    //각각의 spinner를 통해 결정한 값을 묶어서 bloodType 데이터로 저장하기
    private fun getBloodType(): String {
        val rhType = if(binding.bodyRhType.selectedItemPosition == 0) "RH+" else "RH-"
        val bloodType = when(binding.bodyBloodType.selectedItemPosition) {
            0 -> "A"
            1 -> "B"
            2 -> "O"
            3 -> "AB"
            else -> {}
        }
        return "$rhType $bloodType"
    }

    //체크박스 설정여부에 맞게 주의사항 부분(titleCaution, cautionCheckBox, bocyCaution)에 대한 데이터 저장하기
    //- 체크박스를 눌렀을 경우: 주의사항 부분 모두 보이기
    //- 체크박스를 누르지 않은 경우: 주의사항 부분 모두 보이지 않기
    private fun getCaution(): String {
        return  if(binding.cautionCheckBox.isChecked) binding.bodyCaution.text.toString() else ""
    }
}

