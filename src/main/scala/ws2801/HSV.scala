package ws2801

case class HSV(h: Double, s: Double, v: Double)

object HSV {
  implicit class HsvConversions(hsv: HSV) {
    def toRGB: RGB = {
      def f(n: Int): Double = {
        val k = (n + hsv.h / 60) % 6
        hsv.v - hsv.v * hsv.s * math.max(0, Array(k, 4 - k, 1).min)
      }

      println((f(5), f(3), f(1)))
      RGB((f(5) * 255).toByte, (f(3) * 255).toByte, (f(1) * 255).toByte)
    }

    def darkenBy(d: Double): HSV = {
      HSV(hsv.h, hsv.s, math.max(hsv.v - d, 0))
    }
  }
}