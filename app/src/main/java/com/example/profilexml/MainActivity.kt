package com.example.profilexml

import android.content.Intent
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.profilexml.Fragments.OfferingsFragment
import com.example.profilexml.Fragments.RecommendsFragment
import com.example.profilexml.Fragments.RequestsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Profile Settings"

        val viewPageAdapter = ViewPageAdapter(supportFragmentManager)

        viewPageAdapter.addFragment(RequestsFragment(), "Requests (0)")
        viewPageAdapter.addFragment(OfferingsFragment(), "Offerings (0)")
        viewPageAdapter.addFragment(RecommendsFragment(), "Recommends (0)")

        view_pager.adapter = viewPageAdapter
        tab_layout.setupWithViewPager(view_pager)

        otherSettings.setOnClickListener {
            val intent = startActivity(Intent(this, SettingsActivity::class.java))
        }

        fabMain.setOnClickListener {
            onFabClicked()
        }

        llAskingFor.setOnClickListener {
            Toast.makeText(this, "Asking For", Toast.LENGTH_LONG).show()
        }

        llOfferings.setOnClickListener {
            Toast.makeText(this, "Offerings", Toast.LENGTH_LONG).show()
        }

        llRecommendations.setOnClickListener {
            Toast.makeText(this, "Recommendations", Toast.LENGTH_LONG).show()
        }
    }

    private fun onFabClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            llChild.startAnimation(fromBottom)
            fabMain.startAnimation(rotateOpen)
        }
        else{
            llChild.startAnimation(toBottom)
            fabMain.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            llChild.visibility = View.VISIBLE
        }
        else{
            llChild.visibility = View.GONE
        }
    }

    inner class ViewPageAdapter(fragmentManager: FragmentManager):
        FragmentPagerAdapter(fragmentManager){

        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment, title: String){
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }

    }
}