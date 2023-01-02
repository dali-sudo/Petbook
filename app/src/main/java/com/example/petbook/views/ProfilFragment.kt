package com.example.petbook.views

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingBuildInfo
import com.example.petbook.R
import com.example.petbook.databinding.ActivityProfilBinding
import com.example.petbook.databinding.FragmentHomeBinding
import com.example.petbook.repository.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var gso: GoogleSignInOptions? = null
    var gsc: GoogleSignInClient? = null
    var _binding: ActivityProfilBinding? = null
    private val binding get() = _binding!!
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
        _binding=ActivityProfilBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (SessionManager.getString(requireContext(),"profilePic") !=null )
        {
            val imageBytes = Base64.decode(SessionManager.getString(requireContext(),"profilePic"), Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.imageView.setImageBitmap(decodedImage)

        }

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken("196512096964-98hd93oa1f64t27nrttk5364j5h9h6gr.apps.googleusercontent.com").build()
        gsc = GoogleSignIn.getClient(requireContext(), gso!!)

        val acct = GoogleSignIn.getLastSignedInAccount(requireContext())
        if(acct!=null){
            binding.fullnametxt.text = acct.displayName.toString()

        }
        else{
            binding.fullnametxt.text = SessionManager.getString(requireContext(), "username")
        }
        binding.logoutLayout.setOnClickListener()
        {

            SessionManager.clearData(requireContext())
            gsc?.signOut()
            val intent = Intent(activity, signin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)


            startActivity(intent)

        }


        binding.myAccLayout.setOnClickListener()
        {
            val myIntent = Intent(activity, ProfilPosts::class.java)
            myIntent.putExtra("id", SessionManager.getString(requireContext(),"id") ) //Optional parameters
            this.startActivity(myIntent)

        }

        binding.petsButton.setOnClickListener()
        {


            val intent = Intent(activity, PetsProfiles::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)


            startActivity(intent)

        }
        binding.blogsLayout.setOnClickListener(){
            val intent = Intent(activity, GuideActivity::class.java)
            startActivity(intent)


        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}