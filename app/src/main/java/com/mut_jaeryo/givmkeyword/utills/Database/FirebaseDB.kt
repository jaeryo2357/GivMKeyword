package com.mut_jaeryo.givmkeyword.utills.Database

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


//이미지 저장, 삭제 , 조회 구현
class FirebaseDB{


    companion object {
        public fun saveDrawing(activity:Activity,keyword: String, image: Bitmap, name: String, content: String): Boolean {

            val db = FirebaseFirestore.getInstance().collection(keyword)
            val doc = db.document() //고유 id를 자동으로 생성


            val imagesRef: StorageReference? = FirebaseStorage.getInstance().reference.child("images/" + doc.id + ".jpg")


            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data_byte = baos.toByteArray()

            val uploadTask = imagesRef!!.putBytes(data_byte)
            uploadTask.addOnFailureListener {
                Toast.makeText(activity, "서버에 저장이 실패했습니다 ㅠㅠ", Toast.LENGTH_LONG).show()
                Log.d("ImageUpload", "failed")
            }.addOnSuccessListener {
                val data = hashMapOf(
                        "name" to name,
                        "content" to content
                )
               val now = GregorianCalendar()

                doc.set(data)
                        .addOnSuccessListener {
                            val drawingDB = DrawingDB(activity.applicationContext)
                            drawingDB.open()
                            drawingDB.DrawingInsert(doc.id,content,"${now[Calendar.YEAR]}-${now[Calendar.MONTH]+1}-${now[Calendar.DAY_OF_MONTH]}")
                            drawingDB.close()

                            Toast.makeText(activity, "저장에 성공했습니다.", Toast.LENGTH_LONG).show() }
                        .addOnCanceledListener {
                            Toast.makeText(activity, "서버에 저장이 실패했습니다 ㅠㅠ", Toast.LENGTH_LONG).show()
                            imagesRef.delete()
                            Log.d("Document", "failed")
                        }
            }

            return true
        }

        public fun getKeywordDrawings(keyword: String): ArrayList<drawingItem> {
            val array: ArrayList<drawingItem> = ArrayList()
            val db = FirebaseFirestore.getInstance()

            db.collection(keyword)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val name: String = document.getString("name") ?: "알수없음"
                            val content: String = document.getString("content") ?: ""
                            array.add(drawingItem(document.id, name, content))
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("GetDrawing", "Error getting documents: ", exception)
                    }
            return array
        }

        public fun getMineDrawings(context: Context): ArrayList<drawingItem> {

            val drawingDB = DrawingDB(context)
            drawingDB.open()
            val array: ArrayList<drawingItem> = drawingDB.getMyDrawing()
            drawingDB.close()
            return array
        }
    }
}