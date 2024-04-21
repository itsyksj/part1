package fastcompus.part1.chapter4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    }

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
            val caution = getString(CAUTION, "Undefined")

            //저장된 데이터(주의사항)가 비어있지 않다면 화면에 표시하기
            binding.titleCaution.isVisible = caution.isNullOrEmpty().not()
            binding.bodyCaution.isVisible = caution.isNullOrEmpty().not()

            //저장된 데이터(주의사항)가 비어있지 않으면 해당 주의사항으로 설정하기
            if(!caution.isNullOrEmpty()) {
                binding.bodyCaution.text = caution
            }
        }
    }
}