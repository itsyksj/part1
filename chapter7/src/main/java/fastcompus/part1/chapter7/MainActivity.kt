package fastcompus.part1.chapter7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fastcompus.part1.chapter7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), VocaAdapter.ItemClickListener {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var vocaAdapter: VocaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initRecyclerView()

        //addButton을 눌렀을 경우 AddActivity로 이동
        mainBinding.addButton.setOnClickListener {
            Intent(this, AddActivity::class.java).let {
                startActivity(it)
            }
        }
    }

    //목록 초기화 메서드
    private fun initRecyclerView() {
        //임시데이터 생성
        val dummyList = mutableListOf(
            VocaBook(0, "culture", "문화, 교양", "명사"),
            VocaBook(1, "experience", "경험, 체험", "명사"),
            VocaBook(2, "none", "없음, 없다", "명사"),
            VocaBook(3, "attract", "끌어당기다", "동사"),
            VocaBook(4, "comparison", "비교, 대조", "명사"),
        )

        //Adapter를 초기화 하고 임시데이터 넣기
        vocaAdapter = VocaAdapter(dummyList, this)
        mainBinding.vocaList.apply {
            adapter = vocaAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            //단어마다 구분선을 넣어 구분
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    //목록에서 단어를 눌렀을 경우
    override fun onClick(voca: VocaBook){
        Toast.makeText(this, "${voca.word} 클릭", Toast.LENGTH_SHORT).show()
    }
}