package ru.touchin.yandexmap

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.yandex.runtime.ui_view.ViewProvider

class DefaultIconGenerator<T : PointModel>(private val context: Context) : YandexIconGenerator<T>() {

    override fun getClusterIcon(cluster: List<T>): ViewProvider {
        val view: TextView = LayoutInflater.from(context).inflate(R.layout.default_cluster_view, null) as TextView
        view.setText(cluster.size.toString())
        view.setBackgroundResource(ru.touchin.basemap.R.drawable.marker_default_icon)

        return ViewProvider(view)
    }

    override fun getClusterItemIcon(clusterItem: T) = ViewProvider(
            LayoutInflater.from(context).inflate(R.layout.default_cluster_item_view, null)
    )
}
