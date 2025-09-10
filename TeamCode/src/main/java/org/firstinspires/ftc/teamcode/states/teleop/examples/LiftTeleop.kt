package org.firstinspires.ftc.teamcode.states.teleop.examples

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class LiftTeleop(val rh: RobotHardware) : State {

  override fun init() {}

  override fun run() {
    rh.exampleLiftH.setLiftPosition(rh.controls.exampleLiftSpeed())
  }

  override fun stop() {}

  override val isDone = false
}
