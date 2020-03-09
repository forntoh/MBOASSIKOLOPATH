package com.mboasikolopath.ui.main.home

import androidx.lifecycle.ViewModel
import com.mboasikolopath.internal.view.NewsItem

class HomeViewModel : ViewModel() {

    fun getNews() = listOf(
        NewsItem(
            "Exclusive: Google suspends some business with Huawei after Trump blacklist - source",
            "https://s3.reutersmedia.net/resources/r/?m=02&d=20190520&t=2&i=1389203911&r=LYNXNPEF4J1DM&w=1200"
        ),
        NewsItem(
            "Markets slide as Panasonic joins list of firms walking away from Huawei",
            "https://i.guim.co.uk/img/media/f1facac45751fa88a9b6663f6639936db75aab16/450_0_4573_2744/master/4573.jpg?width=620&quality=85&auto=format&fit=max&s=8f7102001bd7840a9639fdf86bf75fb3"
        ),
        NewsItem(
            "Panasonic 'suspends transactions' with Huawei after US ban",
            "https://ichef.bbci.co.uk/news/320/cpsprodpb/12B53/production/_107072667_gettyimages-681753468-1.jpg"
        ),
        NewsItem(
            "Mobile networks are suspending orders for Huawei smartphones",
            "https://cdn.cnn.com/cnnnext/dam/assets/190520155658-01-huawei-file-exlarge-169.jpg"
        )
    )

}
