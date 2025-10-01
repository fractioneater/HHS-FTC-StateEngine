package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.pow

class Controls(private val rh: RobotHardware) {
  private val gp1: Gamepad? = rh.op.gamepad1
  private val gp2: Gamepad? = rh.op.gamepad2

  private val runtime = ElapsedTime()

  /**
   * Scales inputs between -1 and 1 to values of the same range such that -1, 1, and 0 are deadzones.
   * Small changes to the input have little to no change in the output around the deadzones.
   *
   * This uses the function:
   * y = 2.5x^3 - 1.5x^5
   */
  fun smoothInput(gamepadInput: Double): Double {
    return 2.5 * gamepadInput.pow(3.0) - 1.5 * gamepadInput.pow(5.0)
  }

  // TODO: This is where you will place code for accessing the gamepad inputs
  /**
   * All Controls functions need to test if the controller they are accessing exists
   * If the controller doesn't exist then return a default value
   * usually false for booleans and 0 for doubles
   */
  fun exampleDiscreteInput(): Boolean {
    return gp1?.a ?: false
  }

  fun exampleContinuousInput(): Double {
    return (gp1?.right_trigger ?: 0.0).toDouble()
  }

  fun exampleSmoothedContinuousInput(): Double {
    return if (gp1 == null) 0.0
    else smoothInput(gp1.left_trigger.toDouble())
  }

  // Example forwards movement control
  fun driveY(): Double {
    return if (gp1 == null) 0.0
    else smoothInput(gp1.left_stick_y.toDouble())
  }

  // Example sideways movement control
  fun driveX(): Double {
    return if (gp1 == null) 0.0
    else smoothInput(gp1.left_stick_x.toDouble())
  }

  // Example rotation movement control
  fun driveR(): Double {
    return if (gp1 == null) 0.0
    else smoothInput(gp1.right_stick_x.toDouble())
  }

  // Example movement speed control
  fun exampleDriveSpeed(): Double {
    return if (gp1 == null) 0.0
    else 0.5 + (0.25 * smoothInput((gp1.right_trigger - gp1.left_trigger).toDouble()))
  }

  // Example motor speed control (designed for lift)
  // Turns doubles into ints because motor speeds have to be ints
  fun exampleLiftSpeed(): Int {
    return if (gp2 == null) 0
    else (10.0 * smoothInput((gp2.right_trigger - gp2.left_trigger).toDouble())).toInt()
    // right is INCREASE
    // left is DECREASE
  }

  // Example lift adjustment
  fun exampleLiftAdjust(): Int {
    if (gp2 == null) return 0

    var adjust = 0
    if (gp2.dpad_up) adjust++
    if (gp2.dpad_down) adjust--
    return (10 * (adjust))
  }

  // Example claw button
  fun clawButton(): Boolean {
    return gp1?.b ?: false
  }
}
