package ru.touchin.basemap

interface BaseMapItemRenderer<TPoint, TCluster, TViewIcon> {

    var iconGenerator: BaseIconGenerator<TPoint, TCluster, TViewIcon>

    fun getClusterItemIcon(item: TPoint): TViewIcon? = iconGenerator.getClusterItemView(item)

    fun getClusterIcon(cluster: TCluster): TViewIcon? = iconGenerator.getClusterView(cluster)
}
