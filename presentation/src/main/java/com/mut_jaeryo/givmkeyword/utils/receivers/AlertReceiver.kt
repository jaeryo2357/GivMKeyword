package com.mut_jaeryo.givmkeyword.utils.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mut_jaeryo.givmkeyword.utils.keywords.Keyword
import com.mut_jaeryo.givmkeyword.utils.services.SendAlert
import com.mut_jaeryo.givmkeyword.utils.services.ShowNotify
import java.util.*


class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {


        val now = GregorianCalendar()
        now.add(Calendar.DAY_OF_MONTH, 1)
//         now.add(Calendar.SECOND,30)
        now.set(Calendar.HOUR_OF_DAY, 8)
//        now[Calendar.HOUR_OF_DAY] = 8
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MINUTE, 0)
//        now[Calendar.MINUTE] = 0
        SendAlert.setAlert(p0!!, now) //1일 뒤에 다시 설정

        val month = now[Calendar.MONTH] + 1

        Preference.setWork(p0, 0)

        //키워드 변경
        if (Preference.getInit(p0)) {
            val keyword = Keyword.getKeyword(p0)
            Preference.setKeyword(p0, keyword)
        }

        Preference.setDate(p0, "${now[Calendar.YEAR]}-$month-${now[Calendar.DAY_OF_MONTH]}")

        val service = Intent(p0, ShowNotify::class.java)
        p0.startService(service)
    }
}