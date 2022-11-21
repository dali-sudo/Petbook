package com.example.petbook.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petbook.databinding.FragmentSearchBinding
import com.example.petbook.model.SearchResponse
import com.example.petbook.viewModel.SearchUsersViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var _binding: FragmentSearchBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private val binding get() = _binding!!
    lateinit var SearchList : MutableList<SearchResponse>
    private val viewModel by viewModels<SearchUsersViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding=FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root






        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SearchList = ArrayList()


        val bundle = this.arguments
        val myValue = bundle!!.getString("search")
        if(myValue!=""){
        viewModel.FindUsers(myValue.toString() )}
        var searchAdpater = SearchAdapter(requireView().context,  SearchList, viewModel)
        viewModel.list.observe(viewLifecycleOwner) {
            SearchList = it
            searchAdpater =  SearchAdapter(requireView().context,  SearchList , viewModel)
            searchAdpater.notifyDataSetChanged()
            binding.SearchRv.adapter =  searchAdpater
        }

        binding.SearchRv.layoutManager =
            LinearLayoutManager(requireView().context, LinearLayoutManager.VERTICAL, false)
        binding.SearchRv.adapter = searchAdpater




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
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}