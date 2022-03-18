package com.mapr.credibanco.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapr.credibanco.R
import com.mapr.credibanco.model.db.DataArtist
import com.mapr.credibanco.model.tracks.TrackOBJ
import com.mapr.credibanco.services.requests.RequestArtistTracks
import com.mapr.credibanco.services.requests.RequestTopArtists
import com.mapr.credibanco.tools.Constants
import com.mapr.credibanco.tools.DialogFactory
import com.mapr.credibanco.tools.Utils
import com.mapr.credibanco.view.adapters.AdapterArtists
import com.mapr.credibanco.view.adapters.AdapterSongs
import com.mapr.credibanco.viewmodel.ArtistsViewModel

class ArtistSongsCustomDialog(private var item: DataArtist) : DialogFragment() {

    private lateinit var artistsViewModel: ArtistsViewModel

    // Components
    private lateinit var progress: AlertDialog
    private lateinit var closeDialog: TextView
    private lateinit var recyclerSongs: RecyclerView
    private lateinit var adapterSongs: AdapterSongs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View =
            inflater.inflate(R.layout.dialog_artist_songs, container, false)

        context?.let {
            progress = DialogFactory().setProgress(it)
            progress.show()
        }
        artistsViewModel =
            ViewModelProvider(this@ArtistSongsCustomDialog)[ArtistsViewModel::class.java]


        // Recycler de songs
        recyclerSongs = rootView.findViewById(R.id.recycler_songs)
        recyclerSongs.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerSongs.setHasFixedSize(true)

        artistsViewModel.dialogMsg.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                progress.dismiss()
                val dialog = DialogFactory().getDialog(it, requireContext())
                dialog.show()
            }
        }
        artistsViewModel.tracksList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                dismiss()
                //viewList.visibility = View.GONE
                //emptyMsg.visibility = View.VISIBLE
            } else if (it.isNotEmpty()) {
                //viewList.visibility = View.VISIBLE
                //emptyMsg.visibility = View.GONE
                adapterSongs.setData(it)
                adapterSongs.notifyDataSetChanged()
            }
            progress.dismiss()
        }

        closeDialog = rootView.findViewById(R.id.click_close_dialog)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSongsAdapter()
        requestArtistTracks()

        closeDialog.setOnClickListener {
            dismiss()
        }
    }

    private fun initializeSongsAdapter() {
        adapterSongs = AdapterSongs(
            requireContext(),
            ArrayList<TrackOBJ>())
        recyclerSongs.adapter = adapterSongs
    }

    private fun requestArtistTracks() {
        val auth: String = Constants.API_KEY
        val request = RequestArtistTracks()
        artistsViewModel.requestTopTracks(auth, item.mbid, request)
    }
}