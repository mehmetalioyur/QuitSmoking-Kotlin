package com.mehmetalioyur.quitsmoking.viewpager

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayoutMediator
import com.mehmetalioyur.quitsmoking.InformationFragment
import com.mehmetalioyur.quitsmoking.R
import com.mehmetalioyur.quitsmoking.SettingsFragmentDirections
import com.mehmetalioyur.quitsmoking.SuccessesFragment
import com.mehmetalioyur.quitsmoking.databinding.FragmentSettingsBinding
import com.mehmetalioyur.quitsmoking.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true);

        val fragmentList = arrayListOf(
            InformationFragment(),
            SuccessesFragment()
        )

        val adapter = ViewPagerAdapter(fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle)
        binding.viewPager.adapter =adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
           if(position == 0)
           {
               tab.text = "Özet"
           }
            if(position == 1)
            {
                tab.text = "Sağlık"
            }
        }.attach()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.settings_menu, menu)

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsMenu) {
            val action = ViewPagerFragmentDirections.viewPagerToSettings(1)
            Navigation.findNavController(requireView()).navigate(action)
        }

        return super.onOptionsItemSelected(item)
    }


}