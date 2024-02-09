package com.nitinlondhe.newsapp.ui.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nitinlondhe.newsapp.R
import com.nitinlondhe.newsapp.data.model.Language
import com.nitinlondhe.newsapp.data.model.SelectionState
import com.nitinlondhe.newsapp.ui.base.ShowError
import com.nitinlondhe.newsapp.ui.base.ShowLoading
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.ui.theme.pink

@Composable
fun LanguageListRoute(
    onLanguageClick: (languageId: String) -> Unit,
    languageViewModel: LanguageListViewModel = hiltViewModel()
) {
    val languageUiState by languageViewModel.languageUiState.collectAsStateWithLifecycle()
    var selectionState by remember { mutableStateOf(SelectionState()) }

    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText(stringResource(R.string.language))
        TitleText(stringResource(R.string.language_info))
        LanguageScreen(languageUiState,
            selectionState = selectionState,
            onSelectionClick = { it ->
                selectionState = selectionState.copy(
                    selectedLanguage = if (selectionState.selectedLanguage.contains(it)) {
                        selectionState.selectedLanguage - it
                    } else {
                        if (selectionState.selectedLanguage.size < 2) {
                            selectionState.selectedLanguage + it
                        } else {
                            selectionState.selectedLanguage
                        }
                    }
                )
                val listLanguage: List<Language> = selectionState.selectedLanguage
                if (listLanguage.size == 2) {
                    val languageString = listLanguage.joinToString(",") { it.id.toString() }
                    onLanguageClick(languageString)
                }
            })
    }

}

@Composable
fun LanguageScreen(
    uiState: UiState<List<Language>>,
    selectionState: SelectionState,
    onSelectionClick: (languageId: Language) -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            LanguageList(uiState.data, selectionState, onSelectionClick)
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
fun LanguageList(
    languages: List<Language>,
    selectionState: SelectionState,
    onSelectionClick: (languageId: Language) -> Unit
) {

    LazyColumn {
        items(languages.size) { index ->
            Language(
                language = languages[index],
                isSelected = selectionState.selectedLanguage.contains(languages[index]),
                onSelectionClick
            )
        }
    }
}

@Composable
fun Language(
    language: Language,
    isSelected: Boolean,
    onSelectionClick: (languageId: Language) -> Unit
) {
    Button(
        onClick = { onSelectionClick(language) },
        colors = ButtonDefaults.buttonColors(
            containerColor = pink
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language.name,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Blue,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

    }
}

@Composable
fun TitleText(stringTitle: String) {
    Text(
        text = stringTitle,
        style = if (stringTitle == stringResource(R.string.language)) {
            MaterialTheme.typography.labelLarge
        } else {
            MaterialTheme.typography.bodySmall
        },
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier
            .padding(4.dp)
    )
}
