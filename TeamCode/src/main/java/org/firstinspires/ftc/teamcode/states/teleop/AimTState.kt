package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class AimTState : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    val targetID = 21

    val aprilTags = rh.cameraH.aprilTags

    if (aprilTags.any { it.id == targetID }) {
      rh.op.telemetry.addLine("target detected!")
//      rh.chuteH
    }
  }

  override val isDone = false
}