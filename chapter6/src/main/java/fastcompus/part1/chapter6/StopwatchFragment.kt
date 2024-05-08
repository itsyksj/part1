package fastcompus.part1.chapter6

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import fastcompus.part1.chapter6.databinding.FragmentStopwatchBinding
import java.util.Timer
import kotlin.concurrent.timer

class StopwatchFragment : Fragment() {
    private lateinit var binding: FragmentStopwatchBinding

    //스톱워치를 위한 기본값 지정하기
    private var currentDeciSecond = 0
    private var stopwatch: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //resetButton 설정
        binding.resetButton.setOnClickListener {
            reset()
            Log.d("resetButton", "resetButton 클릭")

            //기본값으로 UI 되돌리기
            currentDeciSecond = 0
            binding.stopwatchText.text = "00:00"
            binding.stopwatchTick.text = "0"
        }

        //startButton 설정
        binding.startButton.setOnClickListener {
            start()
            binding.startButton.isVisible = false
            binding.pauseButton.isVisible = true
            Log.d("startButton", "startButton 클릭")
        }

        //pauseButton 설정
        binding.pauseButton.setOnClickListener {
            pause()
            binding.startButton.isVisible = true
            binding.pauseButton.isVisible = false
            Log.d("pauseButton", "pauseButton 클릭")
        }

        //checkButton 설정
        binding.checkButton.setOnClickListener {
            lap()
            Log.d("checkButton", "checkButton 클릭")
        }
    }

    private fun reset() {
        binding.lapList.removeAllViews()
    }

    private fun start() {
        //0.1초 숫자세기
        stopwatch = timer(initialDelay = 0, period = 100) {
            currentDeciSecond += 1

            //분, 초, 밀리초 계산하기
            val minute = currentDeciSecond.div(10) / 60
            val second = currentDeciSecond.div(10) % 60
            val deciSecond = currentDeciSecond % 10

            //초가 변경될 때마다 Main Thread를 통해 UI 변경하기
            requireActivity().runOnUiThread {
                binding.stopwatchText.text = String.format("%02d:%02d", minute, second)
                binding.stopwatchTick.text = deciSecond.toString()
            }
        }
    }

    private fun pause() {
        stopwatch?.cancel()
        stopwatch = null
    }

    private fun lap() {
        val container = binding.lapList

        //스톱워치가 실행 중인 경우에만 시간 특정하기
        if(stopwatch != null) {
            //xml이 아닌 코드를 통해 UI 작성하기
            TextView(requireContext()).apply {
                textSize = 20f
                gravity = Gravity.CENTER

                //분, 초, 밀리초 계산하기
                val minute = currentDeciSecond.div(10) / 60
                val second = currentDeciSecond.div(10) % 60
                val deciSecond = currentDeciSecond % 10
                text = "${container.childCount.inc()}. " + String.format("%02d:%02d %01d", minute, second, deciSecond)

                //기록을 추가 시 알림음을 통해 사용자에게 알리기
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                    .startTone(ToneGenerator.TONE_PROP_ACK, 100)

                setPadding(30)
            }.let { labTextView ->
                //새로 저장되면 위쪽에 추가하기
                container.addView(labTextView, 0)
            }
        }
    }
}