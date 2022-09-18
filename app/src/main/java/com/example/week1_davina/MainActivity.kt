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

//            showAlertDialog(position)
//            GlobalVar.listDataHewan.removeAt(position)
//
//            val keMainIntent = Intent(this, MainActivity::class.java)
//            startActivity(keMainIntent)
//            Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
            //finish()
        }
        if(id == "edit"){
            val keAddEditIntent = Intent(this, AddEditHewanActivity::class.java).putExtra("buatEdit", position)
            startActivity(keAddEditIntent)

            //finish()
        }
    }

//    private fun showAlertDialog(position: Int){
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Hapus")
//        builder.setMessage("Apakah anda yakin ingin menghapusnya?")
//        builder.setIcon(android.R.drawable.ic_dialog_alert)
//
//        builder.setPositiveButton("Ya") { dialogInterface, which ->
//            Toast.makeText(applicationContext, "yes bershasil dihapus :)", Toast.LENGTH_SHORT).show()
//            GlobalVar.listDataHewan.removeAt(position)
//
//            val keMainIntent = Intent(this, MainActivity::class.java)
//            startActivity(keMainIntent)
//            Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
//        }
//
//        builder.setNegativeButton("Tidak") { dialogInterface, which ->
//            dialogInterface.dismiss()
//        }
//
//        val alertDialog: AlertDialog = builder.create()
//        alertDialog.setCancelable(false) //biar user tidak bisa pencet di luar alert dialog
//        alertDialog.show() //menampilkan alert dialog
//    }

}
