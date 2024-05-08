package fastcompus.part1.chapter6

import android.app.AlertDialog
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
    private var currentSecond = 0;
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
            Log.d("resetButton", "resetButton 클릭")

            //기본값으로 UI 되돌리기
            currentSecond = 0
            binding.timerText.text = "60"
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
            tiemrSetting()
            Log.d("timerText", "timerText 클릭해 초기값 설정")
        }
    }

    private fun reset() {
        binding.timerProgressBar.progress = 0
    }

    private fun start() {
        //초시계에 대한 초기값 설정하기
        currentSecond = timerBaseSecond

        timer = timer(initialDelay = 1000,  period = 1000) {
            currentSecond -= 1

            //초시계 초기값에 따른 프로그래스바 설정하기
            val progress = ((timerBaseSecond - currentSecond).toFloat() / timerBaseSecond.toFloat() * 100).toInt()
            Log.d("progress", progress.toString())

            //초가 변경될 때마다 Main Thread를 통해 UI 변경하기
            requireActivity().runOnUiThread {
                binding.timerText.text = String.format("%02d", currentSecond)

                //초시계의 값이 0이 되는 경우 설정 변경하기 (초기화 되는 경우도 포함)
                if (currentSecond <= -1) {
                    pause()
                    binding.startButton.isVisible = true
                    binding.pauseButton.isVisible = false
                    binding.timerText.text = String.format("%02d", timerBaseSecond)
                    Toast.makeText(requireContext(), "Timer가 종료되었습니다", Toast.LENGTH_SHORT).show()
                }

                //초가 줄어들 때마다 프로그래스바 변경하기
                //TODO(프로그래스바에 애니메이션을 적용시켜 부드럽게 변경시키기)
                binding.timerProgressBar.progress = progress
            }
        }
    }

    private fun pause() {
        timer?.cancel()
        timer = null
    }

    private fun tiemrSetting() {
        //초시계가 실행 중인 경우 초기값 변경불가
        if(currentSecond == 0) {
            AlertDialog.Builder(requireContext()).apply {
                val dialogBinding = DialogTimerSettingBinding.inflate(layoutInflater)

                //초시계 초기값은 지정된 범위 안(최소값 ~ 최대값 사이)으로 설정
                with(dialogBinding.timerSetting) {
                    maxValue = 60
                    minValue = 1
                    value = timerBaseSecond
                }
                setView(dialogBinding.root)

                //확인을 누르는 경우 초기값 지정하기 (취소를 누르는 경우 null값으로 지정)
                setPositiveButton("확인") { _, _ ->
                    timerBaseSecond = dialogBinding.timerSetting.value
                    binding.timerText.text = String.format("%02d", timerBaseSecond)
                }
                setNegativeButton("취소", null)
            }.show()
        } else {
            Toast.makeText(requireContext(), "timer 설정을 변경할 수 없습니다", Toast.LENGTH_SHORT).show()
        }
    }
}