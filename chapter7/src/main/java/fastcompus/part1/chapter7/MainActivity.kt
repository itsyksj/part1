package fastcompus.part1.chapter7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fastcompus.part1.chapter7.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity(), VocaAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vocaAdapter: VocaAdapter
    private var selectedVoca: VocaBook? = null

    //AddActivity에서 단어추가에 대한 결과처리
    private val updateAddVocaResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        val isUpdated = result.data?.getBooleanExtra("isUpdated", false) ?: false
        if(result.resultCode == RESULT_OK && isUpdated) {
            updateAddVoca()
        }
    }

    //AddActivity에서 단어변경에 대한 결과처리
    private val updateEditVocaResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val editVoca = result.data?.getParcelableExtra<VocaBook>("editVoca")
        if(result.resultCode == RESULT_OK && editVoca != null) {
            updateEditVoca(editVoca)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVocaList()

        binding.deleteButton.setOnClickListener {
            vocaDelete()
        }

        //updateButton를 누르면 AddActivity로 이동
        binding.updateButton.setOnClickListener {
            vocaEdit()
        }

        //addButton을 누르면 AddActivity로 이동
        binding.addButton.setOnClickListener {
            Intent(this, AddActivity::class.java).let {
                updateAddVocaResult.launch(it)
            }
        }
    }

    //단어목록 초기화
    private fun initVocaList() {
        //Adapter를 초기화하고 DB(app-database)와 연결
        vocaAdapter = VocaAdapter(mutableListOf(), this)

        binding.vocaList.apply {
            adapter = vocaAdapter

            //RecyclerView에 대한 설정(정렬방법, 구분선)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
        }

        Thread {
            val list = AppDatabase.getInstance(this)?.VocaDao()?.getAll() ?: emptyList()
            vocaAdapter.list.addAll(list)

            //recyclerView에 배열크기, 아이템이 변경된 부분만 다시 불러옴
            runOnUiThread { vocaAdapter.notifyDataSetChanged() }
        }.start()
    }

    //선택된 단어 상단표시
    override fun onClick(voca: VocaBook) {
        selectedVoca = voca
        binding.word.text = voca.word
        binding.mean.text = voca.mean
    }

    //선택된 단어 삭제
    private fun vocaDelete() {
        if(selectedVoca == null) return

        Thread {
            selectedVoca?.let { voca ->
                AppDatabase.getInstance(this)?.VocaDao()?.delete(voca)

                runOnUiThread {
                    vocaAdapter.list.remove(voca)
                    vocaAdapter.notifyDataSetChanged()
                    binding.word.text = ""
                    binding.mean.text = ""
                    Toast.makeText(this, "단어 삭제", Toast.LENGTH_SHORT).show()
                }
                selectedVoca = null
            }
        }.start()
    }

    //선택된 단어 수정
    private fun vocaEdit() {
        if(selectedVoca == null) return
        val intent = Intent(this, AddActivity::class.java).putExtra("originVoca", selectedVoca)
        updateEditVocaResult.launch(intent)
    }

    //AddActivity에서 수정된 결과를 상단에도 적용
    private fun updateEditVoca(voca: VocaBook) {
        val index = vocaAdapter.list.indexOfFirst { it.id == voca.id }
        vocaAdapter.list[index] = voca

        runOnUiThread {
            selectedVoca = voca
            vocaAdapter.notifyItemChanged(index)
            binding.word.text = voca.word
            binding.mean.text = voca.mean
        }
    }

    //AddActivity에서 수정된 결과를 단어목록에 반영
    private fun updateAddVoca() {
        Thread {
            AppDatabase.getInstance(this)?.VocaDao()?.getLastVoca()?.let { voca ->
                vocaAdapter.list.add(0, voca)

                runOnUiThread { vocaAdapter.notifyDataSetChanged() }
            }
        }.start()
    }
}

