package com.shindra.acafsxb.feature.balance.navigation.components

import android.graphics.Typeface
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.horizontalLegend
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.DefaultColors
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.shindra.acafsxb.feature.balance.navigation.bo.BalanceGraphUi
import com.shindra.acafsxb.feature.balance.navigation.bo.GraphLine

@Composable
internal fun BalanceGraph(
    graphUi: BalanceGraphUi?,
    balanceLine: GraphLine?,
    modifier: Modifier = Modifier
) {
    if (graphUi == null) return

    val finalesLines = buildList {
        addAll(graphUi.envelops)
        if (balanceLine != null) add(balanceLine)
    }

    val model = entryModelOf(*finalesLines.map { it.points }.toTypedArray())

    ProvideChartStyle(rememberChartStyle(finalesLines.map { it.color })) {
        Chart(
            chart = lineChart(
                axisValuesOverrider = AxisValuesOverrider.fixed(
                    minX = graphUi.minX,
                    maxX = graphUi.maxX,
                    minY = graphUi.minY,
                    maxY = graphUi.maxY
                ),
                lines = currentChartStyle.lineChart.lines.map {
                    it.copy(
                        pointConnector = DefaultPointConnector(
                            cubicStrength = 0f
                        ),
                        lineBackgroundShader = null
                    )
                }
            ),
            model = model,
            modifier = modifier.fillMaxWidth(),
            legend = rememberLegend(finalesLines),
            startAxis = rememberStartAxis(
                valueFormatter = DecimalFormatAxisValueFormatter("#;−#"),
                tickLength = 18.dp,
                itemPlacer = remember { AxisItemPlacer.Vertical.default(4) },
            ),
            bottomAxis = rememberBottomAxis(),
            getXStep = { with(graphUi) { (maxX - minX) / 4 } }
        )
    }
}

@Composable
private fun rememberLegend(lines: List<GraphLine>) = horizontalLegend(
    items = lines.map {
        legendItem(
            icon = shapeComponent(Shapes.pillShape, it.color),
            label = textComponent(
                color = it.color,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = it.title,
        )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding
)

@Composable
private fun rememberChartStyle(
    columnChartColors: List<Color>,
    lineChartColors: List<Color>
): ChartStyle {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    return remember(columnChartColors, lineChartColors, isSystemInDarkTheme) {
        val defaultColors = if (isSystemInDarkTheme) DefaultColors.Dark else DefaultColors.Light
        ChartStyle(
            ChartStyle.Axis(
                axisLabelColor = Color(defaultColors.axisLabelColor),
                axisGuidelineColor = Color(defaultColors.axisGuidelineColor),
                axisLineColor = Color(defaultColors.axisLineColor),
            ),
            ChartStyle.ColumnChart(
                columnChartColors.map { columnChartColor ->
                    LineComponent(
                        columnChartColor.toArgb(),
                        DefaultDimens.COLUMN_WIDTH,
                        Shapes.roundedCornerShape(DefaultDimens.COLUMN_ROUNDNESS_PERCENT),
                    )
                },
            ),
            ChartStyle.LineChart(
                lineChartColors.map { lineChartColor ->
                    LineChart.LineSpec(
                        lineColor = lineChartColor.toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            Brush.verticalGradient(
                                listOf(
                                    lineChartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    lineChartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                                ),
                            ),
                        ),
                    )
                },
            ),
            ChartStyle.Marker(),
            Color(defaultColors.elevationOverlayColor),
        )
    }
}

@Composable
internal fun rememberChartStyle(chartColors: List<Color>) =
    rememberChartStyle(columnChartColors = chartColors, lineChartColors = chartColors)

private val legendItemLabelTextSize = 12.sp
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 4.dp
private val legendItemSpacing = 16.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding =
    dimensionsOf(top = legendTopPaddingValue, bottom = legendTopPaddingValue)

@Composable
@Preview(showBackground = true)
fun BalanceGraphPreview() {

    val graphUi = BalanceGraphUi(
        minY = 500f,
        maxY = 1044f,
        minX = 0.87f,
        maxX = 1.22f,
        envelops = listOf(
            GraphLine(
                title = "U",
                points = listOf(
                    entryOf(0.89f, 500f),
                    entryOf(0.89f, 885f),
                    entryOf(0.9f, 910f),
                    entryOf(1.03f, 910f),
                    entryOf(1.03f, 500f)
                ),
                color = Color(0xff91b1fd)
            ),
            GraphLine(
                title = "N",
                points = listOf(
                    entryOf(0.89f, 500f),
                    entryOf(0.89f, 885f),
                    entryOf(0.98f, 1043f),
                    entryOf(1.2f, 1043f),
                    entryOf(1.2f, 500f)
                ),
                color = Color(0xff8fdaff)
            )
        ),
        xSteps = 4f
    )

    val line = GraphLine(
        title = "Centrage",
        points = listOf(
            entryOf(0.973f, 704f),
            entryOf(0.879f, 693f),
        ),
        color = Color(0xfffab94d)
    )

    BalanceGraph(
        graphUi = graphUi,
        balanceLine = line,
        modifier = Modifier.height(400.dp)
    )
}