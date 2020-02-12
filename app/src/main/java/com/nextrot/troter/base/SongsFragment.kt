package com.nextrot.troter.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.nextrot.troter.TroterViewModel
import com.nextrot.troter.data.Item
import com.nextrot.troter.databinding.SongsFragmentBinding
import com.nextrot.troter.songs.list.SongsListAdapter
import org.koin.android.ext.android.inject

abstract class SongsFragment : Fragment() {
    protected val troterViewModel: TroterViewModel by inject()
    protected lateinit var binding: SongsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SongsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        troterViewModel.selectedItems.observe(this.viewLifecycleOwner, Observer {
            // 옵저빙 해서 notify 함으로써 얻는 성능 저하는 감수해야함... 편할려고 ㅠ
            binding.list.adapter?.notifyDataSetChanged()
        })

        binding.list.adapter = SongsListAdapter(troterViewModel, this)
    }

    fun onClickItem(item: Item) {
        troterViewModel.toggleSelectedItem(item)
    }
}