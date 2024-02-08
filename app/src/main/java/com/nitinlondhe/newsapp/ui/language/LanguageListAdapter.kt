package com.nitinlondhe.newsapp.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nitinlondhe.newsapp.R
import com.nitinlondhe.newsapp.data.model.Language
import com.nitinlondhe.newsapp.data.model.SelectionState
import com.nitinlondhe.newsapp.databinding.LanguageItemLayoutBinding
import com.nitinlondhe.newsapp.utils.ItemClickListener

class LanguageListAdapter(private val languageList: ArrayList<Language>) :
    RecyclerView.Adapter<LanguageListAdapter.LanguageViewHolder>() {

    lateinit var itemClickListener: ItemClickListener<Any>

    var selectedLanguageState = SelectionState()

        class LanguageViewHolder(private val binding: LanguageItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(language: Language, itemClickListener: ItemClickListener<Any>, tracker: SelectionState) {
            binding.btnSource.text = language.name
            binding.btnSource.setBackgroundColor(
                ContextCompat.getColor(
                    binding.btnSource.context, R.color.orage
                )
            )

            if (tracker.selectedLanguage.contains(language)) {
                binding.btnSource.setBackgroundColor(ContextCompat.getColor(binding.btnSource.context, R.color.purple_700))
            }

            binding.btnSource.setOnClickListener {
                itemClickListener(bindingAdapterPosition, language)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(
            LanguageItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = languageList.size

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(languageList[position], itemClickListener, selectedLanguageState)


    }

    fun addLanguage(list: List<Language>) {
        languageList.addAll(list)
    }

}