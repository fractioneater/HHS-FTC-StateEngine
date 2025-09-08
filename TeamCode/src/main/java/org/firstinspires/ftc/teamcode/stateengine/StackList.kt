package org.firstinspires.ftc.teamcode.stateengine

import org.firstinspires.ftc.teamcode.hardware.RobotHardware

abstract class StackList(private val stack: Array<State>) {
  fun initStack(rh: RobotHardware) {
    for (state in this.stack) {
      state.init(rh)
    }
  }
}
