import christmas.ApplicationAnimationController
import ws2801.{AnimationStep, HSV, Pixels}


object App {
  val count = 50
  val fps: Double = 30
  val sleep: Double = 1000 / fps

  def main(args: Array[String]): Unit = {
    val pixels = new Pixels(0, 0, count)

    val mainController = new ApplicationAnimationController(fps, pixels)

    mainController.startup(pixels, 0)
    var externalIteration: Long = 0
    while(true) {
      val start = System.currentTimeMillis()
      mainController.run(pixels)
      val runTime = System.currentTimeMillis() - start
      Thread.sleep(math.max(sleep.toLong - runTime, 0))
      if (externalIteration % 100 == 0) {
        println(s"Iteration $externalIteration Run-Time $runTime ms Calculated FPS ${"%02.2f".format(if (runTime > sleep) 1000.toDouble / runTime else fps)}")
      }
      externalIteration += 1
    }

  }
}
