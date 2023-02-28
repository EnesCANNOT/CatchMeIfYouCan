package com.candroid.catchmeifyoucan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.candroid.catchmeifyoucan.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private var currentIndex = 0
    private var currentResId: Int? = null
    val listOfResId = mutableListOf(R.drawable.android_1, R.drawable.android_2, R.drawable.android_3, R.drawable.android_4)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonVisibility()

        binding.ivBefore.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--;
                binding.ivCharacter.setImageResource(listOfResId[currentIndex]);
                setButtonVisibility()
            }
        }

        binding.ivNext.setOnClickListener {
            if (currentIndex < listOfResId.size - 1) {
                currentIndex++;
                binding.ivCharacter.setImageResource(listOfResId[currentIndex]);
                setButtonVisibility()
            }
        }

        binding.btnPlay.setOnClickListener {
            val direction = MainFragmentDirections.actionMainFragmentToGameSettingsFragment(listOfResId[currentIndex].toLong())
            Navigation.findNavController(it).navigate(direction)
        }
    }

    private fun setButtonVisibility(){
        if (currentIndex > 0){
            binding.ivBefore.visibility = View.VISIBLE
            binding.ivNext.visibility = View.VISIBLE
            if (currentIndex == listOfResId.size-1){
                binding.ivNext.visibility = View.INVISIBLE
            }

        } else{
            binding.ivBefore.visibility = View.INVISIBLE
        }
    }
}