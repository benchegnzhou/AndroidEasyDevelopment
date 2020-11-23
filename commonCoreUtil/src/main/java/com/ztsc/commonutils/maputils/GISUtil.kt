package com.ztsc.commonutils.maputils

import android.util.Log


object GISUtil {

    /// <summary>
    /// 地球半径(m)
    /// </summary>
    private val EarthRadius = 6378137.00


    /// <summary>
    /// 计算两经纬度间的距离，采用GoogleMap算法
    /// </summary>
    /// <param name="lon1">经度1</param>
    /// <param name="lat1">纬度1</param>
    /// <param name="lon2">经度2</param>
    /// <param name="lat2">纬度2</param>
    /// <returns>返回以米为单位的距离</returns>
    @JvmStatic
   open fun distance(
        lon1: Double,
        lat1: Double,
        lon2: Double,
        lat2: Double
    ): Double {
        val radLat1 = Radian(lat1)
        val radLat2 = Radian(lat2)
        val a = radLat1 - radLat2
        val b = Radian(lon1) - Radian(lon2)
        var s = 2 * Math.asin(
            Math.sqrt(
                Math.pow(
                    Math.sin(a / 2),
                    2.0
                ) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(
                    Math.sin(b / 2),
                    2.0
                )
            )
        )
        s *= EarthRadius
        return s
    }
    @JvmStatic
    open fun distanceDesc(distance: Double): String? {
        val v = distance / 1000
        return if (v > 1000) {
            "很远很远"
        } else {
            String.format("%.2fkm", v)
        }
    }

    /**
     * 将地图上GPS手机定位后的偏转坐标点，转换为实际距离
     * 返回单位为Km
     *
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return
     */
    fun point2distanceKm(
        lon1: Double,
        lat1: Double,
        lon2: Double,
        lat2: Double
    ): String? {
        return try {
            distanceDesc(distance(lon1, lat1, lon2, lat2))
        } catch (e: Exception) {
            Log.e("地图点坐标数转换距离出错了", e.toString())
            ""
        }
    }

    /**
     * 将地图上GPS手机定位后的偏转坐标点，转换为实际距离
     * 返回单位为Km
     *
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return
     */
    fun point2distanceKm(
        lon1: String,
        lat1: String,
        lon2: Double,
        lat2: Double
    ): String? {
        return try {
            distanceDesc(distance(lon1.toDouble(), lat1.toDouble(), lon2, lat2))
        } catch (e: Exception) {
            Log.e("地图点坐标数转换距离出错了", e.toString())
            ""
        }
    }

    /// <summary>
    /// 角度转换为弧度
    /// </summary>
    /// <param name="angle">角度</param>
    /// <returns>弧度</returns>
    private fun Radian(angle: Double): Double {
        return angle * Math.PI / 180.0
    }
}