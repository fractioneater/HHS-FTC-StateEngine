package org.firstinspires.ftc.teamcode.stateengine

import org.firstinspires.ftc.teamcode.hardware.RobotHardware

class ParallelStack(private val stack: Array<State>) : StackList(stack), State {
  override var isDone: Boolean = false

  override fun init(rh: RobotHardware) {
    super.initStack(rh)
  }

  override fun run() {
    if (this.isDone) return
    var done = true

    for (state in stack) {
      if (!state.isDone) {
        state.run()
        done = false
      }
    }

    // When all of the states are done, this stack will be done
    if (done) {
      this.isDone = true
    }
  }
}
