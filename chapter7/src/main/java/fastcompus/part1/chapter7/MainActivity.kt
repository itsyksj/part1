package fastcompus.part1.chapter7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fastcompus.part1.chapter7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter

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
            Word("culture", "문화, 교양", "명사"),
            Word("experience", "경험, 체험", "명사"),
            Word("none", "없음, 없다", "명사"),
            Word("attract", "끌어당기다", "동사"),
            Word("comparison", "비교, 대조", "명사"),
        )

        //Adapter를 초기화 하고 임시데이터 넣기
        wordAdapter = WordAdapter(dummyList, this)
        mainBinding.wordList.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            //단어마다 구분선을 넣어 구분
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    //목록에서 단어를 눌렀을 경우
    override fun onClick(word: Word){
        Toast.makeText(this, "${word.englishWord} 클릭", Toast.LENGTH_SHORT).show()
    }
}