package com.example.petbook.views


import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.databinding.FragmentHomeBinding

import com.example.petbook.model.BaseResponse

import com.example.petbook.model.PostResponse
import com.example.petbook.viewModel.PostViewModel

import com.example.petbook.model.Post
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.SigninViewModel


import com.example.petbook.model.PostResponse
import com.example.petbook.viewModel.PostViewModel



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding:FragmentHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val binding get() = _binding!!
    private lateinit var PostList : MutableList<PostResponse>
    private val viewModel by viewModels<PostViewModel>()
    private var skip=0
    private var skipped=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


          _binding=FragmentHomeBinding.inflate(inflater, container, false)
     
        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        PostList = ArrayList()
        var recyclerViewState: Parcelable?
        viewModel.getPagination("3","0")
        var postAdapter = PostAdpater(requireView().context,PostList, viewModel)
        viewModel.list.observe(viewLifecycleOwner) {


            if (it.isNotEmpty()) {
                PostList.addAll(it)
                postAdapter = PostAdpater(requireView().context, PostList, viewModel)
                skip += it.size
                recyclerViewState = binding.PostRv.layoutManager?.onSaveInstanceState()
                postAdapter.notifyDataSetChanged()
                binding.PostRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
                binding.PostRv.adapter = postAdapter

                if (skipped) {
                    skipped = false
                }
            }
        }
        viewModel.newlist.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()) {
                PostList=it
                postAdapter = PostAdpater(requireView().context, PostList, viewModel)
                postAdapter.notifyDataSetChanged()
                binding.PostRv.adapter = postAdapter

            }
        }
        binding.PostRv.layoutManager =
            LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
        binding.PostRv.adapter = postAdapter
        binding.swiperefresh.setOnRefreshListener {
            skip=0
            viewModel.getnew()
            binding.swiperefresh.isRefreshing = false
        }
        binding.PostRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {

                    viewModel.getPagination("3",(skip).toString())


            }
        }

    })
    }
    override fun onDestroyView() {
        super.onDestroyView()
       _binding = null



    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}