package com.example.androidtest

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.preference.PreferenceManager
import com.example.androidtest.di.DbRepository
import com.example.androidtest.ui.theme.AndroidTestTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {

    @Inject
    lateinit var dbRepository: DbRepository
    private lateinit var darkThemeState: MutableState<Boolean>

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        darkThemeState =
            mutableStateOf(preferences.getBoolean(Constants.PREFERENCE_DARK_MODE, false))
        setContent {
            AndroidTestTheme(darkTheme = darkThemeState.value) {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(id = R.string.options_access_settings)) },
                            navigationIcon = {
                                IconButton(onClick = { onNavigateUp() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = ""
                                    )
                                }
                            })
                    }
                ) { contentPadding ->
                    SettingsView(
                        modifier = Modifier.padding(contentPadding),
                        darkThemeState = darkThemeState,
                        dbRepository = dbRepository,
                        preferences = preferences
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    darkThemeState: MutableState<Boolean>,
    dbRepository: DbRepository?,
    preferences: SharedPreferences?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        SwitchPreference(
            modifier = modifier,
            title = stringResource(
                id = if (darkThemeState.value) {
                    R.string.settings_disable_dark_mode
                } else {
                    R.string.settings_enable_dark_mode
                }
            ),
            checked = darkThemeState.value,
            onCheckedChange = { checked ->
                preferences?.let {
                    with(it.edit()) {
                        putBoolean(Constants.PREFERENCE_DARK_MODE, checked)
                        apply()
                    }
                }

                darkThemeState.value = checked
            }
        )

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.default_padding)),
            onClick = { dbRepository?.clearTable() }) {
            Text(text = stringResource(id = R.string.settings_clear_data))
        }
    }

}

@Composable
fun SwitchPreference(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.default_padding)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = if (!enabled) {
                MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
            } else {
                Color.Unspecified
            },
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = true
        )
    }
}

@Preview
@Composable
fun SettingsViewPreview() {
    SettingsView(darkThemeState = remember {
        mutableStateOf(false)
    }, dbRepository = null, preferences = null)
}