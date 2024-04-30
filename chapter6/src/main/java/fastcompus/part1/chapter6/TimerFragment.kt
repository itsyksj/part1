package fastcompus.part1.chapter6

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import fastcompus.part1.chapter6.databinding.DialogTimerSettingBinding
import fastcompus.part1.chapter6.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding

    //타이머를 위한 기본값 지정하기
    private var timerBaseSetting = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
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

        //타이머 초 설정화면 띄우기
        binding.timerText.setOnClickListener {
            timerSettingDialog()
        }
    }

    //타이머 초 설정을 위한 화면 띄우기
    private fun timerSettingDialog() {
        AlertDialog.Builder(requireContext()).apply {
            val dialogBinding = DialogTimerSettingBinding.inflate(layoutInflater)

            //타이머 설정에 대한 기본값(최소값, 최대값) 지정하기
            with(dialogBinding.timerSetting) {
                maxValue = 60
                minValue = 1
                value = timerBaseSetting
            }

            setView(dialogBinding.root)

            //확인을 눌렀을 때 초시계 값에 적용되도록 설정하기
            setPositiveButton("확인") { _, _ ->
                timerBaseSetting = dialogBinding.timerSetting.value

                //초시계의 표시될 숫자형식을 일정하게 맞춰주기 (문자 포멧팅)
                binding.timerText.text = String.format("%02d", timerBaseSetting)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun reset() {}
    private fun start() {}
    private fun pause() {}

}