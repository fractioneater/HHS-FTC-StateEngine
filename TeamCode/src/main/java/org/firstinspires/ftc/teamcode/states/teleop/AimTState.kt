package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class AimTState : State {
  private lateinit var rh: RobotHardware

//  private val targetID = 24
  private val targetID = 21

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    val aprilTags = rh.cameraH.aprilTags

    val found = aprilTags.find { it.id == targetID }
    if (found != null) {
      val h = found.robotPose.orientation.yaw
//      rh.turretH.horizontalSpeed = h * 0.7
      rh.op.telemetry.addLine("target detected! yaw: $h")
    }
  }

  override val isDone = false
}