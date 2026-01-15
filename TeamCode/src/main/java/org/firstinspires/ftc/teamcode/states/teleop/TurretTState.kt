package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class TurretTState : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    val lb = rh.controls.leftBumper1()

    rh.turretH.flywheelSpeed = rh.controls.leftTrigger1()
    rh.turretH.intakeSpeed = if (lb) 1.0 else 0.0

//    if (rh.controls.leftTrigger1() > 0.3 && rh.sortH.getPosIndex() and 1 == 0) {
//      // Ready to launch but the sort is in intake position.
//      rh.sortH.nextPos() // There may be a better way, possibly launching the previously loaded one.
//    }

//    if (rh.controls.leftTrigger1() > 0.95)
//      rh.turretH.pusherUp()
//    else
//      rh.turretH.pusherDown()
  }

  override val isDone = false
}