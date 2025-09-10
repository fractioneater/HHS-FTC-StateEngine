package org.firstinspires.ftc.teamcode.stateengine

interface State {
  fun init()
  fun run()
  fun stop()
  val isDone: Boolean
}
