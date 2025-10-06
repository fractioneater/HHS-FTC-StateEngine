package org.firstinspires.ftc.teamcode.states.teleop.examples

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class ClawTeleop() : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    if (rh.controls.clawButton() && !rh.exampleClawH.clawMoving()) {
      rh.exampleClawH.setClaw(!rh.exampleClawH.isClawClosed)
    }
  }

  override val isDone = false
}
