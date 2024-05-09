package fastcompus.part1.chapter6

import android.app.AlertDialog
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import fastcompus.part1.chapter6.databinding.DialogTimerSettingBinding
import fastcompus.part1.chapter6.databinding.FragmentTimerBinding
import java.util.Timer
import kotlin.concurrent.timer

class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding

    //타이머를 위한 기본값 지정하기
    private var timerBaseSecond = 60
    private var currentSecond = 0
    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //resetButton 설정 (UI도 기본값으로 돌리기)
        binding.resetButton.setOnClickListener {
            reset()
            Log.d("resetButton", "resetButton 클릭됨")
        }

        //startButton 설정
        binding.startButton.setOnClickListener {
            start()
            binding.startButton.isVisible = false
            binding.pauseButton.isVisible = true
            Log.d("startButton", "startButton 클릭됨")
        }

        //pauseButton 설정
        binding.pauseButton.setOnClickListener {
            pause()
            binding.startButton.isVisible = true
            binding.pauseButton.isVisible = false
            Log.d("pauseButton", "pauseButton 클릭")
        }

        //타이머 초기값 설정
        binding.timerText.setOnClickListener {
            timerInitSetting()
            Log.d("timerText", "timerText 클릭해 초기값 설정")
        }
    }

    private fun reset() {
        currentSecond = 0
        binding.timerProgressBar.progress = 0
        binding.timerText.text = timerBaseSecond.toString()
    }

    private fun start() {
        //초시계 현재값이 지정되지 않았으면 초기값을, 지정되어 있다면 기존값을 사용
        currentSecond = if (currentSecond == 0) timerBaseSecond else currentSecond

        timer = timer(initialDelay = 1000, period = 1000) {
            currentSecond -= 1

            //프로그래스바 설정
            val progress = ((timerBaseSecond - currentSecond).toFloat() / timerBaseSecond.toFloat() * 100).toInt()

            //UI 업데이트
            requireActivity().runOnUiThread {
                binding.timerText.text = String.format("%02d", currentSecond)
                binding.timerProgressBar.progress = progress
                Log.d("text", binding.timerText.text.toString())
                Log.d("progress", progress.toString())

                //초시계 현재값이 0 이하인 경우
                if(currentSecond <= 0) {
                    pause()
                    binding.startButton.isVisible = true
                    binding.pauseButton.isVisible = false

                    //초시계가 종료되면 현재값을 초기값으로 변경
                    binding.timerText.text = String.format("%02d", timerBaseSecond)

                    //초시계 종료메시지 띄우기
                    Toast.makeText(requireContext(), "Timer가 종료되었습니다", Toast.LENGTH_SHORT).show()

                    //종료 알림음 보내기
                    ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                        .startTone(ToneGenerator.TONE_CDMA_ALERT_AUTOREDIAL_LITE, 1000)
                }
            }
        }
    }

    private fun pause() {
        timer?.cancel()
        timer = null
        Log.d("pause", timer.toString())
    }

    private fun timerInitSetting() {
        //타이머 초기값 지정을 위한 바인딩
        val dialogBinding = DialogTimerSettingBinding.inflate(layoutInflater)

        if(currentSecond == 0) {
            //초기값은 최소값 ~ 최대값 사이에서 지정
            AlertDialog.Builder(requireContext()).apply {
                with(dialogBinding.timerSetting) {
                    maxValue = 60
                    minValue = 1
                    value = timerBaseSecond
                }
                setView(dialogBinding.root)

                //확인을 눌렀다면 설정갑을 초기값으로 지정
                setPositiveButton("확인") { _, _ ->
                    timerBaseSecond = dialogBinding.timerSetting.value
                    binding.timerText.text = String.format("%02d", timerBaseSecond)
                }

                //취소를 눌렀다면 초기값 없이 null값 지정
                setNegativeButton("취소", null)
            }.show()
        } else {
            Toast.makeText(requireContext(), "timer 설정을 변경할 수 없습니다", Toast.LENGTH_SHORT).show()
        }
    }
}