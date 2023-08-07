package com.prmto.inviostaj.di

import android.content.Context
import androidx.room.Room
import com.prmto.inviostaj.BuildConfig
import com.prmto.inviostaj.data.interceptor.RequestInterceptor
import com.prmto.inviostaj.data.local.InvioDatabase
import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSource
import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSourceImpl
import com.prmto.inviostaj.data.remote.api.TmdbApi
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSourceImpl
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.data.repository.MovieRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .client(onHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideInvioDatabase(
        @ApplicationContext context: Context
    ): InvioDatabase {
        return Room.databaseBuilder(
            context,
            InvioDatabase::class.java,
            InvioDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        invioDatabase: InvioDatabase
    ): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao = invioDatabase.movieDao())
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(
        tmdbApi: TmdbApi
    ): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(api = tmdbApi)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieRemoteDataSource = movieRemoteDataSource,
            movieLocalDataSource = movieLocalDataSource
        )
    }
}