package com.example.courskotlin.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.example.courskotlin.ui.theme._2024_10_cdanTheme
import com.example.courskotlin.viewmodel.MainViewModel
import androidx.compose.ui.text.input.ImeAction


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    // view model with is preview for fake data
    val mockViewModel = MainViewModel(isPreview = true)

    _2024_10_cdanTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(
                modifier = Modifier.padding(innerPadding),
                mainViewModel = mockViewModel
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .padding(top = 64.dp, start = 16.dp, end = 16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                singleLine = true,
                onValueChange = {
                    searchQuery = it
                    mainViewModel.searchGames(it.text)
                },
                label = { Text("Search a game...") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        mainViewModel.searchGames(searchQuery.text)
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text("Categories")
                Button(onClick = {
                    focusManager.clearFocus()
                    mainViewModel.loadTopGames("best_of_the_year")
                }) {
                    Text("Best of the Year")
                }
                Button(onClick = {
                    focusManager.clearFocus()
                    mainViewModel.loadTopGames("popular_in_2023")
                }) {
                    Text("Popular in 2023")
                }
                Button(onClick = {
                    focusManager.clearFocus()
                    mainViewModel.loadTopGames("top_250")
                }) {
                    Text("All Time Top 250")
                }
            }

            LazyColumn {
                items(mainViewModel.dataList.value) { game ->
                    PictureRowItem(data = game, mainViewModel = mainViewModel)
                }
            }
        }
    }
}
