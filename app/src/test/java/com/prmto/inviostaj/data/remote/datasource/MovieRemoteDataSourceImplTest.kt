package com.prmto.inviostaj.data.remote.datasource

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.data.remote.api.TmdbApi
import com.prmto.inviostaj.data.remote.response.genreResponse
import com.prmto.inviostaj.data.remote.response.topRatedMoviesResponsePage1
import com.prmto.inviostaj.util.MainDispatcherRule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
class MovieRemoteDataSourceImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var movieRemoteDataSource: MovieRemoteDataSourceImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: TmdbApi
    private lateinit var moshi: Moshi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        api = Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    moshi
                )
            )
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(TmdbApi::class.java)

        movieRemoteDataSource = MovieRemoteDataSourceImpl(
            api = api
        )

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun response_whenGetTopRatedMovie_isNotNull() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(topRatedMoviesResponsePage1)
        )

        val result = api.getTopRatedMovies(
            page = 1
        )

        assertThat(result).isNotNull()
    }

    @Test
    fun requestPath_whenGetTopRatedMovie_isSameWithRequest() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(topRatedMoviesResponsePage1)
        )
        api.getTopRatedMovies(page = 1)
        val request = mockWebServer.takeRequest()

        assertThat(request.path).isEqualTo("/movie/top_rated?page=")
    }

    @Test
    fun firstElement_WhenGetTopRatedMovie_hasSameName() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(topRatedMoviesResponsePage1)
        )

        val result = api.getTopRatedMovies(page = 1)
        assertThat(result.results.first().originalTitle).isEqualTo("The Godfather")
    }

    @Test
    fun response_whenGetMovieGenre_isNotNull() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(genreResponse)
        )

        val result = api.getMovieGenreList()

        assertThat(result).isNotNull()
    }

    @Test
    fun requestPath_whenGetMovieGenre_isSameWithRequest() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(genreResponse)
        )
        api.getMovieGenreList()
        val request = mockWebServer.takeRequest()

        assertThat(request.path).isEqualTo("/genre/movie/list")
    }

    @Test
    fun firstElement_whenGetMovieGenre_hasSameName() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(genreResponse)
        )

        val result = api.getMovieGenreList()
        assertThat(result.genres[0].name).isEqualTo("Action")
        assertThat(result.genres[0].id).isEqualTo(28)
    }

}