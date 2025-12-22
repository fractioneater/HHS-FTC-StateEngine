package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class LauncherTeleop : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    val lb = rh.controls.leftBumper1()
    val rb = rh.controls.rightBumper1()

    rh.launcherH.flywheelSpeed = rb.toDouble();
    rh.launcherH.intakeSpeed = lb.toDouble();
  }

  override val isDone = false
}