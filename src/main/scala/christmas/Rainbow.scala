package christmas

import ws2801.{AnimationController, AnimationStep, HSV, Pixels}

class Rainbow(override val fps: Double, rotations: Int = 2) extends AnimationController with AnimationStep {
  private def pixelToH(pixel: Int, count: Int, iteration: Long): Double = {
    ((pixel + iteration) * 360 / count / 2) % 360
  }

  override protected val steps: Array[AnimationStep] = Array(
    new AnimationStep {
      override val startup: (Pixels, Long) => Unit = (_, _) => ()
      override val run: (Pixels, Long) => Boolean = (pixels, _) => {
        //noinspection FoldTrueAnd
        val done = (0 until pixels.count).foldLeft(true)((forAll, pixel) => {
          val goal = HSV(pixelToH(pixel, pixels.count, 0), 1, 1)
          val current = pixels.getPixel(pixel)
          if (current == goal) {
            forAll
          } else {
            pixels.setPixel(pixel, current.fadeTo(goal))
            false
          }
        })
        pixels.show()
        done
      }
    },
    new AnimationStep {
      override val startup: (Pixels, Long) => Unit = (_, _) => {}
      override val run: (Pixels, Long) => Boolean = (pixels, iteration) => {
        (0 until pixels.count).foreach(pixel => {
          val goal = HSV(pixelToH(pixel, pixels.count, iteration), 1, 1)
          pixels.setPixel(pixel, goal)
        })
        pixels.show()
        iteration > 360 * rotations
      }
    }
  )

  override val startup: (Pixels, Long) => Unit = (_, _) => ()
  override val run: (Pixels, Long) => Boolean = (pixels, _) => run(pixels)
}
