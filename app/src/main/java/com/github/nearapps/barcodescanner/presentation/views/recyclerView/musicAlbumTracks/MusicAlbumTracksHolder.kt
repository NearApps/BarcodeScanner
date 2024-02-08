package com.github.nearapps.barcodescanner.presentation.views.recyclerView.musicAlbumTracks

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.nearapps.barcodescanner.databinding.RecyclerViewItemMusicAlbumTrackBinding
import com.github.nearapps.barcodescanner.domain.entity.product.musicProduct.AlbumTrack

class MusicAlbumTracksHolder(private val viewBinding: RecyclerViewItemMusicAlbumTrackBinding)
    : RecyclerView.ViewHolder(viewBinding.root) {

    fun updateItem(track: AlbumTrack, artist: String?) {
        configureTextView(viewBinding.recyclerViewItemMusicAlbumTrackNumberTextView, track.position?.let { "$it" })
        configureTextView(viewBinding.recyclerViewItemMusicAlbumTrackTitleTextView, track.title)
        configureTextView(viewBinding.recyclerViewItemMusicAlbumTrackLengthTextView, track.length?.let { convertMillisecondsToMinutesSeconds(it) })
        configureTextView(viewBinding.recyclerViewItemMusicAlbumTrackArtistTextView, artist)
    }

    private fun configureTextView(textView: TextView, text: String?) {
        textView.visibility = if(text.isNullOrEmpty()) View.GONE else View.VISIBLE
        textView.text = text
    }

    private fun convertMillisecondsToMinutesSeconds(time: Long): String {
        val minutes = time / 60000
        val seconds = (time % 60000) / 1000
        return String.format("%d:%02d", minutes, seconds)
    }
}