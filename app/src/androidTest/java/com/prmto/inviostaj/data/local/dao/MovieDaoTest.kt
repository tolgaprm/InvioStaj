package com.prmto.inviostaj.data.local.dao

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.data.TestConstants
import com.prmto.inviostaj.data.local.InvioDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class MovieDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: InvioDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMovie_whenInsertMovie_successfullyAddedMovie() = runTest {
        val movie = TestConstants.movie
        dao.insertFavoriteMovie(movie)
        val result = dao.getAllFavoriteMovies()
        assertThat(result).contains(movie)
    }

    @Test
    fun insertMovie_whenRemoveMovie_successfullyRemoveMovie() = runTest {
        val movie = TestConstants.movie
        dao.insertFavoriteMovie(movie)
        dao.deleteFavoriteMovie(movie)
        val result = dao.getAllFavoriteMovies()
        assertThat(result).doesNotContain(movie)
    }
}