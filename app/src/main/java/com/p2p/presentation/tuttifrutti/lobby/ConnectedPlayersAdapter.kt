package com.p2p.presentation.tuttifrutti.lobby

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.p2p.databinding.ViewConnectedUserBinding

class ConnectedPlayersAdapter: RecyclerView.Adapter<ConnectedPlayersAdapter.ViewHolder>() {

    var players = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewConnectedUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(players[position])
    }

    override fun getItemCount() = players.size

    inner class ViewHolder(private val binding: ViewConnectedUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) = with(binding) {
            name.text = category
        }
    }
}
