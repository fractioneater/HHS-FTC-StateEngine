package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

@Suppress("unused")
class SampleStateTeleop(val rh: RobotHardware) : State {

  override fun init() {
    // There probably won't be any need to init anything in the teleop states
  }

  override fun run() {
    // The functions that call hardware functions are put here

    // NOTE: DO NOT have code editing the hardware here!
    // That should all be done by calling hardware functions!
  }

  override fun stop() {
    // TODO.
  }

  // TeleOp states will probably run for the entire TeleOp stage so they won't need to stop
  override val isDone = false
}
