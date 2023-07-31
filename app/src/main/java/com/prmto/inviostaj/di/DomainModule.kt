package com.prmto.inviostaj.di

import com.prmto.inviostaj.domain.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.ConvertDateFormatUseCase
import com.prmto.inviostaj.domain.usecase.ConvertGenreListToSeparatedByCommaUseCase
import com.prmto.inviostaj.domain.usecase.ConvertVoteCountToKFormatUseCase
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
        repository: MovieRepository,
    ): GetTopRatedMoviePagingDataUseCase {
        return GetTopRatedMoviePagingDataUseCase(
            repository = repository,
            convertVoteCountToKFormatUseCase = ConvertVoteCountToKFormatUseCase(),
            convertGenreListToSeparatedByCommaUseCase = ConvertGenreListToSeparatedByCommaUseCase(),
            convertDateFormatUseCase = ConvertDateFormatUseCase()
        )
    }
}