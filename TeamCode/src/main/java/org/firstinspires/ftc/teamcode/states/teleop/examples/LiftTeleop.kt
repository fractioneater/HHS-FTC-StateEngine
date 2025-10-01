package org.firstinspires.ftc.teamcode.states.teleop.examples

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class LiftTeleop() : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    rh.exampleLiftH.setLiftPosition(rh.controls.exampleLiftSpeed())
  }

  override fun stop() {}

  override val isDone = false
}
