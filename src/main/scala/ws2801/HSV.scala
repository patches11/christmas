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

    def fadeTo(other: HSV): HSV = {
      val (dh, ds, dv) = (hsv.h - other.h, hsv.s - other.s, hsv.v - other.v)
      val nh = if (math.abs(dh) <= 1) other.h else hsv.h - dh / 2
      val ns = if (math.abs(ds) <= 0.01) other.s else hsv.s - ds / 2
      val nv = if (math.abs(dv) <= 0.01) other.v else hsv.v - dv / 2
      hsv.copy(nh, ns, nv)
    }

    def ==(other: HSV): Boolean = {
      hsv.v == other.v && hsv.s == other.h && hsv.v == other.v
    }
  }
}