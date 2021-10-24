package com.mehmetalioyur.quitsmoking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mehmetalioyur.quitsmoking.databinding.FragmentSuccessesBinding
import java.util.*
import java.util.concurrent.TimeUnit


class SuccessesFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentSuccessesBinding? = null
    private val binding get() = _binding!!
    val calendar = Calendar.getInstance()



    var handler = Handler(Looper.getMainLooper())
    var runnable = kotlinx.coroutines.Runnable { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuccessesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val savedDate = setCalender()

        runnable = Runnable {
            val millionSeconds = Calendar.getInstance().timeInMillis - savedDate!!.timeInMillis
            val timeDifference = TimeUnit.MILLISECONDS.toSeconds(millionSeconds)

            val progressOne = (timeDifference * 100 / (1200)).toInt()
            val progressTwo = (timeDifference * 100 / (28800)).toInt()
            val progressThree = (timeDifference * 100 / (86400)).toInt()
            val progressFour = (timeDifference * 100 / (86400 * 2)).toInt()
            val progressFive = (timeDifference * 100 / (3628800)).toInt()
            val progressSix = (timeDifference * 100 / (15552000)).toInt()
            val progressSeven = (timeDifference * 100 / (31536000)).toInt()

            binding.firstProgress.progress = progressOne
            binding.secondProgress.progress = progressTwo
            binding.thirdProgress.progress = progressThree
            binding.fourthProgress.progress = progressFour
            binding.fifthProgress.progress = progressFive
            binding.sixthProgress.progress = progressSix
            binding.seventhProgress.progress = progressSeven

            val progressList = arrayOf(
                progressOne,
                progressTwo,
                progressThree,
                progressFour,
                progressFive,
                progressSix,
                progressSeven
            )

            val texts = arrayOf(
                binding.firstText,
                binding.secondText,
                binding.thirdText,
                binding.fourthText,
                binding.fifthText,
                binding.sixthText,
                binding.seventhText
            )

            for (i in progressList.indices) {

                if (progressList[i] < 100) {
                    texts[i].text = "${progressList[i]}%"
                } else {
                    println("girdim")
                    texts[i].text = "100%"
                }
            }


            handler.postDelayed(runnable, 1000)
        }
        handler.post(runnable)


    }

    fun setCalender(): Calendar? {

        sharedPreferences = requireActivity().getSharedPreferences(
            "com.mehmetalioyur.quitsmoking",
            Context.MODE_PRIVATE
        )

        val sPMinute = sharedPreferences.getInt("savedMinute", -1)
        val sPHour = sharedPreferences.getInt("savedHour", -1)
        val sPDay = sharedPreferences.getInt("savedDay", -1)
        val sPMonth = sharedPreferences.getInt("savedMonth", -1)
        val sPYear = sharedPreferences.getInt("savedYear", -1)

        calendar.set(
            sPYear,
            sPMonth,
            sPDay,
            sPHour,
            sPMinute
        )

        return calendar
    }





override fun onDestroyView() {

    super.onDestroyView()
    handler.removeCallbacks(runnable)
    _binding = null

}
}