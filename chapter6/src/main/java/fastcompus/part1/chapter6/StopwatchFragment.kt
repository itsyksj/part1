package fastcompus.part1.chapter6

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import fastcompus.part1.chapter6.databinding.FragmentStopwatchBinding

class StopwatchFragment : Fragment() {
    private lateinit var binding: FragmentStopwatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //resetButton에 대한 이벤트 설정하기
        binding.resetButton.setOnClickListener {
            reset()
            Log.d("resetButton에", "리셋버튼 클릭됨")
        }

        //startButton에 대한 이벤트 설정하기
        binding.startButton.setOnClickListener {
            start()
            binding.startButton.isVisible = false
            binding.pauseButton.isVisible = true
            Log.d("startButton", "시작버튼 클릭됨")
        }

        //pauseButton에 대한 이벤트 설정하기
        binding.pauseButton.setOnClickListener {
            pause()
            binding.startButton.isVisible = true
            binding.pauseButton.isVisible = false
            Log.d("pauseButton", "일시정지버튼 클릭됨")
        }

        //checkButton에 대한 이벤트 설정하기
        binding.checkButton.setOnClickListener {
            lap()
            Log.d("checkButton", "lap버튼 클릭됨")
        }
    }

    private fun reset() {}
    private fun start() {}
    private fun pause() {}
    private fun lap() {}

}