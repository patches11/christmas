package christmas

import ws2801.{AnimationController, AnimationStep, HSV, Pixels}

class Chase(override val fps: Double) extends AnimationController with AnimationStep {
  private val colors = Array(
    HSV(0, 1, 1),
    HSV(120, 1, 1),
    HSV(240, 1, 1)
  )

  private val tail = 7

  override protected val steps: Array[AnimationStep] = Array(
    new AnimationStep {
      override val startup: (Pixels, Long) => Unit = (_, _) => {}

      override val run: (Pixels, Long) => Boolean = (pixels, stepIteration) => {
        (0 until pixels.count).foreach(pixel => {
          if ((pixel + stepIteration / 7) % tail == 0) {
            pixels.setPixel(pixel, colors((pixel + stepIteration / 7).toInt % colors.length))
          } else {
            pixels.darkenPixel(pixel, 0.025)
          }
        })
        pixels.show()
        stepIteration > 1000
      }
    }
  )


  override val startup: (Pixels, Long) => Unit = (_, _) => ()
  override val run: (Pixels, Long) => Boolean = (pixels, _) => run(pixels)
}
