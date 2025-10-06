package org.firstinspires.ftc.teamcode.stateengine

import org.firstinspires.ftc.teamcode.hardware.RobotHardware

class SeriesStack(private val stack: Array<State>) : StackList(stack), State {
  private var stackIndex = 0

  override val isDone: Boolean
    get() = stackIndex == stack.size // When this stack has finished all States, this stack will be done

  override fun init(rh: RobotHardware) {
    super.initStack(rh)
  }

  override fun run() {
    // Go to next State if current State is done
    if (stack[stackIndex].isDone) {
      stackIndex++
    }

    // Run State
    if (!isDone) {
      stack[stackIndex].run()
    }
  }
}
