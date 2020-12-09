import ws2801.Pixels

object App {
  val count = 50

  def modThree(i: Int, c: Int): Byte = {
    val mThree = (System.currentTimeMillis() / 1000 / 20 + i) % 3
    if (mThree == c) 255.toByte else 0
  }

  def main(args: Array[String]): Unit = {

    val pixels = new Pixels(0, 0, count)

    while(true) {
      (0 until count).foreach { pixel =>
        pixels.setPixel(pixel, modThree(pixel, 0), modThree(pixel, 1), modThree(pixel, 2))
      }
      pixels.show()
      Thread.sleep(500)
    }

  }
}
