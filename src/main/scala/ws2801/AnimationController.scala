package ws2801

trait AnimationController {
  protected val steps: Array[AnimationStep]
  protected val fps: Double

  protected var iteration: Long = 0
  protected var stepIteration: Long = 0

  def startup(pixels: Pixels, animationIteration: Long): Unit = {}

  def run(pixels: Pixels): Boolean = {
    val done = steps((iteration % steps.length).toInt).run(pixels, stepIteration)
    stepIteration += 1
    if (done) {
      iteration += 1
      stepIteration = 0
      steps((iteration % steps.length).toInt).startup(pixels, iteration / steps.length)
    }
    done && iteration % steps.length == 0
  }

  def gracefullyExit(pixels: Pixels, stepIteration: Long): Boolean = {
    true
  }
}
