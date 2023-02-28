package com.candroid.catchmeifyoucan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.candroid.catchmeifyoucan.databinding.FragmentGameSettingsBinding
import com.google.android.material.snackbar.Snackbar

class GameSettingsFragment : Fragment(){

    private lateinit var binding: FragmentGameSettingsBinding
    private var resId: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGameSettingsBinding.inflate(inflater, container, false)
        val bundle: GameSettingsFragmentArgs by navArgs()
        resId = bundle.resId.toInt()
        binding.time.text = ((binding.seekBarTime.progress+1)*15).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.seekBarTime.setOnSeekBarChangeListener(/* l = */ object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.time.text = ((progress+1)*15).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        binding.btnPlay.setOnClickListener {
            play()
        }
    }

    fun play(){
        val rgDifficultyId = binding.rgDifficulty.checkedRadioButtonId
        val rbDifficulty: RadioButton? = view?.findViewById(rgDifficultyId)
        val difficulty = rbDifficulty?.text?.toString()

        val rgLayoutId = binding.rgLayout.checkedRadioButtonId
        val rbLayout: RadioButton? = view?.findViewById(rgLayoutId)
        val layout = rbLayout?.text?.toString()

        val time = binding.time.text?.toString()

        val gameSettings = GameSettings(difficulty, layout, time, resId)


        if (difficulty == null){
            Snackbar.make(requireView(), "Difficulty can not be blank!", Snackbar.LENGTH_LONG).show()
        } else if (layout == null){
            Snackbar.make(requireView(), "Layout can not be blank!", Snackbar.LENGTH_LONG).show()
        } else if(time == null){
            Snackbar.make(requireView(), "Time can not be blank!", Snackbar.LENGTH_LONG).show()
        } else{
            val direction = GameSettingsFragmentDirections.actionGameSettingsFragmentToGameFragment(gameSettings)
            Navigation.findNavController(requireView()).navigate(direction)
        }

    }
}