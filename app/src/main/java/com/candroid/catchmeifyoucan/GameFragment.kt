package com.candroid.catchmeifyoucan

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.candroid.catchmeifyoucan.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Random

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var gameSettings: GameSettings
    private var ivArr: ArrayList<ImageView> = ArrayList()
    private var difficulty = 700
    private var time: Long = 15

    private var iv9: ArrayList<ImageView> = ArrayList()
    private var iv16: ArrayList<ImageView> = ArrayList()
    private var iv25: ArrayList<ImageView> = ArrayList()

    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable{}

    var counter = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        val bundle: GameFragmentArgs by navArgs()
        gameSettings = bundle.GameSettings
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(gameSettings.layout){
            "3 x 3" -> createLayoutAs3x3()
            "4 x 4" -> createLayoutAs4x4()
            "5 x 5" -> createLayoutAs5x5()
            else -> Snackbar.make(requireView(), "Something went wrong!", Snackbar.LENGTH_LONG).show()
        }

        difficulty = when (gameSettings.difficulty){
            "Low" -> 700
            "Medium" -> 500
            "High" -> 300
            else -> 700
        }

        time = if (gameSettings.time?.toLong() != null) gameSettings.time!!.toLong() else 15
        object : CountDownTimer(time*1000, 1000){
            override fun onFinish() {
                binding.tvTime.text = "Time : 0"
                handler.removeCallbacks(runnable)

                for (iv in ivArr){
                    iv.visibility = View.INVISIBLE
                }

                val alert = AlertDialog.Builder(requireContext())
                alert.setTitle("Game Over")
                alert.setMessage("Restart The Game?")
                alert.setPositiveButton("Yes") {dialog, which ->
                    //Restart
                    //Navigation.findNavController(requireView()).navigate(R.id.gameFragment)
                    Navigation.findNavController(requireView()).navigate(R.id.action_gameFragment_to_gameSettingsFragment)
                }

                alert.setNegativeButton("No") {dialog, which ->
                    Snackbar.make(requireView(),"Game Over",Snackbar.LENGTH_LONG).show()
                }

                alert.show()
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.tvTime.text = "Time : ${millisUntilFinished/1000L}"
            }
        }.start()
    }

    private fun setGridLayouts(){
        binding.grid3x3.visibility = View.INVISIBLE
    }

    private fun createLayoutAs3x3(){
        binding.grid3x3.visibility = View.VISIBLE

        iv9.add(binding.iv91)
        iv9.add(binding.iv92)
        iv9.add(binding.iv93)
        iv9.add(binding.iv94)
        iv9.add(binding.iv95)
        iv9.add(binding.iv96)
        iv9.add(binding.iv97)
        iv9.add(binding.iv98)
        iv9.add(binding.iv99)

        ivArr = iv9
        hideImages(ivArr)
    }

    private fun createLayoutAs4x4(){
        binding.grid4x4.visibility = View.VISIBLE

        iv16.add(binding.iv161)
        iv16.add(binding.iv162)
        iv16.add(binding.iv163)
        iv16.add(binding.iv164)
        iv16.add(binding.iv165)
        iv16.add(binding.iv166)
        iv16.add(binding.iv167)
        iv16.add(binding.iv168)
        iv16.add(binding.iv169)
        iv16.add(binding.iv1610)
        iv16.add(binding.iv1611)
        iv16.add(binding.iv1612)
        iv16.add(binding.iv1613)
        iv16.add(binding.iv1614)
        iv16.add(binding.iv1615)
        iv16.add(binding.iv1616)

        ivArr = iv16
        hideImages(ivArr)
    }

    private fun createLayoutAs5x5(){

    }

    private fun hideImages(ivArr: ArrayList<ImageView>){
        runnable = object : Runnable{
            override fun run() {
                for (iv in ivArr){
                    iv.visibility = View.INVISIBLE
                }

                val random = Random()
                val randomIndex = random.nextInt(9)
                ivArr[randomIndex].visibility = View.VISIBLE
                ivArr[randomIndex].setOnClickListener {
                    counter++
                    binding.tvScore.text = "Score : $counter"
                    ivArr[randomIndex].visibility = View.INVISIBLE
                }
                handler.postDelayed(runnable, difficulty.toLong())
            }
        }

        handler.post(runnable)
    }
}