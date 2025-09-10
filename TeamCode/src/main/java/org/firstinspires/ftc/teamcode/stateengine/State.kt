package org.firstinspires.ftc.teamcode.stateengine

interface State {
  fun init() // TODO NEXT: Switch back to stack.init(rh) instead of State(rh). It's annoying to have it as a param everywhere.
  fun run()
  fun stop()
  val isDone: Boolean
}
