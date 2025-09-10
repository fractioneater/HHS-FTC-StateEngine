package org.firstinspires.ftc.teamcode.states.teleop.examples

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class ClawTeleop(val rh: RobotHardware) : State {

  override fun init() {}

  override fun run() {
    if (rh.controls.clawButton() && !rh.exampleClawH.clawMoving()) {
      rh.exampleClawH.setClaw(!rh.exampleClawH.isClawClosed)
    }
  }

  override fun stop() {
    // TODO.
  }

  override val isDone = false
}
