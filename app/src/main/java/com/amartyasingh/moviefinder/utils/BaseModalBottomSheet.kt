package com.amartyasingh.moviefinder.utils

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseModalBottomSheet(
    content: @Composable ColumnScope.() -> Unit = {},
    isShowingState: Boolean = true,
    onDismissRequest: () -> Unit
) {
    if(isShowingState) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismissRequest = onDismissRequest,
            content = content
        )
    }
}