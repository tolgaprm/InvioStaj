package com.prmto.inviostaj.data.remote.response

val movieDetailJsonResponse = """
    {
        "adult": false,
        "backdrop_path": "/fm6KqXpk3M2HVveHwCrBSSBaO0V.jpg",
        "belongs_to_collection": null,
        "budget": 100000000,
        "genres": [
            {
                "id": 18,
                "name": "Drama"
            },
            {
                "id": 36,
                "name": "History"
            }
        ],
        "homepage": "http://www.oppenheimermovie.com",
        "id": 872585,
        "imdb_id": "tt15398776",
        "original_language": "en",
        "original_title": "Oppenheimer",
        "overview": "The story of J. Robert Oppenheimer’s role in the development of the atomic bomb during World War II.",
        "popularity": 873.86,
        "poster_path": "/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
        "production_companies": [
            {
                "id": 9996,
                "logo_path": "/3tvBqYsBhxWeHlu62SIJ1el93O7.png",
                "name": "Syncopy",
                "origin_country": "GB"
            },
            {
                "id": 33,
                "logo_path": "/8lvHyhjr8oUKOOy2dKXoALWKdp0.png",
                "name": "Universal Pictures",
                "origin_country": "US"
            },
            {
                "id": 507,
                "logo_path": "/aRmHe6GWxYMRCQljF75rn2B9Gv8.png",
                "name": "Atlas Entertainment",
                "origin_country": "US"
            }
        ],
        "production_countries": [
            {
                "iso_3166_1": "GB",
                "name": "United Kingdom"
            },
            {
                "iso_3166_1": "US",
                "name": "United States of America"
            }
        ],
        "release_date": "2023-07-19",
        "revenue": 552000000,
        "runtime": 181,
        "spoken_languages": [
            {
                "english_name": "English",
                "iso_639_1": "en",
                "name": "English"
            },
            {
                "english_name": "German",
                "iso_639_1": "de",
                "name": "Deutsch"
            }
        ],
        "status": "Released",
        "tagline": "The world forever changes.",
        "title": "Oppenheimer",
        "video": false,
        "vote_average": 8.301,
        "vote_count": 1620
    }
""".trimIndent()

val movieDetailErrorJsonResponse = movieDetailJsonResponse.removePrefix("{")