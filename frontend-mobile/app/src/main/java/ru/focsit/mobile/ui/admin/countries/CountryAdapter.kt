package ru.focsit.mobile.ui.admin.countries

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ru.focsit.mobile.R
import ru.focsit.mobile.data.Country

class CountryAdapter(private val context: Context, private val countries: List<Country>) : BaseAdapter() {

    override fun getCount(): Int = countries.size

    override fun getItem(position: Int): Any = countries[position]

    override fun getItemId(position: Int): Long = countries[position].countryId

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_country, parent, false)
        val country = getItem(position) as Country

        val countryNameTextView: TextView = view.findViewById(R.id.country_name)
        countryNameTextView.text = country.countryName

        return view
    }
}