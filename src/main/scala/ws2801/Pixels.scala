package ws2801

import com.diozero.api.{SpiClockMode, SpiDevice}

class Pixels(spiPort: Int, spiDevice: Int, count: Int) {
  val clockFrequency = 1000000
  val clockMode = SpiClockMode.MODE_0
  val lsbFirst = false

  val device = new SpiDevice(spiPort, spiDevice, clockFrequency, clockMode, lsbFirst)
  val array = new Array[Byte](count * 3)

  sys.addShutdownHook {
    device.close()
  }

  def show(): Unit = {
    device.write(array)
  }

  def setPixel(pixel: Int, r: Byte, g: Byte, b: Byte): Unit = {
    array.update(pixel * 3, r)
    array.update(pixel * 3 + 1, g)
    array.update(pixel * 3 + 2, b)
  }

  def setPixel(pixel: Int, rgb: RGB): Unit = {
    setPixel(pixel, rgb.r, rgb.g, rgb.b)
  }

  def getPixel(pixel: Int): RGB = {
    RGB(array(pixel * 3), array(pixel * 3 + 1), array(pixel * 3 + 2))
  }
}
