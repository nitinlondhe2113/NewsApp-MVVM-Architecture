package com.nitinlondhe.newsapp.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nitinlondhe.newsapp.R
import com.nitinlondhe.newsapp.data.model.Country
import com.nitinlondhe.newsapp.databinding.SourceItemLayoutBinding
import com.nitinlondhe.newsapp.utils.ItemClickListener

class CountryListAdapter( private val countryList: ArrayList<Country>):
RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<Any>

    class CountryViewHolder(private val binding: SourceItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country, itemClickListener: ItemClickListener<Any>){
            binding.btnSource.text = country.name
            binding.btnSource.setBackgroundColor(
                ContextCompat.getColor(
                    binding.btnSource.context, R.color.pink
                )
            )

            binding.btnSource.setOnClickListener {
                itemClickListener(bindingAdapterPosition, country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            SourceItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countryList[position], itemClickListener)
    }

    fun addCountry(list: List<Country>) {
        countryList.addAll(list)
    }

}