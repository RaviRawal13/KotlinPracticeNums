package com.foobles.kotlinnum.home.repo

import com.foobles.kotlinnum.network.NetworkManager

class MainRepo {
    suspend fun fetchRandomTrivia(): String = NetworkManager
        .retrofitService.randomNumberTrivia()
}