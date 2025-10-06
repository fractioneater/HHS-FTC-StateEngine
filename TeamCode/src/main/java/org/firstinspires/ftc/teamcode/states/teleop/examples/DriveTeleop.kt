package org.firstinspires.ftc.teamcode.states.teleop.examples

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class DriveTeleop() : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    val max = rh.controls.exampleDriveSpeed()

    // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
    val axial = rh.controls.driveY()
    val lateral = rh.controls.driveX()
    val yaw = rh.controls.driveR()

    rh.exampleDriveH.drive(axial, lateral, yaw, max)
  }

  override val isDone = false
}
