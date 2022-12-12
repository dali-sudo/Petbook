package com.example.petbook.views

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.petbook.R
import com.example.petbook.databinding.ActivityPetsProfilesBinding
import com.example.petbook.databinding.FragmentHomeBinding
import com.example.petbook.model.PetResponse
import com.example.petbook.model.PostResponse
import com.example.petbook.viewModel.petProfilesviewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageGridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageGridFragment : Fragment() {

    var petGridList = ArrayList<gridItemModel>()
    lateinit var ImgsListContainer : PetResponse
    private val viewModel by viewModels<petProfilesviewModel>()

    var descriptions = arrayOf(
        "desc",
        "desc",
        "desc",
        "desc"
    )



    var images = intArrayOf(
        R.drawable.sliderdog1,
        R.drawable.sliderdog2,
        R.drawable.sliderdog3,
        R.drawable.sliderdog4

    )


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_grid, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ImgsListContainer = PetResponse()
            viewModel.getPetImages(singlePetProfile.name)
            viewModel.singlePetImages.observe(viewLifecycleOwner) {
                ImgsListContainer = it
                println("Begin Receive")
                println(it)
                println("End Receive!!!!!!!!")

                if ( ImgsListContainer.images?.size!!>0) {

                    for (i in ImgsListContainer.images?.indices!!)
                    {
                        var imageString : String = ImgsListContainer.images!![i]
                        petGridList.add(gridItemModel(imageString))

                    }
                    println(petGridList)

                    var customAdapter =Adapter(petGridList,requireContext())
                    var gridview = view.findViewById<GridView>(R.id.petsProfileGridView)
                    gridview.adapter=customAdapter

                }
            }




     /*   gridview.setOnItemClickListener { parent, view, position, id ->

            Log.e("description", petGridList[position].description)
        }
*/




    }

    class Adapter(
        var itemModel: ArrayList<gridItemModel>,
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
                view=layoutInflater.inflate(R.layout.grid_pet_item,parent,false)
            }

    var ImageView = view?.findViewById<ImageView>(R.id.petpostImage)



            if(itemModel[position].image!=null) {
                val imageBytes = Base64.decode(itemModel[position].image, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                ImageView?.setImageBitmap(decodedImage)
            }


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
         * @return A new instance of fragment ImageGridFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageGridFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}