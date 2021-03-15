package com.jesen.cod.gitappkotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import com.jesen.cod.gitappkotlin.AppContext
import org.jetbrains.anko.connectivityManager
import java.net.NetworkInterface

object NetworkUtil {

    private const val TAG = "NetworkUtil"

    fun isAvailable(): Boolean =
        AppContext.connectivityManager.activeNetworkInfo?.isConnected ?: false

    fun getIpv4(context: Context): String {
        var ip: String
        val wifiManager =
            context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        ip = intToIp(ipAddress)
        AppLog.i(TAG, "wifi_ip地址为------$ip")
        return ip
    }

    private fun intToIp(ipInt: Int): String {
        val sb = StringBuilder()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }

    fun getWifiIp(context: Context): String {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        AppLog.i(TAG, "getIp: $ipAddress")
        val ip =
            (ipAddress and 0xff).toString() + "." + (ipAddress shr 8 and 0xff) + "." + (ipAddress shr 16 and 0xff) + "." + (ipAddress shr 24 and 0xff)
        return ip
    }

    fun getMobileIp(): String {
        var en = NetworkInterface.getNetworkInterfaces()//获取所有网络接口
        while (en.hasMoreElements()) {
            var nif = en.nextElement()
            var eip = nif.inetAddresses
            while (eip.hasMoreElements()) {//获取接口的地址
                var ia = eip.nextElement()
                AppLog.i(TAG, "getMobileIp: ${ia.hostAddress}")

                if (nif.displayName.contains("wlan0")) { //获取无线网卡Ip地址
                    AppLog.i(TAG, "wlan0" + ia.hostAddress)
                    if (checkIp(ia.hostAddress)) {
                        return ia.hostAddress
                    }
                }
            }
        }
        return "0.0.0.0"
    }

    private fun checkIp(ip: String): Boolean {
        if (ip.split(".").size == 4 && ip != "127.0.0.1") {
            return true
        }
        return false
    }

}