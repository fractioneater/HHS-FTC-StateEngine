package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

class SortTState : State {
  private lateinit var rh: RobotHardware

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    if (rh.controls.dpadL1JustPressed()) rh.sortH.prevPos()
    else if (rh.controls.dpadR1JustPressed()) rh.sortH.nextPos()
  }

  override val isDone = false
}