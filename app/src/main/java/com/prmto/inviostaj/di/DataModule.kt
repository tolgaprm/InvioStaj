package com.prmto.inviostaj.di

import com.prmto.inviostaj.data.remote.RequestInterceptor
import com.prmto.inviostaj.data.remote.TmdbApi
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSourceImpl
import com.prmto.inviostaj.data.repository.MovieRepositoryImpl
import com.prmto.inviostaj.domain.repository.MovieRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        onHttpClient: OkHttpClient,
        moshi: Moshi
    ): TmdbApi {
        return Retrofit.Builder()
            .baseUrl(TmdbApi.BASE_URL)
            .client(onHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(
        tmdbApi: TmdbApi
    ): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(tmdbApi)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource)
    }
}