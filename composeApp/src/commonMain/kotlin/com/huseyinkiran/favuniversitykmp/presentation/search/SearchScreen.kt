package com.huseyinkiran.favuniversitykmp.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huseyinkiran.favuniversitykmp.domain.model.University
import com.huseyinkiran.favuniversitykmp.presentation.cards.UniversityCard
import com.huseyinkiran.favuniversitykmp.presentation.platform.appViewModel
import favuniversity.composeapp.generated.resources.Res
import favuniversity.composeapp.generated.resources.cd_clear_search
import favuniversity.composeapp.generated.resources.cd_search
import favuniversity.composeapp.generated.resources.error_occurred
import favuniversity.composeapp.generated.resources.loading_universities
import favuniversity.composeapp.generated.resources.no_result_found
import favuniversity.composeapp.generated.resources.search_min_char_warning
import favuniversity.composeapp.generated.resources.search_no_match
import favuniversity.composeapp.generated.resources.search_placeholder
import favuniversity.composeapp.generated.resources.title_search
import favuniversity.composeapp.generated.resources.try_again
import favuniversity.composeapp.generated.resources.universities_found
import org.jetbrains.compose.resources.stringResource
import org.koin.core.annotation.KoinExperimentalAPI

private val SearchBarBackground = Color(0xFFFFFFFF)
private val SearchBarFocusedBorder = Color(0xFFD8226C)
private val SearchBarUnfocusedBorder = Color(0xFFE0E0E0)
private val HintTextColor = Color(0xFF9E9E9E)
private val ResultCountColor = Color(0xFF757575)
private val SearchTextColor = Color(0xFF212121)

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: SearchViewModel = appViewModel()
    val state by viewModel.state.collectAsState()
    val expandedUniversityIds by viewModel.expandedUniversityIds.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    com.huseyinkiran.favuniversitykmp.presentation.platform.BackToBackgroundHandler()

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            }
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        SearchBar(
            query = state.query,
            onQueryChange = viewModel::onQueryChange,
            onClearQuery = viewModel::onClearQuery
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                LoadingContent()
            }

            state.errorMessage != null -> {
                ErrorContent(
                    message = state.errorMessage!!,
                    onRetry = viewModel::retry
                )
            }

            state.query.isEmpty() -> {
                InitialContent()
            }

            state.query.length < 2 -> {
                HintContent()
            }

            state.showEmptyState -> {
                EmptyContent(query = state.query)
            }

            else -> {
                SearchResults(
                    universities = state.filteredUniversities,
                    expandedUniversityIds = expandedUniversityIds,
                    onExpandToggle = viewModel::onUniversityExpandToggle,
                    onFavoriteToggle = viewModel::toggleFavorite
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(Res.string.search_placeholder),
                color = HintTextColor
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(Res.string.cd_search),
                tint = SearchBarFocusedBorder
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = query.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(Res.string.cd_clear_search),
                        tint = HintTextColor
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SearchBarFocusedBorder,
            unfocusedBorderColor = SearchBarUnfocusedBorder,
            focusedContainerColor = SearchBarBackground,
            unfocusedContainerColor = SearchBarBackground,
            focusedTextColor = SearchTextColor,
            unfocusedTextColor = SearchTextColor
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { keyboardController?.hide() }
        )
    )
}

@Composable
private fun SearchResults(
    universities: List<University>,
    expandedUniversityIds: Set<Int>,
    onExpandToggle: (Int) -> Unit,
    onFavoriteToggle: (University) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "${universities.size} " + stringResource(Res.string.universities_found),
            color = ResultCountColor,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
        ) {
            items(
                items = universities,
                key = { it.id }
            ) { university ->
                UniversityCard(
                    university = university,
                    isExpanded = university.id in expandedUniversityIds,
                    onExpandToggle = { onExpandToggle(university.id) },
                    onFavoriteToggle = { onFavoriteToggle(university) }
                )
            }
        }
    }
}

@Composable
private fun InitialContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = HintTextColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.title_search),
                fontSize = 16.sp,
                color = HintTextColor
            )
        }
    }
}

@Composable
private fun HintContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.search_min_char_warning),
            fontSize = 14.sp,
            color = HintTextColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EmptyContent(
    query: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.no_result_found),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(Res.string.search_no_match, query),
                fontSize = 14.sp,
                color = HintTextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = SearchBarFocusedBorder
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.loading_universities),
                fontSize = 14.sp,
                color = HintTextColor
            )
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.error_occurred),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                fontSize = 14.sp,
                color = HintTextColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onRetry) {
                Text(
                    text = stringResource(Res.string.try_again),
                    color = SearchBarFocusedBorder,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}