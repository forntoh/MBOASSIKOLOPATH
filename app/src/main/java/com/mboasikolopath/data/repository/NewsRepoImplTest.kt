package com.mboasikolopath.data.repository

import com.mboasikolopath.data.model.News

class NewsRepoImplTest : NewsRepo() {

    override suspend fun loadAll() = listOf(
        News(
            0,
            "Exclusive: Google suspends some business with Huawei after Trump blacklist - source",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "https://s3.reutersmedia.net/resources/r/?m=02&d=20190520&t=2&i=1389203911&r=LYNXNPEF4J1DM&w=1200"
        ),
        News(
            1,
            "Markets slide as Panasonic joins list of firms walking away from Huawei",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "https://i.guim.co.uk/img/media/f1facac45751fa88a9b6663f6639936db75aab16/450_0_4573_2744/master/4573.jpg?width=620&quality=85&auto=format&fit=max&s=8f7102001bd7840a9639fdf86bf75fb3"
        ),
        News(
            2,
            "Panasonic 'suspends transactions' with Huawei after US ban",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "https://ichef.bbci.co.uk/news/320/cpsprodpb/12B53/production/_107072667_gettyimages-681753468-1.jpg"
        ),
        News(
            3,
            "Mobile networks are suspending orders for Huawei smartphones",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            "https://cdn.cnn.com/cnnnext/dam/assets/190520155658-01-huawei-file-exlarge-169.jpg"
        )
    )

    override suspend fun findNewsByID(id: Int) = loadAll().find { news -> news.NewsID == id }!!

    override suspend fun initData() = Unit
}