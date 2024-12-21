package ru.focsit.mobile.data

data class Country(
    val countryId: Long,
    val countryName: String,
    val users: List<User>,
    val artists: List<Artist>
)