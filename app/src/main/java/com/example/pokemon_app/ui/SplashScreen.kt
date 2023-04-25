package com.example.pokemon_app.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokemon_app.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {

    private var _binding: ActivitySplashBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ActivitySplashBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            imgBackgroud.animate().translationY(-2500.0f).setDuration(1000).startDelay = 2000
            appMade.animate().translationY(2000.0f).setDuration(1000).startDelay = 2000
            animationView.animate().translationY(1500.0f).setDuration(1000).startDelay = 2000
        }
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(
                SplashScreenDirections.actionSplashScreenToListPokemonFragment())
            //findNavController().navigate(R.id.action_splashScreen_to_listPokemonFragment)
        }, 3000)
    }
}