package christmas

import ws2801.{AnimationController, AnimationStep, Pixels}

class ApplicationAnimationController(override val fps: Double, pixels: Pixels) extends AnimationController {
  override protected val steps: Array[AnimationStep] = Array(
    new FadeInOut(fps),
    new Rainbow(fps),
    new Chase(fps),
    new Sweep(fps)
  )

  override def startup(pixels: Pixels, iteration: Long): Unit = steps.head.startup(pixels, iteration)
}
