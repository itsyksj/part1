package fastcompus.part1.chapter7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val text = addBinding.wordEdit.text.toString()
        val mean = addBinding.meanEdit.text.toString()
        val type = findViewById<Chip>(addBinding.typeChip.checkedChipId)
    }
}