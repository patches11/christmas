package christmas

import ws2801.{AnimationController, AnimationStep, Pixels}

class ApplicationAnimationController(override val fps: Double, pixels: Pixels) extends AnimationController {
  override protected val steps: Array[AnimationStep] = Array(
    new FadeInOut(fps),
    new Chase(fps)
  )
  override protected var iteration: Long = 0
  override protected var stepIteration: Long = 0

  override val startup: (Pixels, Long) => Unit = (pixels, _) => steps.head.startup(pixels, iteration)
  override val gracefullyExit: (Pixels, Long) => Boolean = (_, _) => true


}
