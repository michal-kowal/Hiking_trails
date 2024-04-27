package lab.hiking_trails

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TopFragment()
            1 -> Tab1Fragment()
            2 -> Tab2Fragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.resources.getText(R.string.home_tab)
            1 -> context.resources.getText(R.string.tab1)
            2 -> context.resources.getText(R.string.tab2)
            else -> null
        }
    }
}