import ws2801.{AnimationStep, HSV, Pixels}


object App {
  val count = 50
  val fps: Double = 30
  val sleep: Double = 1000 / fps

  val twinkleRgb: Array[AnimationStep] = Array(
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
        iteration > fps * 10
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

  def main(args: Array[String]): Unit = {
    val pixels = new Pixels(0, 0, count)

    var iteration: Long = 0
    var stepIteration = 0
    twinkleRgb(0).startup(pixels, iteration)
    while(true) {
      val done = twinkleRgb((iteration % 3).toInt).run(pixels, stepIteration)
      stepIteration += 1
      if (done) {
        iteration += 1
        stepIteration = 0
        twinkleRgb((iteration % 3).toInt).startup(pixels, iteration / 3)
      }
      Thread.sleep(sleep.toLong)
    }

  }
}
