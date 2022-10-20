package ru.touchin.yandexmap

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.yandex.runtime.ui_view.ViewProvider

class DefaultIconGenerator<T : PointModel>(private val context: Context) : YandexIconGenerator<T>() {

    override fun getClusterIcon(cluster: List<T>): ViewProvider {
        val textView = LayoutInflater.from(context).inflate(R.layout.default_cluster_view, null).apply {
            (this as? TextView)?.text = cluster.size.toString()
            setBackgroundResource(ru.touchin.basemap.R.drawable.marker_default_icon)
        }
        return ViewProvider(textView)
    }

    override fun getClusterItemIcon(clusterItem: T) = ViewProvider(
            LayoutInflater.from(context).inflate(R.layout.default_cluster_item_view, null)
    )
}
