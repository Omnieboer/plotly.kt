package histogram

import hep.dataforge.meta.invoke
import kscience.plotly.Plotly
import kscience.plotly.makeFile
import kscience.plotly.models.BarMode
import kscience.plotly.models.Histogram
import kscience.plotly.palettes.T10
import java.util.*

/**
 * - overlaying histograms
 * - use T10 as color palette
 * - change legend font size and color
 */
fun main() {
    val rnd = Random()
    val k = List(500) { rnd.nextDouble() }
    val x1 = k.map { it + 1 }.toList()
    val x2 = k.map { it + 1.1 }.toList()

    val trace1 = Histogram {
        x.set(x1)
        opacity = 0.5
        marker {
            color(T10.BLUE)
        }
    }

    val trace2 = Histogram {
        x.set(x2)
        opacity = 0.6
        marker {
            color(T10.PINK)
        }
    }

    val plot = Plotly.plot {
        traces(trace1, trace2)
        layout {
            title = "Overlaid Histogram"
            barmode = BarMode.overlay
            bargap = 0.1

            legend {
                font {
                    size = 16
                    color("black")
                }
            }
        }
    }

    plot.makeFile()
}