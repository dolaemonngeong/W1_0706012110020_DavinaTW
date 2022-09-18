package com.example.week1_davina

import Adaptor.ListDataAdaptor
import Database.GlobalVar
import Interface.CardListener
import android.app.AlertDialog
import android.content.Intent
import android.location.GnssAntennaInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week1_davina.databinding.ActivityMainBinding
import com.example.week1_davina.databinding.CardHewanBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), CardListener {
    private lateinit var viewBind: ActivityMainBinding
    private var adapter = ListDataAdaptor(GlobalVar.listDataHewan,  this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        setupRecyclerView()
        Listener()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(baseContext)
        viewBind.listHewanRV.layoutManager = layoutManager //set layout
        viewBind.listHewanRV.adapter = adapter //set adaptor
    }

    private fun Listener() {
        viewBind.tambahtombol.setOnClickListener {
            val keAddEditIntent = Intent(this, AddEditHewanActivity::class.java)
            startActivity(keAddEditIntent)
        }
    }

    override fun onCardClick(id: String, position: Int) {
        if(id == "hapus"){
            AlertDialog.Builder(this).setMessage("Apakah anda yakin ingin menghapusnya?")
                .setPositiveButton("Ya") { dialog, id ->
                    GlobalVar.listDataHewan.removeAt(position)
                    val snackBar =
                        Snackbar.make(viewBind.root, "Hewan telah dihapus", Snackbar.LENGTH_SHORT)

                    adapter.notifyDataSetChanged()
                    val keMainIntent = Intent(this, MainActivity::class.java)
                    startActivity(keMainIntent)
                }
                .setNegativeButton("Tidak") { dialog, id ->

                    dialog.dismiss()
                }.create().show()

        }
        if(id == "edit"){
            val keAddEditIntent = Intent(this, AddEditHewanActivity::class.java).putExtra("buatEdit", position)
            startActivity(keAddEditIntent)

        }
    }


}
