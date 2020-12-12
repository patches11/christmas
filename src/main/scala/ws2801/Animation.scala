package ws2801

trait Animation {

  def startup(pixels: Pixels, iteration: Long): Boolean

  val steps: Array[(Pixels, Long) => Boolean]
}
