package christmas

import ws2801.{AnimationController, AnimationStep, HSV, Pixels}

class FadeInOut(override val fps: Double, val mainIterations: Int = 10) extends AnimationController with AnimationStep {
  override protected val steps: Array[AnimationStep] = Array(
    new AnimationStep {
      override val startup: (Pixels, Long) => Unit = (pixels: Pixels, iteration: Long) => {
        (0 until pixels.count).foreach { pixel =>
          pixels.setPixel(pixel, HSV(((iteration + pixel) % 3) * 120, 1, 0))
        }
      }
      override val run: (Pixels, Long) => Boolean = (pixels, _) => {
        //noinspection FoldTrueAnd
        val done = (0 until pixels.count).foldLeft(true)((forAll, pixel) => {
          pixels.lightenPixel(pixel, 1 / (fps * 2)).v == 1 && forAll
        })
        pixels.show()
        done
      }
    },
    new AnimationStep {
      override val startup: (Pixels, Long) => Unit = (_, _) => {}
      override val run: (Pixels, Long) => Boolean = (pixels, iteration) => {
        pixels.show()
        iteration > fps * 5
      }
    },
    new AnimationStep {
      override val startup: (Pixels, Long) => Unit = (_, _) => {}
      override val run: (Pixels, Long) => Boolean = (pixels, _) => {
        //noinspection FoldTrueAnd
        val done = (0 until pixels.count).foldLeft(true)((forAll, pixel) => {
          pixels.darkenPixel(pixel, 1 / (fps * 2)).v == 0 && forAll
        })
        pixels.show()
        done
      }
    }
  )

  override protected var iteration: Long = 0
  override protected var stepIteration: Long = 0

  override val gracefullyExit: (Pixels, Long) => Boolean = steps.last.run
  override val startup: (Pixels, Long) => Unit = steps.head.startup

  override val run: (Pixels, Long) => Boolean = (pixels, _) => run(pixels) && iteration % mainIterations == 0
}
