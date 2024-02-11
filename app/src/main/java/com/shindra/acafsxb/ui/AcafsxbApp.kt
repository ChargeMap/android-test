package com.shindra.acafsxb.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.shindra.acafsxb.R
import com.shindra.acafsxb.navigation.AcafsxbNavHost

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun AcafsxbApp(
    appState: AcafsxbAppState = rememberAcafsxbAppState()
) {
    var toolbarTitle by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Toolbar(
                title = toolbarTitle,
                hasNavUp = appState.shouldShowNavUp,
                onNavUpClick = { appState.navigateUp() }
            )

        }
    ) {
        AcafsxbNavHost(
            modifier = Modifier.padding(it),
            navController = appState.navController,
            onTitleChange = { newTitle ->
                toolbarTitle = newTitle
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    title: String,
    hasNavUp: Boolean,
    onNavUpClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (hasNavUp) {
                IconButton(onClick = { onNavUpClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_navup),
                        contentDescription = null
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}