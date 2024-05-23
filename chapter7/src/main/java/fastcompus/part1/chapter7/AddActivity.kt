package fastcompus.part1.chapter7

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
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

        //단어 입력에 대한 에러
        binding.wordEdit.addTextChangedListener {
            it?.let { word ->
                binding.wordInput.error = when {
                    word.isEmpty() -> "단어를 입력해주세요"
                    word.length == 1 -> "2글자 이상으로 작성해주세요"
                    word.length > 20 -> "작성가능한 범위를 넘겼습니다"
                    else -> null
                }
            }
        }

        //뜻 입력에 대한 에러
        binding.meanEdit.addTextChangedListener {
            it?.let { mean ->
                binding.meanInput.error = if(mean.isEmpty()) "뜻을 작성해주세요" else null
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
        val typeChip = findViewById<Chip>(binding.typeChip.checkedChipId)

        //에러 발생 시 단어 추가불가
        if(binding.wordInput.error != null || word.isEmpty() || binding.meanInput.error != null || mean.isEmpty() || typeChip == null) {
            Toast.makeText(this, "정보를 제대로 작성되지 않았습니다", Toast.LENGTH_SHORT).show()
            return
        }

        val type = typeChip?.text.toString()
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

        //에러 발생 시 단어 수정불가
        if(binding.wordInput.error != null || binding.meanInput.error != null) {
            Toast.makeText(this, "정보를 제대로 작성되지 않았습니다", Toast.LENGTH_SHORT).show()
            return
        }

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
