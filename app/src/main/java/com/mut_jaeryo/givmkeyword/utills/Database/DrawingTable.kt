package com.mut_jaeryo.givmkeyword.utills.Database

class DrawingTable{

    companion object{

        const val Table_Name = "MyDrawing"

        const val column1 = "id"
        const val column2 = "content"
        const val _create = "create table $Table_Name( $column1 text, $column2 text);"

    }
}