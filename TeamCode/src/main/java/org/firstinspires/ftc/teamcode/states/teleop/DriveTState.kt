package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class DriveTState : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    val max = rh.controls.driveMaxSpeed()

    val axial = rh.controls.driveY()
    val lateral = rh.controls.driveX()
    val yaw = rh.controls.driveR()

    rh.driveH.drive(axial, lateral, yaw, max)
  }

  override val isDone = false
}