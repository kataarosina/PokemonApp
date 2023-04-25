package com.example.pokemon_app

import android.app.Application
import com.example.data.koin.dataModule
import com.example.pokemon_app.koin.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokemonApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PokemonApplication)
            modules(
                dataModule,
                viewModelsModule
            )
        }
    }
}