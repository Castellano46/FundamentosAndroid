package com.example.fundamentosandroid.heroes.heroeslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentosandroid.databinding.FragmentListHeroesBinding
import com.example.fundamentosandroid.datafiles.Hero
import com.example.fundamentosandroid.heroes.HeroesViewModel


interface Clicked {
    fun onClick()
    fun goBack()
}
class ListHeroesFragment : Fragment(), HeroClicked {

    private lateinit var binding: FragmentListHeroesBinding
    private val viewModel: HeroesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListHeroesBinding.inflate(inflater)
        heroesListConfiguration(viewModel.list)
        configureListeners()
        return binding.root
    }

    private fun heroesListConfiguration(heroList: List<Hero>){
        viewModel.list = heroList
        val adapter = HeroesListAdapter(viewModel.list, this)
        binding.rvHeroesList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.rvHeroesList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun heroClicked(hero: Hero) {
        if(hero.hitPoints==0){
            Toast.makeText(binding.root.context, "${hero.name} está K.O. Pulsa sobre el botón para sanarlo.", Toast.LENGTH_SHORT).show()
        }else{
            viewModel.configureDetails(hero)
        }
    }
    private fun configureListeners(){
        binding.fbRecovery.setOnClickListener {
            viewModel.healingAllHeroes()
        }
    }
}