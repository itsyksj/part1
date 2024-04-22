package fastcompus.part1.chapter4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
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

        //deleteButton을 눌렀을 때 저장된 데이터 삭제하기
        binding.deleteButton.setOnClickListener {
            getDeleteData()
        }
    }

    //화면이동 발생 시 Lifecycle (EditActivity -> MainActivity)
    //- EditActivity onPayse() -> MainActivity onResume() -> EditActivity onStop() -> ...
    override fun onResume() {
        super.onResume()
        getDataAndUiUpdate()
    }

    //저장된 데이터를 전달받아 해당 화면에 표시하기
    private fun getDataAndUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.bodyName.text = getString(NAME, "Undefined")
            binding.bodyBirthday.text = getString(BIRTHDATE, "Undefined")
            binding.bodyBloodType.text = getString(BLOOD_TYPE, "Undefined")
            binding.bodyPhoneNumber.text = getString(PHONE_NUMBER, "Undefined")
            val caution = getString(CAUTION, "")

            //저장된 데이터(주의사항)가 비어있지 않다면 화면에 표시하기
            binding.titleCaution.isVisible = caution.isNullOrEmpty().not()
            binding.bodyCaution.isVisible = caution.isNullOrEmpty().not()

            //저장된 데이터(주의사항)가 비어있지 않으면 해당 주의사항으로 설정하기
            if(!caution.isNullOrEmpty()) {
                binding.bodyCaution.text = caution
            }
        }
    }

    private fun getDeleteData() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            clear()
            apply()
            getDataAndUiUpdate()
        }

        //사용자에게 삭제여부 알리기
        Toast.makeText(this, "초기화를 완료했습니다", Toast.LENGTH_SHORT).show()
    }
}