package ws2801

case class HSV(h: Double, s: Double, v: Double)

object HSV {
  implicit class HsvConversions(hsv: HSV) {
    def toRGB: RGB = {
      def f(n: Int): Double = {
        val k = (n + hsv.h / 60) % 6
        hsv.v - hsv.v * hsv.s * math.max(0, Array(k, 4 - k, 1).min)
      }

      RGB((f(5) * 255).toByte, (f(3) * 255).toByte, (f(1) * 255).toByte)
    }

    def darkenBy(d: Double): HSV = {
      HSV(hsv.h, hsv.s, math.max(hsv.v - d, 0))
    }

    def lightenBy(d: Double): HSV = {
      HSV(hsv.h, hsv.s, math.min(hsv.v + d, 1))
    }
  }
}