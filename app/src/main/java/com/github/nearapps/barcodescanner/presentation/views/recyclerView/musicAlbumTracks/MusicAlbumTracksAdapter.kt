package com.github.nearapps.barcodescanner.presentation.views.recyclerView.musicAlbumTracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemMusicAlbumTrackBinding
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.AlbumTrack

class MusicAlbumTracksAdapter(private val tracks: List<AlbumTrack>,
                              private val artists: String?): RecyclerView.Adapter<MusicAlbumTracksHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAlbumTracksHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = RecyclerViewItemMusicAlbumTrackBinding.inflate(layoutInflater, parent, false)
        return MusicAlbumTracksHolder(viewBinding)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: MusicAlbumTracksHolder, position: Int) {
        holder.updateItem(tracks[position], artists)
    }
}