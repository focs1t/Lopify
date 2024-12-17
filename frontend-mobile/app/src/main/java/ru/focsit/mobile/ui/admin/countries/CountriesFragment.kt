package ru.focsit.mobile.ui.admin.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.focsit.mobile.R
import ru.focsit.mobile.data.Country
import ru.focsit.mobile.ui.home.HomeViewModel
import ru.focsit.mobile.repository.admin.CountryRepository

class CountriesFragment : Fragment() {

    private lateinit var countryRepository: CountryRepository
    private lateinit var countriesListView: ListView
    private var countriesList: List<Country> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_countries, container, false)

        countriesListView = root.findViewById(R.id.countries_list_view)
        countryRepository = CountryRepository()

        // Загружаем список стран
        loadCountries()

        return root
    }

    private fun loadCountries() {
        countryRepository.getCountries { countries ->
            countries?.let {
                countriesList = it
                val adapter = CountryAdapter(requireContext(), countriesList)
                countriesListView.adapter = adapter
            }
        }
    }
}