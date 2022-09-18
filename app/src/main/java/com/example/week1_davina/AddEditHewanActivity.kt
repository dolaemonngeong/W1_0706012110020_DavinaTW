package com.example.week1_davina

import Database.GlobalVar
import Model.Hewan
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.week1_davina.databinding.ActivityAddEditHewanBinding

class AddEditHewanActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityAddEditHewanBinding
    private lateinit var hewan: Hewan
    private var position = -1
    private var photoHewan: String =""
    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data                 // GET PATH TO IMAGE FROM GALLEY

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(uri != null){
                    baseContext.getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                }
            }
            //code

            viewBind.foto.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
            photoHewan = uri.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityAddEditHewanBinding.inflate(layoutInflater)
        setContentView(viewBind.root)

        CheckPermissions()
        GetIntent()
        Listener()
    }

    private fun GetIntent(){
        position = intent.getIntExtra("buatEdit", -1)
        if(position != -1){
            val hewan = GlobalVar.listDataHewan[position]
            viewBind.toolbarAddEdit.title = "Edit Hewan"
            viewBind.foto.setImageURI(Uri.parse(hewan.imageUri))
            viewBind.namaInput.editText?.setText(hewan.nama)
            viewBind.jenisInput.editText?.setText(hewan.jenis)
            viewBind.usiaInput.editText?.setText(hewan.usia)
        }
    }

    private fun CheckPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), GlobalVar.STORAGEWrite_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), GlobalVar.STORAGERead_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GlobalVar.STORAGEWrite_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == GlobalVar.STORAGERead_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun Listener(){
        viewBind.foto.setOnClickListener{
            val gambarIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            gambarIntent.type = "image/*"
            GetResult.launch(gambarIntent)
        }
        viewBind.simpantombol.setOnClickListener{
            var nama = viewBind.namaInput.editText?.text.toString().trim()
            var jenis = viewBind.jenisInput.editText?.text.toString().trim()
            var usia = viewBind.usiaInput.editText?.text.toString().trim()

            hewan = Hewan(nama, jenis, usia)
            hewan.imageUri = photoHewan
            Checking()
        }
        viewBind.toolbarAddEdit.getChildAt(1).setOnClickListener{
            finish()
        }
    }

    private fun Checking(){
        var isCompleted:Boolean = true

        //judul film
        if(hewan.nama!!.isEmpty()){
            viewBind.namaInput.error = "Tolong isi nama hewannya"
            isCompleted = false
        }else{
            viewBind.namaInput.error = ""
        }

        //rating film
        if(hewan.jenis!!.isEmpty()){
            viewBind.jenisInput.error = "Tolong isi jenis hewan"
            isCompleted = false
        }else{
            viewBind.jenisInput.error = ""
        }

        //genre film
        if(hewan.usia!!.isEmpty()){
            viewBind.usiaInput.error = "Tolong isi usia hewannya"
            isCompleted = false
        }else{
            viewBind.usiaInput.error = ""
        }

        if(isCompleted) {
            if (position == -1) {
                GlobalVar.listDataHewan.add(hewan)
                finish()
            } else {
                GlobalVar.listDataHewan[position] = hewan
                Toast.makeText(this, "tes123", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

}