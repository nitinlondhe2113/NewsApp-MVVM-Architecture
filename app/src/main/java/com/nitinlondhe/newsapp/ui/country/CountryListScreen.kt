package com.nitinlondhe.newsapp.ui.country

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nitinlondhe.newsapp.R
import com.nitinlondhe.newsapp.data.model.Country
import com.nitinlondhe.newsapp.ui.base.ShowError
import com.nitinlondhe.newsapp.ui.base.ShowLoading
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.ui.theme.orage

@Composable
fun CountryListRoute(
    onCountryClick: (countryId: String) -> Unit,
    countryViewModel: CountryListViewModel = hiltViewModel()
) {
    val countryUiState by countryViewModel.countryUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText()
        CountryScreen(countryUiState, onCountryClick)
    }
}

@Composable
fun CountryScreen(uiState: UiState<List<Country>>, onNewsClick: (countryId: String) -> Unit) {
    when (uiState) {
        is UiState.Success -> {
            CountryList(uiState.data, onNewsClick)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            ShowError(uiState.message)
        }
    }
}

@Composable
fun CountryList(countrys: List<Country>, onNewsClick: (countryId: String) -> Unit) {
    LazyColumn {
        items(countrys.size) { index ->
            Country(country = countrys[index], onNewsClick)
        }
    }
}

@Composable
fun Country(country: Country, onNewsClick: (countryId: String) -> Unit) {
    Button(
        onClick = { onNewsClick(country.id) },
        colors = ButtonDefaults.buttonColors(
            containerColor = orage
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = country.name,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TitleText() {
    Text(
        text = stringResource(R.string.coutries),
        style = MaterialTheme.typography.labelLarge,
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier
            .padding(4.dp)
    )
}

