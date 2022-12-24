package com.example.petbook.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petbook.databinding.FragmentHomeBinding
import com.example.petbook.model.PostResponse
import com.example.petbook.viewModel.PostViewModel
import kotlinx.coroutines.flow.merge


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

    var _binding:FragmentHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val binding get() = _binding!!
    lateinit var PostList : MutableList<PostResponse>
    private val viewModel by viewModels<PostViewModel>()
    private var skip=0
    private var skiped=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


          _binding=FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root






        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PostList = ArrayList()
         var recyclerViewState=binding.PostRv.layoutManager?.onSaveInstanceState()
        viewModel.getPagination("3","0")
        var postAdpater = PostAdpater(requireView().context,PostList, viewModel)
        viewModel.list.observe(viewLifecycleOwner) {

            if (!it.isEmpty()) {
                PostList.addAll(it)
                postAdpater = PostAdpater(requireView().context, PostList, viewModel)
                skip += it.size
                recyclerViewState = binding.PostRv.layoutManager?.onSaveInstanceState()
                postAdpater.notifyDataSetChanged()
                binding.PostRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
                binding.PostRv.adapter = postAdpater

                if (skiped) {
                    skiped = false
                }
            }
        }
        viewModel.newlist.observe(viewLifecycleOwner) {

            if (!it.isEmpty()) {
                PostList=it
                postAdpater = PostAdpater(requireView().context, PostList, viewModel)
                postAdpater.notifyDataSetChanged()
                binding.PostRv.adapter = postAdpater

            }
        }
        binding.PostRv.layoutManager =
            LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
        binding.PostRv.adapter = postAdpater
        binding.swiperefresh.setOnRefreshListener {
            viewModel.getnew()
skip=0
            binding.swiperefresh.setRefreshing(false)
        }
        binding.PostRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(!skiped)
                    viewModel.getPagination("3",(skip).toString())
                    skiped=true
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