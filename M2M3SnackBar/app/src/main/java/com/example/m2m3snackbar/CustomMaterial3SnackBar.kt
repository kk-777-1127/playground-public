package com.example.m2m3snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomMaterial3SnackBar(
    message: String,
    modifier: Modifier = Modifier,
    showIcon: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.inverseSurface,
    contentColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .widthIn(max = 700.dp),
        shape = RoundedCornerShape(4.dp),
        color = backgroundColor,
        contentColor = contentColor,
        tonalElevation = 6.dp // 標準Snackbarと同じ Level 3の値
    ) {
        Box(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (showIcon) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = message,
                    color = contentColor,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun CustomMaterial3SnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    showIcon: Boolean = false
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
    ) { snackbarData ->
        CustomMaterial3SnackBar(
            message = snackbarData.visuals.message,
            showIcon = showIcon
        )
    }
}