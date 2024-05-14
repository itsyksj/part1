package fastcompus.part1.chapter7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.chip.Chip
import fastcompus.part1.chapter7.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private lateinit var addBinding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        initView()

        addBinding.addButton.setOnClickListener {
            add()
        }
    }

    private fun initView() {
        //Type 선언
        val types = listOf("명사", "대명사", "동사", "형용사", "부사", "접속사", "전치사", "감탄사")

        //Type를 Chip으로 설정
        addBinding.typeChip.apply {
            types.forEach { text ->
                addView(createChip(text))
            }
        }
    }

    //Type은 1개만 선택하도록 지정
    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    //abbButton을 눌렀을 때 단어 저장
    private fun add() {
        val word = addBinding.wordEdit.text.toString()
        val mean = addBinding.meanEdit.text.toString()
        val type = findViewById<Chip>(addBinding.typeChip.checkedChipId).text.toString()
        val voca = VocaBook(word, mean, type)

        //작업시간이 긴 작업(DB에 관한 작업)은 Thread에서 따로 작업진행
        Thread {
            //DB와의 상호작용을 통해 새로운 단어 추가
            AppDatabase.getInstance(this)?.VocaDao()?.insert(voca)

            //UI에 관한 작업은 runOnUiThread에서 진행
            runOnUiThread {
                Toast.makeText(this, "단어추가 완료", Toast.LENGTH_SHORT).show()
            }

            //단어가 추가한 데이터 반영시키기
            val intent = Intent().putExtra("isUpdated", true)
            setResult(RESULT_OK, intent)

            finish()
        }.start()
    }
}