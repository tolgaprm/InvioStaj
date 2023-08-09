package com.prmto.inviostaj.data.remote.datasource.remote

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.TestConstants
import com.prmto.inviostaj.data.remote.api.TmdbApi
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSourceImpl
import com.prmto.inviostaj.data.remote.response.genreErrorResponse
import com.prmto.inviostaj.data.remote.response.genreJsonResponse
import com.prmto.inviostaj.data.remote.response.movieDetailErrorJsonResponse
import com.prmto.inviostaj.data.remote.response.movieDetailJsonResponse
import com.prmto.inviostaj.data.remote.response.searchMoviesErrorResponse
import com.prmto.inviostaj.data.remote.response.searchMoviesJsonResponse
import com.prmto.inviostaj.data.remote.response.topRatedMoviesErrorResponse
import com.prmto.inviostaj.data.remote.response.topRatedMoviesJsonResponsePage1
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieRemoteDataSourceTest {
    private lateinit var movieRemoteDataSource: MovieRemoteDataSourceImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: TmdbApi
    private lateinit var moshi: Moshi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        api = Retrofit.Builder().addConverterFactory(
            MoshiConverterFactory.create(
                moshi
            )
        ).baseUrl(mockWebServer.url("/")).build().create(TmdbApi::class.java)

        movieRemoteDataSource = MovieRemoteDataSourceImpl(
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    // TopRatedMovies Test
    @Test
    fun `whenGetTopRatedMovie, correct response is parsed into success result`() = runTest {
        enqueueWithMockResponse(body = topRatedMoviesJsonResponsePage1)
        val result = movieRemoteDataSource.getTopRatedMovies(page = 1)
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `whenGetTopRatedMovie, malformed response returns json error result `() = runTest {
        enqueueWithMockResponse(body = topRatedMoviesErrorResponse)
        val result = movieRemoteDataSource.getTopRatedMovies(page = 1)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).exception).isInstanceOf(JsonDataException::class.java)
    }

    @Test
    fun `whenGetTopRatedMovie, error response returns http error result`() = runTest {
        enqueueWithMockResponse(body = topRatedMoviesJsonResponsePage1, responseCode = 400)
        val result = movieRemoteDataSource.getTopRatedMovies(page = 1)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).exception).isInstanceOf(HttpException::class.java)
    }

    @Test
    fun `whenGetTopRatedMovie requestPath isSameWithRequest`() = runTest {
        enqueueWithMockResponse(body = topRatedMoviesJsonResponsePage1)
        movieRemoteDataSource.getTopRatedMovies(page = 1)
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/movie/top_rated?page=1")
    }

    @Test
    fun `WhenGetTopRatedMovie, firstElement original title hasSameName`() = runTest {
        enqueueWithMockResponse(body = topRatedMoviesJsonResponsePage1)
        val result = movieRemoteDataSource.getTopRatedMovies(page = 1)
        assertThat(result.data!!.first().originalTitle).isEqualTo("The Godfather")
    }

    // Get Movie Genre Test
    @Test
    fun `whenGetMovieGenre correct response is parsed into success result`() = runTest {
        enqueueWithMockResponse(body = genreJsonResponse)
        val result = movieRemoteDataSource.getMovieGenreList()
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `whenGetMovieGenre, malformed response returns json error result `() = runTest {
        enqueueWithMockResponse(body = genreErrorResponse)
        val result = movieRemoteDataSource.getMovieGenreList()
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).exception).isInstanceOf(JsonDataException::class.java)
    }

    @Test
    fun `whenGetMovieGenre, error response returns http error result`() = runTest {
        enqueueWithMockResponse(responseCode = 400, body = genreJsonResponse)
        val result = movieRemoteDataSource.getMovieGenreList()
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).exception).isInstanceOf(HttpException::class.java)
    }

    @Test
    fun `whenGetMovieGenre requestPath isSameWithRequest`() = runTest {
        enqueueWithMockResponse(body = genreJsonResponse)
        movieRemoteDataSource.getMovieGenreList()
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/genre/movie/list")
    }

    @Test
    fun `whenGetMovieGenre firstElement hasSameName`() = runTest {
        enqueueWithMockResponse(body = genreJsonResponse)
        val result = movieRemoteDataSource.getMovieGenreList()
        assertThat(result.data!!.genres[0].name).isEqualTo("Action")
        assertThat(result.data!!.genres[0].id).isEqualTo(28)
    }

    // Movie Detail Test
    @Test
    fun `whenGetMovieDetail correct response is parsed into success result`() = runTest {
        enqueueWithMockResponse(body = movieDetailJsonResponse)
        val result = movieRemoteDataSource.getMovieDetail(TestConstants.movieDetailId)
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `whenGetMovieDetail, malformed response returns json error result `() = runTest {
        enqueueWithMockResponse(body = movieDetailErrorJsonResponse)
        val result = movieRemoteDataSource.getMovieDetail(TestConstants.movieDetailId)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).exception).isInstanceOf(JsonDataException::class.java)
    }

    @Test
    fun `whenGetMovieDetail, error response returns http error result`() = runTest {
        enqueueWithMockResponse(responseCode = 400, body = movieDetailJsonResponse)
        val result = movieRemoteDataSource.getMovieDetail(TestConstants.movieDetailId)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).exception).isInstanceOf(HttpException::class.java)
    }

    @Test
    fun `whenGetMovieDetail requestPath isSameWithRequest`() = runTest {
        enqueueWithMockResponse(body = movieDetailJsonResponse)
        movieRemoteDataSource.getMovieDetail(TestConstants.movieDetailId)
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/movie/${TestConstants.movieDetailId}")
    }

    @Test
    fun `whenGetMovieDetail firstElement hasSameName`() = runTest {
        enqueueWithMockResponse(body = movieDetailJsonResponse)
        val result = movieRemoteDataSource.getMovieDetail(TestConstants.movieDetailId)
        assertThat(result.data!!.originalTitle).isEqualTo("Oppenheimer")
        assertThat(result.data!!.id).isEqualTo(TestConstants.movieDetailId)
    }

    // SearchMovies Test
    @Test
    fun `whenSearchMovie correct response is parsed into success result`() = runTest {
        enqueueWithMockResponse(body = searchMoviesJsonResponse)
        val result = movieRemoteDataSource.searchMovie(TestConstants.oppenheimerQuery, 1)
        assertThat(result).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `whenSearchMovie, malformed response returns json error result `() = runTest {
        enqueueWithMockResponse(body = searchMoviesErrorResponse)
        val result = movieRemoteDataSource.searchMovie(TestConstants.oppenheimerQuery, 1)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).exception).isInstanceOf(JsonDataException::class.java)
    }

    @Test
    fun `whenSearchMovie, error response returns http error result`() = runTest {
        enqueueWithMockResponse(responseCode = 400, body = searchMoviesJsonResponse)
        val result = movieRemoteDataSource.searchMovie(TestConstants.oppenheimerQuery, 1)
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).exception).isInstanceOf(HttpException::class.java)
    }

    @Test
    fun `whenSearchMovie requestPath isSameWithRequest`() = runTest {
        enqueueWithMockResponse(body = searchMoviesJsonResponse)
        movieRemoteDataSource.searchMovie(TestConstants.oppenheimerQuery, 1)
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/search/movie?query=${TestConstants.oppenheimerQuery}&page=1")
    }

    @Test
    fun `whenSearchMovie firstElement hasSameName`() = runTest {
        enqueueWithMockResponse(body = searchMoviesJsonResponse)
        val result = movieRemoteDataSource.searchMovie(TestConstants.oppenheimerQuery, 1)
        assertThat(result.data!!.first().originalTitle).isEqualTo("Oppenheimer")
    }

    private fun enqueueWithMockResponse(
        body: String, responseCode: Int = 200
    ) {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(responseCode).setBody(body)
        )
    }
}