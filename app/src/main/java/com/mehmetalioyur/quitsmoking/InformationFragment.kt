package com.mehmetalioyur.quitsmoking

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mehmetalioyur.quitsmoking.databinding.FragmentInformationsBinding
import kotlinx.coroutines.Runnable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class InformationFragment : Fragment() {
    private var _binding: FragmentInformationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable { }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInformationsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*   arguments?.let {
               val savedMinute = InformationFragmentArgs.fromBundle(it).savedMinute
               val savedHour = InformationFragmentArgs.fromBundle(it).savedHour
               val savedDay = InformationFragmentArgs.fromBundle(it).savedDay
               val savedMonth = InformationFragmentArgs.fromBundle(it).savedMonth
               val savedYear = InformationFragmentArgs.fromBundle(it).savedYear

               val dailySmokedCigarette = InformationFragmentArgs.fromBundle(it).dailySmokedCigarette
               val priceOfCigarette = InformationFragmentArgs.fromBundle(it).priceOfCigarette
               val piecesInABox = InformationFragmentArgs.fromBundle(it).piecesInABox*/

        sharedPreferences =
            requireActivity().getSharedPreferences(
                "com.mehmetalioyur.quitsmoking",
                Context.MODE_PRIVATE
            )

        val sPPieceOfBox = sharedPreferences.getInt("savedPieceOfBox", -1)
        val sPPriceOfCigarette = sharedPreferences.getFloat("savedPriceOfCigarette", -1f)
        val sPDailySmokedCigarette = sharedPreferences.getInt("savedDailySmokedCigarette", -1)
        val sPMinute = sharedPreferences.getInt("savedMinute", -1)
        val sPHour = sharedPreferences.getInt("savedHour", -1)
        val sPDay = sharedPreferences.getInt("savedDay", -1)
        val sPMonth = sharedPreferences.getInt("savedMonth", -1)
        val sPYear = sharedPreferences.getInt("savedYear", -1)

        val cal = Calendar.getInstance()
        cal.set(
            sPYear,
            sPMonth,
            sPDay,
            sPHour,
            sPMinute, 0
        )

        val formatDate = SimpleDateFormat("dd MMMM yyyy  -  HH:mm")

        binding.savedDate.text = formatDate.format(cal.time)



        runnable = object : Runnable {
            override fun run() {
                val millionSeconds = Calendar.getInstance().timeInMillis - cal.timeInMillis
                val timeDifference = TimeUnit.MILLISECONDS.toSeconds(millionSeconds)
                val day = timeDifference / 86400
                val hour = (timeDifference % 86400) / 3600
                val minute = ((timeDifference % 86400) % 3600) / 60
                val second = timeDifference % 86400 % 3600 % 60

                binding.dayText.text = day.toString()
                binding.hourText.text = hour.toString()
                binding.minuteText.text = minute.toString()
                binding.secondText.text = second.toString()


                val savedMoney =
                    (sPPriceOfCigarette / sPPieceOfBox) * sPDailySmokedCigarette * timeDifference / 86400
                binding.savedMoneyText.text = savedMoney.toString()


                val nonSmokingCigarette =  sPDailySmokedCigarette * timeDifference / (24 * 60 * 60)
                binding.nonSmokingCigaretteText.text =
                    nonSmokingCigarette.toString()


                handler.postDelayed(runnable, 1000)

            }

        }
        handler.post(runnable)


    }




    override fun onDestroyView() {

        super.onDestroyView()
        handler.removeCallbacks(runnable)
        _binding = null

    }


}