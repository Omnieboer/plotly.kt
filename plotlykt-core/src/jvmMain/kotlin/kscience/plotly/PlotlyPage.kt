package kscience.plotly

import kotlinx.html.*
import kotlinx.html.stream.createHTML

/**
 * A custom HTML fragment including plotly container reference
 */
class PlotlyFragment(val render: FlowContent.(renderer: PlotlyRenderer) -> Unit)

/**
 * A complete page including headers and title
 */
data class PlotlyPage(
    val headers: Collection<HtmlFragment>,
    val fragment: PlotlyFragment,
    val title: String = "Plotly.kt",
    val renderer: PlotlyRenderer = StaticPlotlyRenderer
) {
    fun render(): String = createHTML().html {
        head {
            meta {
                charset = "utf-8"
            }
            title(this@PlotlyPage.title)
            headers.distinct().forEach { it.visit(consumer) }
        }
        body {
            fragment.render(this, renderer)
        }
    }
}

fun Plotly.fragment(content: FlowContent.(renderer: PlotlyRenderer) -> Unit): PlotlyFragment = PlotlyFragment(content)

/**
 * Create a complete page including plots
 */
fun Plotly.page(
    vararg headers: HtmlFragment = arrayOf(cdnPlotlyHeader),
    title: String = "Plotly.kt",
    renderer: PlotlyRenderer = StaticPlotlyRenderer,
    content: FlowContent.(renderer: PlotlyRenderer) -> Unit
) = PlotlyPage(headers.toList(), fragment(content), title, renderer)

/**
 * Convert an html plot fragment to page
 */
fun PlotlyFragment.toPage(
    vararg headers: HtmlFragment = arrayOf(cdnPlotlyHeader),
    title: String = "Plotly.kt",
    renderer: PlotlyRenderer = StaticPlotlyRenderer
) = PlotlyPage(headers.toList(), this, title, renderer)

/**
 * Convert a plot to the sigle-plot page
 */
fun Plot.toPage(
    vararg headers: HtmlFragment = arrayOf(cdnPlotlyHeader),
    config: PlotlyConfig = PlotlyConfig.empty(),
    title: String = "Plotly.kt",
    renderer: PlotlyRenderer = StaticPlotlyRenderer
): PlotlyPage = PlotlyFragment {
    plot(this@toPage, config = config, renderer = renderer)
}.toPage(*headers, title = title)