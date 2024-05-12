package fastcompus.part1.chapter7

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fastcompus.part1.chapter7.databinding.ItmeWordBinding

class WordAdapter(private val list: MutableList<Word>,
    private val itemClickListener: ItemClickListener? = null): RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    //Adapter 사용을 위해 Holder 선언
    class WordViewHolder(val itmeWordBinding: ItmeWordBinding) : RecyclerView.ViewHolder(itmeWordBinding.root) {
        //viewBinding을 통해 데이터를 View에 연결
        fun bind(word: Word) {
            itmeWordBinding.apply {
                englishWord.text = word.englishWord
                mean.text = word.mean
                wordType.text = word.wordType
            }
        }
    }

    //ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItmeWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    //UI와 ViewHolder의 데이터 연결
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = list[position]
        holder.bind(word)
        holder.itemView.setOnClickListener { itemClickListener?.onClick(word) }
    }

    //Adapter에 표시되는 데이터 갯수 파악
    override fun getItemCount(): Int {
        return list.size
    }

    //해당 단어를 눌렀을 경우
    interface ItemClickListener {
        fun onClick(word: Word)
    }
}