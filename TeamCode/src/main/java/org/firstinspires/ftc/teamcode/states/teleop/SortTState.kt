package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.Controls.ButtonState
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class SortTState : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    if (rh.controls.x1State() == ButtonState.PRESSED) rh.sortH?.slot0()
    else if (rh.controls.y1State() == ButtonState.PRESSED) rh.sortH?.slot1()
    else if (rh.controls.b1State() == ButtonState.PRESSED) rh.sortH?.slot2()
    else if (rh.controls.a1State() == ButtonState.PRESSED) rh.sortH?.nearestIntake()
  }

  override val isDone = false
}