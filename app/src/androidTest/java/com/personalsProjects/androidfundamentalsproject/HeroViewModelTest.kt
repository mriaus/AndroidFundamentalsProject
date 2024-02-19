package com.personalsProjects.androidfundamentalsproject

import com.personalsProjects.androidfundamentalsproject.data.repository.models.Hero
import com.personalsProjects.androidfundamentalsproject.ui.viewmodels.HeroesViewModel
import org.junit.Test

class HeroViewModelTest {
    private val viewModel = HeroesViewModel()

    //Not secure about a random function test its fine
    @Test
    fun testDamageInRange(){
        viewModel.heroList = mutableListOf(Hero("12134","","Test","Test", 100, 100))
        viewModel.selectHero(viewModel.heroList[0])
        viewModel.receiveDmg()

        assert(viewModel.heroList.filter { it.isSelected }[0].currentHealth <= 90)
        assert(viewModel.heroList.filter { it.isSelected }[0].currentHealth >= 40 )
    }


    @Test
    fun testHealHero(){
        viewModel.heroList = mutableListOf(Hero("12134","","Test","Test", 30, 100))
        viewModel.selectHero(viewModel.heroList[0])

        viewModel.heal()
        assert(viewModel.heroList.filter { it.isSelected }[0].currentHealth <= 100)
        assert(viewModel.heroList.filter { it.isSelected }[0].currentHealth == 50)
    }

    @Test
    fun testHealAll(){
        viewModel.heroList = mutableListOf(
            Hero("12134","","Test","Test", 30, 100),
            Hero("12145","","Test1","Test1", 10, 150),
            Hero("12167","","Test2","Test2", 50, 200))

        viewModel.healAll()

        assert(viewModel.heroList[0].currentHealth == 100)
        assert(viewModel.heroList[1].currentHealth == 150)
        assert(viewModel.heroList[2].currentHealth == 200)


    }

}