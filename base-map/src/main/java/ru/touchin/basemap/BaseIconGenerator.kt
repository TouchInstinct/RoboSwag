package ru.touchin.basemap

interface BaseIconGenerator<TPoint, TCluster, TViewIcon> {

    fun getClusterIcon(cluster: TCluster): TViewIcon?

    fun getClusterItemIcon(clusterItem: TPoint): TViewIcon?

    fun getClusterItemView(clusterItem: TPoint): TViewIcon?

    fun getClusterView(cluster: TCluster): TViewIcon?
}
