package org.firstinspires.ftc.teamcode.stateengine

import org.firstinspires.ftc.teamcode.hardware.RobotHardware

interface State {
  fun init(rh: RobotHardware)
  fun run()
  val isDone: Boolean
}
