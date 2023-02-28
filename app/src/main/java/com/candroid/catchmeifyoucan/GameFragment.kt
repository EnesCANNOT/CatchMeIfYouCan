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
import com.candroid.catchmeifyoucan.Utils.createLayout
import com.candroid.catchmeifyoucan.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Random

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var gameSettings: GameSettings
    private var ivArr: ArrayList<ImageView> = ArrayList()
    private var difficulty = 700
    private var time: Long = 15
    private var resId: Int? = null

    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable{}

    var counter = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        val bundle: GameFragmentArgs by navArgs()
        gameSettings = bundle.GameSettings
        resId = gameSettings.resId
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(gameSettings.layout){
            "3 x 3" -> {
                ivArr = createLayout(layout = "3 x 3", context = requireContext(), binding = binding, resId = resId)
                hideImages(ivArr)
            }
            "4 x 4" -> {
                ivArr = createLayout(layout = "4 x 4", context = requireContext(), binding = binding, resId = resId)
                hideImages(ivArr)
            }
            "5 x 5" -> {
                ivArr = createLayout(layout = "5 x 5", context = requireContext(), binding = binding, resId = resId)
                hideImages(ivArr)
            }
            else -> {
                ivArr = createLayout(layout = "3 x 3", context = requireContext(), binding = binding, resId = resId)
                hideImages(ivArr)
            }
        }

        difficulty = when (gameSettings.difficulty){
            "Low" -> 700
            "Easy" -> 600
            "Medium" -> 500
            "Hard" -> 400
            "Very Hard" -> 300
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
                    Navigation.findNavController(requireView()).navigate(R.id.action_gameFragment_to_mainFragment)
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

    private fun hideImages(ivArr: ArrayList<ImageView>){
        runnable = object : Runnable{
            override fun run() {
                for (iv in ivArr){
                    iv.visibility = View.INVISIBLE
                }

                val random = Random()
                val randomIndex = random.nextInt(ivArr.size)
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