package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.math.pow

class Controls(private val rh: RobotHardware) {
  private enum class ButtonState {
    PRESSED, HELD, RELEASED, NOT_PRESSED
  }

  private class ButtonControl(val inputCallback: () -> Boolean) {
    private var held = false

    val state: ButtonState
      get() {
        return if (inputCallback()) {
          if (held) ButtonState.HELD
          else ButtonState.PRESSED
        } else {
          if (held) ButtonState.RELEASED
          else ButtonState.NOT_PRESSED
        }
      }
  }

  private val gp1: Gamepad? = rh.op.gamepad1
  private val gp2: Gamepad? = rh.op.gamepad2

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

  // TODO: Buttons that can take advantage of ButtonState should be defined here ---------------------------------------------------------

  // Example claw button
  private val aButton = ButtonControl { gp1?.a ?: false }
  fun clawButtonPressed(): Boolean =
    (aButton.state == ButtonState.PRESSED)

  // TODO: This is where you will place code for accessing the gamepad inputs ------------------------------------------------------------

  /**
   * All Controls functions need to test if the controller they are accessing exists
   * If the controller doesn't exist then return a default value
   * usually false for booleans and 0 for doubles
   */

  // This does not specify whether the button was just pressed or is being held. Use ButtonState for that.
  fun exampleDiscreteInput() =
    gp1?.a ?: false

  fun exampleContinuousInput() =
    (gp1?.right_trigger ?: 0.0).toDouble()

  fun exampleSmoothedContinuousInput() =
    if (gp1 == null) 0.0
    else smoothInput(gp1.left_trigger.toDouble())

  // Example forwards movement control
  fun driveY() =
    if (gp1 == null) 0.0
    else smoothInput(gp1.left_stick_y.toDouble())

  // Example sideways movement control
  fun driveX() =
    if (gp1 == null) 0.0
    else smoothInput(gp1.left_stick_x.toDouble())

  // Example rotation movement control
  fun driveR() =
    if (gp1 == null) 0.0
    else smoothInput(gp1.right_stick_x.toDouble())

  // Example movement speed control
  fun exampleDriveSpeed() =
    if (gp1 == null) 0.0
    else 0.5 + (0.25 * smoothInput((gp1.right_trigger - gp1.left_trigger).toDouble()))

  // Example motor speed control (designed for lift)
  // Turns doubles into ints because motor speeds have to be ints
  fun exampleLiftSpeed() =
    if (gp2 == null) 0
    else (10.0 * smoothInput((gp2.right_trigger - gp2.left_trigger).toDouble())).toInt()
  // right is INCREASE
  // left is DECREASE

  // Example lift adjustment
  fun exampleLiftAdjust(): Int {
    if (gp2 == null) return 0

    var adjust = 0
    if (gp2.dpad_up) adjust++
    if (gp2.dpad_down) adjust--
    return 10 * adjust
  }
}
