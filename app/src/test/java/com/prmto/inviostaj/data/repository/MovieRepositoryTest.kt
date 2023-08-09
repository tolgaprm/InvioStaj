package com.prmto.inviostaj.data.repository

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.TestConstants
import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.datasource.local.FakeMovieLocalDataSource
import com.prmto.inviostaj.data.remote.datasource.remote.FakeMovieRemoteDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {
    private lateinit var remoteDataSource: MovieRemoteDataSource
    private lateinit var localDataSource: MovieLocalDataSource
    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        remoteDataSource = FakeMovieRemoteDataSource()
        localDataSource = FakeMovieLocalDataSource()
        repository = MovieRepositoryImpl(remoteDataSource, localDataSource)
    }

    // Get TopRatedMovies
    @Test
    fun `getTopRatedMovies, when remoteDataSource return success, should return success`() =
        runTest {
            assertThat(repository.getTopRatedMovies(1)).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `getTopRatedMovies, when remoteDataSource return error, should return error`() =
        runTest {
            passNewDataSourceToRepository(FakeMovieRemoteDataSource(isReturnSuccess = false))
            assertThat(repository.getTopRatedMovies(1)).isInstanceOf(Resource.Error::class.java)
        }

    // Get MovieGenreList
    @Test
    fun `getMovieGenreList, when remoteDataSource return success, should return success`() =
        runTest {
            assertThat(repository.getMovieGenreList()).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `getMovieGenreList, when remoteDataSource return error, should return error`() =
        runTest {
            passNewDataSourceToRepository(FakeMovieRemoteDataSource(isReturnSuccess = false))
            assertThat(repository.getMovieGenreList()).isInstanceOf(Resource.Error::class.java)
        }

    // Get MovieDetail
    @Test
    fun `getMovieDetail, when remoteDataSource return success, should return success`() =
        runTest {
            assertThat(repository.getMovieDetail(TestConstants.movieDetailId)).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `getMovieDetail, when remoteDataSource return error, should return error`() =
        runTest {
            passNewDataSourceToRepository(FakeMovieRemoteDataSource(isReturnSuccess = false))
            assertThat(repository.getMovieDetail(TestConstants.movieDetailId)).isInstanceOf(Resource.Error::class.java)
        }

    // Get SearchMovies
    @Test
    fun `getSearchMovies, when remoteDataSource return success, should return success`() =
        runTest {
            assertThat(
                repository.getSearchMovies(
                    query = TestConstants.oppenheimerQuery,
                    page = 1
                )
            ).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `getSearchMovies, when remoteDataSource return error, should return error`() =
        runTest {
            passNewDataSourceToRepository(FakeMovieRemoteDataSource(isReturnSuccess = false))
            assertThat(
                repository.getSearchMovies(
                    query = TestConstants.oppenheimerQuery,
                    page = 1
                )
            ).isInstanceOf(Resource.Error::class.java)
        }

    // Get FavoriteMovies
    @Test
    fun `getFavoriteMovies, when localDataSource return success, should return success`() =
        runTest {
            assertThat(
                repository.getFavoriteMovies()
            ).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun `getFavoriteMovies, when localDataSource return error, should return error`() =
        runTest {
            passNewDataSourceToRepository(localDataSource = FakeMovieLocalDataSource(isReturnSuccess = false))
            assertThat(
                repository.getFavoriteMovies()
            ).isInstanceOf(Resource.Error::class.java)
        }

    private fun passNewDataSourceToRepository(
        remoteDataSource: FakeMovieRemoteDataSource = FakeMovieRemoteDataSource(),
        localDataSource: FakeMovieLocalDataSource = FakeMovieLocalDataSource()
    ) {
        repository = MovieRepositoryImpl(remoteDataSource, localDataSource)
    }
}