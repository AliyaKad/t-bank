package com.example.t_bank.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.t_bank.presentation.ui.AllocatedTransactionsFragment
import com.example.t_bank.presentation.ui.UnallocatedTransactionsFragment

class TransactionPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            UnallocatedTransactionsFragment()
        } else {
            AllocatedTransactionsFragment()
        }
    }
}