package com.jdm.menunenaw.di

import com.jdm.menunenaw.data.remote.KaKaoApi
import com.jdm.menunenaw.data.remote.repository.KaKaoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideKakaoRepository(
        @AUTH kakaoApi: KaKaoApi,
    ): KaKaoRepo {
        return KaKaoRepo(kakaoApi)
    }

}