package com.shindra.acafsxb.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun EmbeddedTabLayout(
    selectedIndex: Int,
    onIndexChange: (Int) -> Unit,
    nbOfTabs: Int,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(50),
    border: BorderStroke = BorderStroke(1.dp, Color.Gray),
    colors: EmbeddedTabLayoutColors = EmbeddedTabLayoutDefaults.colors(),
    tabContent: @Composable ColumnScope.(index: Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier
            .border(
                border = border,
                shape = shape
            )
            .clip(shape),
        containerColor = colors.backgroundColor,
        indicator = { tabPositions: List<TabPosition> ->
            FancyIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                indicatorColor = colors.indicatorColor,
                shape = shape
            )
        },
        divider = {}
    ) {
        (0 until nbOfTabs).forEach { index ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onIndexChange(index) },
                modifier = Modifier
                    .clip(shape)
                    .zIndex(2F)
                    .background(Color.Transparent),
                content = { tabContent(index) }
            )
        }
    }
}

@Preview
@Composable
private fun EmbeddedTabLayoutPreview() {
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    EmbeddedTabLayout(
        selectedIndex = selectedIndex,
        onIndexChange = { selectedIndex = it },
        nbOfTabs = 4,
        tabContent = { i: Int ->
            val selected = selectedIndex == i
            Text(
                text = "Tab $i",
                color = if (selected) Color.White else Color.Unspecified
            )
        }
    )
}

@Composable
private fun FancyIndicator(
    modifier: Modifier = Modifier,
    indicatorColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape,
) {
    Box(
        modifier
            .padding(1.dp)
            .clip(shape)
            .fillMaxSize()
            .background(color = indicatorColor)
            .zIndex(1f)
    )
}

object EmbeddedTabLayoutDefaults {
    @Composable
    fun colors(
        backgroundColor: Color = LocalContentColor.current,
        indicatorColor: Color = MaterialTheme.colorScheme.primary
    ) = EmbeddedTabLayoutColors(
        backgroundColor = backgroundColor,
        indicatorColor = indicatorColor
    )
}

class EmbeddedTabLayoutColors internal constructor(
    val backgroundColor: Color,
    val indicatorColor: Color
)