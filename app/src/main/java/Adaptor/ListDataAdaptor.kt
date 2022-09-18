package Adaptor

import Database.GlobalVar
import Interface.CardListener
import Model.Hewan
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_davina.MainActivity
import com.example.week1_davina.R
import com.example.week1_davina.databinding.CardHewanBinding


class ListDataAdaptor(val listHewan: ArrayList<Hewan>, val cardListener: CardListener):
    RecyclerView.Adapter<ListDataAdaptor.viewHolder>(){
    class viewHolder(itemView:View, val cardListener1: CardListener):RecyclerView.ViewHolder(itemView){
        //memanage card_user -> kegunaan class viewHolder
        val binding = CardHewanBinding.bind(itemView)
        //val namaCard = itemView.card_view

        fun setData(data:Hewan){
            binding.namaCard.text = data.nama
            binding.jenisCard.text = data.jenis
            binding.usiaCard.text = data.usia + " tahun"

            if(!data.imageUri.isEmpty()){
                binding.posterfilmCard.setImageURI(Uri.parse(data.imageUri))
            }

            binding.hapusHewan.setOnClickListener {
                cardListener1.onCardClick("hapus",adapterPosition)

            }

            binding.editHewan.setOnClickListener {
                cardListener1.onCardClick("edit",adapterPosition)
            }
        }

//
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataAdaptor.viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_hewan,parent, false)
        return viewHolder(view, cardListener)

        // Listener()
    }

    override fun onBindViewHolder(holder: ListDataAdaptor.viewHolder, position: Int) {
        holder.setData(listHewan[position])
    }

    override fun getItemCount(): Int {
        return listHewan.size
    }

}