package com.hadiyarajesh.easynotes.ui.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hadiyarajesh.easynotes.R
import com.hadiyarajesh.easynotes.database.entity.Note
import com.hadiyarajesh.easynotes.ui.components.EmptyViewWithText
import com.hadiyarajesh.easynotes.ui.components.LoadingProgressBar
import com.hadiyarajesh.easynotes.ui.components.NotesTopBar
import com.hadiyarajesh.easynotes.ui.components.RetryItem

@Composable
fun NotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val notes = remember { notesViewModel.notes }.collectAsLazyPagingItems()

    Scaffold(
        topBar = { NotesTopBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AllNotesView(
                notes = notes,
                onClick = {}
            )
        }
    }
}

@Composable
private fun AllNotesView(
    modifier: Modifier = Modifier,
    notes: LazyPagingItems<Note>,
    onClick: (Note) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(notes) { item ->
            item?.let { note ->
                NoteItem(note = note, onClick = onClick)
            }
        }

        notes.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingProgressBar(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingProgressBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && notes.itemCount < 1 -> {
                    item {
                        EmptyViewWithText(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(8.dp),
                            text = stringResource(id = R.string.empty_message),
                        )
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        RetryItem(
                            modifier = Modifier.fillMaxSize(),
                            onRetryClick = { retry() }
                        )
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        RetryItem(
                            modifier = Modifier.fillMaxSize(),
                            onRetryClick = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: (Note) -> Unit,
) {
    Column(
        modifier = modifier.clickable { onClick(note) }
    ) {
        Text(text = note.title)
        note.description?.let { description ->
            Text(text = description)
        }
    }
}
