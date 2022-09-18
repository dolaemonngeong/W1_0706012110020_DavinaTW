package Database

import Model.Hewan

class GlobalVar {
    companion object{
        val listDataHewan = ArrayList<Hewan>()
        val STORAGEWrite_PERMISSION_CODE: Int = 3
        val STORAGERead_PERMISSION_CODE: Int= 2
    }
}