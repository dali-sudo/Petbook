package com.example.petbook.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petbook.R
import com.example.petbook.databinding.ActivityChatContatcsBinding
import com.example.petbook.databinding.FragmentHomeBinding
import com.example.petbook.model.ChatContactsResponse
import com.example.petbook.repository.SessionManager
import com.example.petbook.viewModel.ChatViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatContactsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    var _binding: ActivityChatContatcsBinding? = null
    private val binding get() = _binding!!
    lateinit var list : MutableList<ChatContactsResponse>
    private val viewModel by viewModels<ChatViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=ActivityChatContatcsBinding.inflate(inflater, container, false)
        val view = binding.root






        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list=ArrayList()
        if (SessionManager.getString(requireContext(),"id") !=null ) {
            viewModel.getContatcs(SessionManager.getString(requireContext(),"id").toString())
        }
        var chatAdpater =ChatContactsAdapter(requireContext(),list)
        viewModel.Contacs.observe(viewLifecycleOwner) {
            list = it
            chatAdpater =ChatContactsAdapter(requireContext(),list)
            chatAdpater.notifyDataSetChanged()
            binding.SearchRv.adapter = chatAdpater
        }

        binding.SearchRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.SearchRv.adapter = chatAdpater


    }

        companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatContactsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatContactsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}