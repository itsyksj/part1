package fastcompus.part1.chapter7

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fastcompus.part1.chapter7.databinding.ItmeVocaBinding


class VocaAdapter(val list: MutableList<VocaBook>,
                  private val itemClickListener: ItemClickListener? = null): RecyclerView.Adapter<VocaAdapter.VocaViewHolder>() {

    //Adapter 사용을 위해 Holder 선언
    class VocaViewHolder(val itemVocaBinding: ItmeVocaBinding) : RecyclerView.ViewHolder(itemVocaBinding.root) {
        //viewBinding을 통해 데이터를 View에 연결
        fun bind(voca: VocaBook) {
            itemVocaBinding.apply {
                word.text = voca.word
                mean.text = voca.mean
                type.text = voca.type
            }
        }
    }

    //ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocaViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItmeVocaBinding.inflate(inflater, parent, false)
        return VocaViewHolder(binding)
    }

    //UI와 ViewHolder의 데이터 연결
    override fun onBindViewHolder(holder: VocaViewHolder, position: Int) {
        val voca = list[position]
        holder.bind(voca)
        holder.itemView.setOnClickListener { itemClickListener?.onClick(voca) }
    }

    //Adapter에 표시되는 데이터 갯수 파악
    override fun getItemCount(): Int {
        return list.size
    }

    //해당 단어를 눌렀을 경우
    interface ItemClickListener {
        fun onClick(voca: VocaBook)
    }
}