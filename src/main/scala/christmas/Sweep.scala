package christmas

import ws2801.{AnimationController, AnimationStep, HSV, Pixels}

class Sweep(override val fps: Double, rotations: Int = 2) extends AnimationController with AnimationStep {
  override protected val steps: Array[AnimationStep] = Array(
    new AnimationStep {
      override val startup: (Pixels, Long) => Unit = (_, _) => ()
      override val run: (Pixels, Long) => Boolean = (pixels, _) => {
        val goal = HSV(0, 1, 1)
        //noinspection FoldTrueAnd
        val done = (0 until pixels.count).foldLeft(true)((forAll, pixel) => {
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
        val goal = HSV((iteration / 2) % 360, 1, 1)
        (0 until pixels.count).foreach(pixel => {
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
