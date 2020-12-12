package ws2801

import com.diozero.api.{SpiClockMode, SpiDevice}

class Pixels(spiPort: Int, spiDevice: Int, val count: Int) {
  val clockFrequency = 1000000
  val clockMode = SpiClockMode.MODE_0
  val lsbFirst = false

  val device = new SpiDevice(spiPort, spiDevice, clockFrequency, clockMode, lsbFirst)
  val pixels = Array.fill(count)(HSV(0, 0, 0))
  val array = new Array[Byte](count * 3)

  sys.addShutdownHook {
    device.close()
  }

  def show(): Unit = {
    device.write(array)
  }

  def setPixel(pixel: Int, hsv: HSV): Unit = {
    pixels.update(pixel, hsv)
    val RGB(r, g, b) = hsv.toRGB
    array.update(pixel * 3, r)
    array.update(pixel * 3 + 1, g)
    array.update(pixel * 3 + 2, b)
  }

  def getPixel(pixel: Int): HSV = {
    pixels(pixel)
  }

  def darkenPixel(pixel: Int, by: Double): HSV = {
    val darkened = getPixel(pixel).darkenBy(by)
    setPixel(pixel, darkened)
    darkened
  }


  def lightenPixel(pixel: Int, by: Double): HSV = {
    val lightened = getPixel(pixel).lightenBy(by)
    setPixel(pixel, lightened)
    lightened
  }
}
