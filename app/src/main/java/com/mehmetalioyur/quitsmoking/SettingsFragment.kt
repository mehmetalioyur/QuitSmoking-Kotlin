package com.mehmetalioyur.quitsmoking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.mehmetalioyur.quitsmoking.databinding.FragmentSettingsBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.contracts.contract

class SettingsFragment : Fragment(), DatePickerDialog.OnDateSetListener ,TimePickerDialog.OnTimeSetListener {

    private lateinit var sharedPreferences: SharedPreferences



    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    private var minute = 0
    private var hour = 0
    private var day = 0
    private var month = 0
    private var year = 0

    private var savedMinute = 0
    private var savedHour = 0
    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedDailySmokedCigarette: Int? = null
    private var savedPriceOfCigarette: Float? = null
    private var savedPieceOfBox: Int? = null

    private var control = 2

    private val calendar = Calendar.getInstance()
    private val formatDate = SimpleDateFormat("dd MMMM yyyy")
    private val formatTime = SimpleDateFormat("HH:mm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedPreferences =
            requireActivity().getSharedPreferences("com.mehmetalioyur.quitsmoking", MODE_PRIVATE)

        val sPPieceOfBox = sharedPreferences.getInt("savedPieceOfBox", -1)
        val sPPriceOfCigarette = sharedPreferences.getFloat("savedPriceOfCigarette", -1f)
        val sPDailySmokedCigarette = sharedPreferences.getInt("savedDailySmokedCigarette", -1)
        val sPMinute = sharedPreferences.getInt("savedMinute", -1)
        val sPHour = sharedPreferences.getInt("savedHour", -1)
        val sPDay = sharedPreferences.getInt("savedDay", -1)
        val sPMonth = sharedPreferences.getInt("savedMonth", -1)
        val sPYear = sharedPreferences.getInt("savedYear", -1)



        arguments?.let {
            control = SettingsFragmentArgs.fromBundle(it).control
        }


        if (sPDay != -1 && sPPieceOfBox != -1 && control != 1) {
            println("diğer aktiviteye gidiom.bb")
            val action = SettingsFragmentDirections.settingsToViewPager(   )
            Navigation.findNavController(view).navigate(action)
        }
        if (control == 1){  // Diğer Fragment'ten geldiysem, kayıtlı değerleri göstermek istiyorum.

            calendar.set(sPYear, sPMonth, sPDay, sPHour, sPMinute)
            binding.quitTimeEdit.hint = formatTime.format(calendar.time)
            binding.quitDateEdit.hint = formatDate.format(calendar.time)
            binding.dailySmokedEdit.hint = sPDailySmokedCigarette.toString()
            binding.priceOfCigaretteEdit.hint =sPPriceOfCigarette.toString()
            binding.piecesInBoxEdit.hint = sPPieceOfBox.toString()
        }

        pickDate()
        pickTime()





        binding.saveButton.setOnClickListener {
            save()
        }
    }

      private fun getDateTime() {
          val cal: Calendar = Calendar.getInstance()
          day = cal[Calendar.DAY_OF_MONTH]
          month = cal[Calendar.MONTH]
          year = cal[Calendar.YEAR]
          hour = cal[Calendar.HOUR]
          minute = cal[Calendar.MINUTE]

      }


      private fun pickDate() {
          binding.quitDateEdit.setOnClickListener {

              getDateTime()
              DatePickerDialog(requireContext(), R.style.customDatePickerStyle,this, year, month, day).show()
          }
      }

      override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
          savedDay = dayOfMonth
          savedMonth = month
          savedYear = year
          calendar.set(savedYear, savedMonth, savedDay)
          binding.quitDateEdit.text = formatDate.format(calendar.time)


      }

      private fun pickTime() {

          binding.quitTimeEdit.setOnClickListener {
              getDateTime()
              TimePickerDialog(requireContext(),R.style.customTimePickerStyle, this, hour, minute, true).show()
          }
      }

      override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
          savedHour = hourOfDay
          savedMinute = minute
          calendar.set(savedYear, savedMonth, savedDay, savedHour, savedMinute)
          binding.quitTimeEdit.text = formatTime.format(calendar.time)
      }

    private fun save() {

        control = 2

        val millionSeconds = Calendar.getInstance().timeInMillis - calendar.timeInMillis
        val timeDifference = TimeUnit.MILLISECONDS.toSeconds(millionSeconds)

        if (timeDifference >= 0) {
            if (binding.dailySmokedEdit.text.toString() != "" &&
                binding.priceOfCigaretteEdit.text.toString() != "" &&
                binding.priceOfCigaretteEdit.text.toString() != "." &&
                binding.piecesInBoxEdit.text.toString() != ""
            ) {

                savedDailySmokedCigarette = binding.dailySmokedEdit.text.toString().toInt()
                savedPriceOfCigarette = binding.priceOfCigaretteEdit.text.toString().toFloat()
                savedPieceOfBox = binding.piecesInBoxEdit.text.toString().toInt()


                sharedPreferences = requireActivity().getSharedPreferences(
                    "com.mehmetalioyur.quitsmoking",
                    MODE_PRIVATE
                )
                sharedPreferences.edit().putInt("savedPieceOfBox", savedPieceOfBox!!).apply()
                sharedPreferences.edit().putFloat("savedPriceOfCigarette", savedPriceOfCigarette!!)
                    .apply()
                sharedPreferences.edit()
                    .putInt("savedDailySmokedCigarette", savedDailySmokedCigarette!!).apply()
                sharedPreferences.edit().putInt("savedMinute", savedMinute).apply()
                sharedPreferences.edit().putInt("savedHour", savedHour).apply()
                sharedPreferences.edit().putInt("savedDay", savedDay).apply()
                sharedPreferences.edit().putInt("savedMonth", savedMonth).apply()
                sharedPreferences.edit().putInt("savedYear", savedYear).apply()


                val action = SettingsFragmentDirections.settingsToViewPager(
                  /*  savedPieceOfBox!!,
                    savedPriceOfCigarette!!,
                    savedDailySmokedCigarette!!,
                    savedMinute, savedHour, savedDay, savedMonth, savedYear*/
                )
                Navigation.findNavController(requireView()).navigate(action)
            } else {
                Toast.makeText(requireContext(), "Lütfen değerleri giriniz!", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Sigarayı bırakmak için en iyi zaman şu an!!",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
