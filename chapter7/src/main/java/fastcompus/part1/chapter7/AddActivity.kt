package fastcompus.part1.chapter7

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.chip.Chip
import fastcompus.part1.chapter7.databinding.ActivityAddBinding


class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var originVoca: VocaBook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        binding.addButton.setOnClickListener {
            if(originVoca == null) vocaAdd() else vocaEdit()
        }
    }

    //단어정보 초기화
    private fun initView() {
        val types = listOf("명사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")
        binding.typeChip.apply {
            types.forEach { text ->
                addView(createChip(text))
            }
        }

        originVoca = intent.getParcelableExtra("originVoca")
        originVoca?.let { voca ->
            binding.addButton.text = "수정"
            binding.wordEdit.setText(voca.word)
            binding.meanEdit.setText(voca.mean)
            val selectChip = binding.typeChip.children.firstOrNull { (it as Chip).text == voca.type } as? Chip?
            selectChip?.isChecked = true
        }
    }

    //Type에 대한 설정(1개만 선택가능)
    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
    }

    //새로운 단어 추가
    private fun vocaAdd() {
        val word = binding.wordEdit.text.toString()
        val mean = binding.meanEdit.text.toString()
        val type = findViewById<Chip>(binding.typeChip.checkedChipId).text.toString()
        val voca = VocaBook(word, mean, type)

        //백그라운드 관련 작업
        Thread {
            AppDatabase.getInstance(this)?.VocaDao()?.insert(voca)

            //UI 관련 작업
            runOnUiThread { Toast.makeText(this, "단어 저장완료", Toast.LENGTH_SHORT).show() }

            val intent = Intent().putExtra("isUpdated", true)
            setResult(RESULT_OK, intent)
            finish()
        }.start()
    }

    //기존 단어 변경
    private fun vocaEdit() {
        val word = binding.wordEdit.text.toString()
        val mean = binding.meanEdit.text.toString()
        val type = findViewById<Chip>(binding.typeChip.checkedChipId).text.toString()
        val editVoca = originVoca?.copy(word = word, mean = mean, type = type)

        Thread {
            editVoca?.let { voca ->
                AppDatabase.getInstance(this)?.VocaDao()?.update(voca)

                val intent = Intent().putExtra("editVoca", editVoca)
                setResult(RESULT_OK, intent)

                runOnUiThread { Toast.makeText(this, "단어 수정완료", Toast.LENGTH_SHORT).show() }
                finish()
            }
        }.start()
    }
}
