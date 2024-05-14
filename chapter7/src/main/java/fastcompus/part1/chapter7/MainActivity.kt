package fastcompus.part1.chapter7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fastcompus.part1.chapter7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), VocaAdapter.ItemClickListener {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var vocaAdapter: VocaAdapter

    //AddActivity에서 단어가 추가된 결과 가져오기
    private val updateAddWordResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val isUpdated = result?.data?.getBooleanExtra("isUpdated", false) ?: false
        if(result.resultCode == RESULT_OK && isUpdated) {
            updateAddWord()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initRecyclerView()

        //addButton을 눌렀을 경우 AddActivity로 이동
        mainBinding.addButton.setOnClickListener {
            Intent(this, AddActivity::class.java).let {
                updateAddWordResult.launch(it)
            }
        }
    }

    //목록 초기화 메서드
    private fun initRecyclerView() {
        //Adapter를 초기화하고 DB(app-database)와 연결
        vocaAdapter = VocaAdapter(mutableListOf(), this)
        mainBinding.vocaList.apply {
            adapter = vocaAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            //단어마다 구분선을 넣어 구분
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        Thread {
            //저장된 데이터를 list로 가져오기
            val list = AppDatabase.getInstance(this)?.VocaDao()?.getAll()?: emptyList()

            //Thread 업데이트가 될 때 UI 업데이트가 안 될 수 있음 (확인을 위해 1초 뒤에 Thread 업데이트 실시)
            //Thread.sleep(1000)

            vocaAdapter.list.addAll(list)

            //데이터가 변경된 경우 Adapter를 통해 UI 변경시키기
            runOnUiThread {
                //notifyDataSetChanged : recyclerView에 포함된 모든 정보를 가져옴 (정보량에 따라 과부하 가능)
                vocaAdapter.notifyDataSetChanged()
            }
        }.start()
    }

    //목록에서 단어를 눌렀을 경우
    override fun onClick(voca: VocaBook){
        Toast.makeText(this, "${voca.word} 클릭", Toast.LENGTH_SHORT).show()
    }

    //단어를 추가한 후 UI 변경하기
    private fun updateAddWord() {
        Thread {
            //가장 마지막에 추가된 단어 가져오기
            AppDatabase.getInstance(this)?.VocaDao()?.getLastWord()?. let {
                voca ->  vocaAdapter.list.add(0, voca)

                //데이터가 변경된 것을 UI에 알림
                runOnUiThread { vocaAdapter.notifyDataSetChanged() }
            }
        }.start()
    }
}