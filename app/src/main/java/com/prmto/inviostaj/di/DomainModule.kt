package com.prmto.inviostaj.di

import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.ConvertDateFormatUseCase
import com.prmto.inviostaj.domain.usecase.ConvertMovieGenreListToSeparatedByCommaUseCase
import com.prmto.inviostaj.domain.usecase.GetMovieDetailUseCase
import com.prmto.inviostaj.domain.usecase.GetSearchMovieUseCase
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
    fun provideGetTopRatedMoviePagingDataUseCase(
        repository: MovieRepository
    ): GetTopRatedMoviePagingDataUseCase {
        return GetTopRatedMoviePagingDataUseCase(
            repository = repository,
            convertMovieGenreListToSeparatedByCommaUseCase = ConvertMovieGenreListToSeparatedByCommaUseCase(repository),
            convertDateFormatUseCase = ConvertDateFormatUseCase()
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetMovieDetailUseCase(
        repository: MovieRepository
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(
            movieRepository = repository,
            convertDateFormatUseCase = ConvertDateFormatUseCase()
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetSearchMovieUseCase(
        repository: MovieRepository
    ): GetSearchMovieUseCase {
        return GetSearchMovieUseCase(
            repository = repository,
            convertDateFormatUseCase = ConvertDateFormatUseCase(),
            convertMovieGenreListToSeparatedByCommaUseCase = ConvertMovieGenreListToSeparatedByCommaUseCase(repository)
        )
    }
}