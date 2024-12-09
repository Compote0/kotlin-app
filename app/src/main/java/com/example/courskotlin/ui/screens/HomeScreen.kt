package com.example.courskotlin.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.courskotlin.ui.theme._2024_10_cdanTheme
import com.example.courskotlin.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    val mockViewModel = MainViewModel(isPreview = true)
    mockViewModel.loadFakeData()

    _2024_10_cdanTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HomeScreen(
                modifier = Modifier.padding(innerPadding),
                mainViewModel = mockViewModel
            )
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val isLoadingMore = mainViewModel.runInProgress.value
    var isRefreshing by remember { mutableStateOf(false) }
    var selectedSort by remember { mutableStateOf("relevance") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var isTopVisible by remember { mutableStateOf(true) }

    val progressIndicatorColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    LaunchedEffect(Unit) {
        if (mainViewModel.homeGamesList.value.isEmpty()) {
            mainViewModel.loadRandomGamesForHome()
        }
    }


    LaunchedEffect(listState.firstVisibleItemIndex) {
        isTopVisible = listState.firstVisibleItemIndex == 0
    }

    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (isTopVisible) {
                Spacer(modifier = Modifier.size(40.dp))

                Text(
                    text = "Top Picks",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Sort by: ${selectedSort.replaceFirstChar { it.uppercase() }}",
                        modifier = Modifier
                            .clickable { isDropdownExpanded = true }
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        listOf(
                            "Relevance", "Date Added", "Name", "Release Date",
                            "Popularity", "Average Rating"
                        ).forEach { sortOption ->
                            DropdownMenuItem(
                                text = { Text(text = sortOption, color = MaterialTheme.colorScheme.onSurface) },
                                onClick = {
                                    selectedSort = sortOption.lowercase()
                                    mainViewModel.loadSortedGames(selectedSort)
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            // SwipeRefresh Wrapper
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    isRefreshing = true
                    mainViewModel.refreshHomeGames { isRefreshing = false }
                }
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 6.dp,
                        end = 6.dp,
                        bottom = 120.dp
                    )
                ) {
                    if (mainViewModel.homeGamesList.value.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                            )
                        }
                    } else {
                        items(mainViewModel.homeGamesList.value) { game ->
                            PictureRowItem(data = game, mainViewModel = mainViewModel)
                        }
                    }

                    if (isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = progressIndicatorColor,
                                    modifier = Modifier.size(30.dp),
                                    strokeWidth = 4.dp
                                )
                            }
                        }
                    }
                }
            }

            // load more games on scroll to bottom
            LaunchedEffect(listState) {
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { lastVisibleItemIndex ->
                        val totalItems = mainViewModel.homeGamesList.value.size
                        if (lastVisibleItemIndex == totalItems - 1 && !isLoadingMore && !mainViewModel.isEndReached) {
                            mainViewModel.loadMoreGamesForHome()
                        }
                    }
            }
        }
    }
}
