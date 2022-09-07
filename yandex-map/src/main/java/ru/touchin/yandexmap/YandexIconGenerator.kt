package ru.touchin.yandexmap

import com.yandex.runtime.ui_view.ViewProvider
import ru.touchin.basemap.BaseIconGenerator
import ru.touchin.basemap.getOrPutIfNotNull

abstract class YandexIconGenerator<TPoint : PointModel>
    : BaseIconGenerator<TPoint, List<TPoint>, ViewProvider> {

    private val placemarksCache = mutableMapOf<TPoint, ViewProvider>()
    private val clustersCache = mutableMapOf<List<TPoint>, ViewProvider>()

    override fun getClusterItemView(clusterItem: TPoint) = placemarksCache.getOrPutIfNotNull(clusterItem) {
        getClusterItemIcon(clusterItem)
    }

    override fun getClusterView(cluster: List<TPoint>) = clustersCache.getOrPutIfNotNull(cluster) {
        getClusterIcon(cluster)
    }

    abstract override fun getClusterIcon(cluster: List<TPoint>): ViewProvider?

    abstract override fun getClusterItemIcon(clusterItem: TPoint): ViewProvider?
}
