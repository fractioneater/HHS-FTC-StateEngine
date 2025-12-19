package org.firstinspires.ftc.teamcode.states.testing

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class MotorTest : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    rh.driveH.setPower(
      if (rh.controls.x1()) 1.0 else 0.0,
      if (rh.controls.y1()) 1.0 else 0.0,
      if (rh.controls.a1()) 1.0 else 0.0,
      if (rh.controls.b1()) 1.0 else 0.0
    )

    rh.launcherH.flywheelSpeed = rh.controls.rightTrigger1()
    rh.launcherH.intakeSpeed = rh.controls.leftTrigger1()

    // Telemetry.
    rh.op.telemetry.addLine("Tilt the controller 45° clockwise. Now ABXY buttons correspond to the drive motor in their position.")

    rh.op.telemetry.addLine("               (Y) - front right\n")
    rh.op.telemetry.addLine("(X) - front left       (A) - back right\n")
    rh.op.telemetry.addLine("               (B) - back left\n")

    rh.op.telemetry.addLine()
    rh.op.telemetry.addLine("Press [right_trigger] for flywheel speed.")
    rh.op.telemetry.addLine("Press [left_trigger] for intake speed.")
  }

  override val isDone = false
}