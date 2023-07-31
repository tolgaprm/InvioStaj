package com.prmto.inviostaj.di

import com.prmto.inviostaj.domain.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.GetMovieGenreListUseCase
import com.prmto.inviostaj.domain.usecase.GetTopRatedMoviePagingDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetMovieGenreListUseCase(
        repository: MovieRepository
    ): GetMovieGenreListUseCase {
        return GetMovieGenreListUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTopRatedMoviePagingDataUseCase(
        repository: MovieRepository,
        getMovieGenreListUseCase: GetMovieGenreListUseCase
    ): GetTopRatedMoviePagingDataUseCase {
        return GetTopRatedMoviePagingDataUseCase(
            repository = repository,
            getMovieGenreListUseCase = getMovieGenreListUseCase
        )
    }
}