package org.firstinspires.ftc.teamcode.states.autonomous

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

@Suppress("unused")
class SampleStateAutonomous(val rh: RobotHardware) : State {

  override fun init() {
    // Init anything needed for this state
    // But this will likely not be used for most states
  }

  override fun run() {
    // The functions that call hardware functions are put here

    // NOTE: DO NOT have code editing the hardware here!
    // That should all be done by calling hardware functions!
  }

  override fun stop() {
    // TODO.
  }

  override val isDone: Boolean
    get() = false /* TODO: Update this function to return true when the state has finished running.
                       This "getter" structure is helpful, but not necessary. */
}
