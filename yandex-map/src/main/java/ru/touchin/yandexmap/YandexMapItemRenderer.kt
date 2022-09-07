package ru.touchin.yandexmap

import com.yandex.runtime.ui_view.ViewProvider
import ru.touchin.basemap.BaseIconGenerator
import ru.touchin.basemap.BaseMapItemRenderer

class YandexMapItemRenderer<TPoint : PointModel>(
        override var iconGenerator: BaseIconGenerator<TPoint, List<TPoint>, ViewProvider>
) : BaseMapItemRenderer<TPoint, List<TPoint>, ViewProvider>
