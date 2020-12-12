package ws2801

trait AnimationStep {
  val startup: (Pixels, Long) => Unit

  val run: (Pixels, Long) => Boolean
}
