package com.mapr.credibanco.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapr.credibanco.databinding.FragmentArtistsBinding
import com.mapr.credibanco.model.db.DataArtist
import com.mapr.credibanco.services.requests.RequestTopArtists
import com.mapr.credibanco.tools.Constants
import com.mapr.credibanco.tools.DialogFactory
import com.mapr.credibanco.view.adapters.AdapterArtists
import com.mapr.credibanco.view.dialogs.DetailAuthCustomDialog
import com.mapr.credibanco.viewmodel.ArtistsViewModel

class ArtistsFragment : Fragment() {

    private lateinit var artistsViewModel: ArtistsViewModel
    private var _binding: FragmentArtistsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Components
    private lateinit var progress: AlertDialog
    private lateinit var recyclerArtists: RecyclerView
    private lateinit var adapterArtists: AdapterArtists

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        context?.let {
            progress = DialogFactory().setProgress(it)
        }
        artistsViewModel =
            ViewModelProvider(this@ArtistsFragment)[ArtistsViewModel::class.java]

        // Recycler de authorization
        recyclerArtists = binding.recyclerArtists
        recyclerArtists.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerArtists.setHasFixedSize(true)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeArtistsAdapter()
        artistsViewModel.dialogMsg.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                val dialog = DialogFactory().getDialog(it, requireContext())
                dialog.show()
            }
        }

        artistsViewModel.artistsList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                //viewList.visibility = View.GONE
                //emptyMsg.visibility = View.VISIBLE
            } else if (it.isNotEmpty()) {
                //viewList.visibility = View.VISIBLE
                //emptyMsg.visibility = View.GONE
                adapterArtists.setData(it)
                adapterArtists.notifyDataSetChanged()
            }
            progress.dismiss()
        }
    }

    private fun initializeArtistsAdapter() {
        adapterArtists = AdapterArtists(
            requireContext(),
            ArrayList<DataArtist>(),
            object : AdapterArtists.OnClickDetail {
                override fun onClickDetail(item: DataArtist) {
                    val dialogDetail = DetailAuthCustomDialog(item)
                    dialogDetail.show(
                        childFragmentManager,
                        Constants.CUSTOM_DIALOG_DETAIL
                    )
                }

                override fun onClickDelete(item: DataArtist) {
                    //requestCancellation(item)
                }
            })
        recyclerArtists.adapter = adapterArtists
    }

    override fun onResume() {
        super.onResume()
        requestGetTopArtists()
    }

    /**
     *
     */
    private fun requestGetTopArtists() {
        val auth: String = Constants.API_KEY
        val request = RequestTopArtists()
        artistsViewModel.requestTopArtists(auth, "colombia", request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}