package com.example.petbook.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.petbook.R
import com.example.petbook.model.DiscoverResponse
import com.example.petbook.viewModel.DiscoverViewModel
import com.example.petbook.viewModel.PostViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverFragment : Fragment() {
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
    lateinit var List : MutableList<DiscoverResponse>
    private val viewModel by viewModels<DiscoverViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       List = ArrayList()


            viewModel.getDiscover()
        var adapter = Adapter(List, requireView().context)
        var gridview = view.findViewById<GridView>(R.id.DiscoverGridView)
        gridview.adapter=adapter
        viewModel.list.observe(viewLifecycleOwner) {
           List = it
            if(List.size%3==1)
                List.removeLast()
            if(List.size%3==2){
                List.removeLast()
                List.removeLast()}
            adapter=  Adapter(List, requireView().context)
            adapter.notifyDataSetChanged()
            gridview.adapter=adapter
        }
        var swipe= view.findViewById<SwipeRefreshLayout>(R.id.Discoverswiperefresh)
        swipe.setOnRefreshListener {
            viewModel.getDiscover()
            swipe.setRefreshing(false)
        }


        gridview.setOnItemClickListener { parent, view, position, id ->



            val intent = Intent(activity, DiscoverProfileActivity::class.java)
            intent.putExtra("id",List[position].id.toString())
            startActivity(intent)
        }

    }

    class Adapter(
        var itemModel:MutableList<DiscoverResponse>,
        var context: Context
    ) : BaseAdapter(){

        var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getCount(): Int {
            return itemModel.size
        }

        override fun getItem(position: Int): Any {
            return itemModel[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if (view == null) {
                view=layoutInflater.inflate(R.layout.layout_grid_imageview,parent,false)
            }

            var ImageView = view?.findViewById<ImageView>(R.id.DiscovergridImageView)


            ImageView?.setImageURI(Uri.parse(itemModel[position].Image!!))

            return view!!

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}