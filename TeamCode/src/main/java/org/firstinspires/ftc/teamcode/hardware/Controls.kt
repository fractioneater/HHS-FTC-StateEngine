package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.Gamepad
import kotlin.math.pow

class Controls(private val rh: RobotHardware) {
  enum class ButtonState {
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

  private var gp2: Gamepad? = null
  private var gp1: Gamepad? = null

  fun initialize() {
    gp1 = rh.op.gamepad1
    gp2 = rh.op.gamepad2
  }

  /**
   * Scales inputs between -1 and 1 to values of the same range such that -1, 1, and 0 are deadzones.
   * Small changes to the input have little to no change in the output around the deadzones.
   *
   * This uses the function:
   * y = 2.5x^3 - 1.5x^5
   */
  fun curveInput(gamepadInput: Double): Double {
    return 2.5 * gamepadInput.pow(3.0) - 1.5 * gamepadInput.pow(5.0)
  }

  // TODO: Buttons that can take advantage of ButtonState should be defined here ---------------------------------------------------------

  // Example claw button
  fun clawButtonPressed(): Boolean = (aButton1.state == ButtonState.PRESSED)

  // Let's do all the buttons for fun.
  private val aButton1 = ButtonControl { gp1?.a ?: false }
  private val bButton1 = ButtonControl { gp1?.b ?: false }
  private val xButton1 = ButtonControl { gp1?.x ?: false }
  private val yButton1 = ButtonControl { gp1?.y ?: false }

  private val dpadR1 = ButtonControl { gp1?.dpad_right ?: false }
  private val dpadL1 = ButtonControl { gp1?.dpad_left ?: false }

  fun a1State() = aButton1.state
  fun a1() = gp1?.a ?: false
  fun b1State() = bButton1.state
  fun b1() = gp1?.b ?: false
  fun x1State() = xButton1.state
  fun x1() = gp1?.x ?: false
  fun y1State() = yButton1.state
  fun y1() = gp1?.y ?: false

  fun dpadR1JustPressed() = dpadR1.state == ButtonState.PRESSED
  fun dpadL1JustPressed() = dpadL1.state == ButtonState.PRESSED

  private val aButton2 = ButtonControl { gp2?.a ?: false }
  private val bButton2 = ButtonControl { gp2?.b ?: false }
  private val xButton2 = ButtonControl { gp2?.x ?: false }
  private val yButton2 = ButtonControl { gp2?.y ?: false }

  fun a2State() = aButton2.state
  fun a2() = gp2?.a ?: false
  fun b2State() = bButton2.state
  fun b2() = gp2?.b ?: false
  fun x2State() = xButton2.state
  fun x2() = gp2?.x ?: false
  fun y2State() = yButton2.state
  fun y2() = gp2?.y ?: false

  // TODO: This is where you will place code for accessing the gamepad inputs ------------------------------------------------------------

  /**
   * All Controls functions need to test if the controller they are accessing exists
   * If the controller doesn't exist then return a default value
   * usually false for booleans and 0 for doubles
   */

  // IMPORTANT: This does not specify whether the button was just pressed or is being held. Use ButtonState for that.
  fun exampleDiscreteInput() =
    gp1?.a ?: false

  fun rightTrigger1() =
    (gp1?.right_trigger ?: 0.0).toDouble()

  fun leftTrigger1() =
    (gp1?.left_trigger ?: 0.0).toDouble()

  fun rightBumper1() = gp1?.right_bumper ?: false
  fun leftBumper1() = gp1?.left_bumper ?: false

  fun exampleSmoothedContinuousInput() =
    if (gp1 == null) 0.0
    else curveInput(gp1!!.left_trigger.toDouble())

  // Forwards movement control
  fun driveY() =
    if (gp1 == null) 0.0
    else curveInput(gp1!!.left_stick_y.toDouble())

  // Sideways movement control
  fun driveX() =
    if (gp1 == null) 0.0
    else curveInput(gp1!!.left_stick_x.toDouble())

  // Rotation movement control
  fun driveR() =
    if (gp1 == null) 0.0
    else curveInput(gp1!!.right_stick_x.toDouble())

  // Movement speed control
  // Scaling between 0.5 and 1
  fun driveMaxSpeed() =
    if (gp1 != null && gp2 != null) {
      if (gp1!!.right_trigger == 0f) 0.5 + gp2!!.right_trigger / 3.0
      else 0.5 + gp1!!.right_trigger / 2.0
    } else {
      0.5 + ((gp1?.right_trigger?.toDouble() ?: (gp2?.right_trigger?.toDouble() ?: 0.0))) / 2.0
    }

  // Example motor speed control (designed for lift)
  // Turns doubles into ints because motor speeds have to be ints
  fun exampleLiftSpeed() =
    if (gp2 == null) 0
    else (10.0 * curveInput((gp2!!.right_trigger - gp2!!.left_trigger).toDouble())).toInt()
  // right is INCREASE
  // left is DECREASE

  // Example lift adjustment
  fun exampleLiftAdjust(): Int {
    if (gp2 == null) return 0

    var adjust = 0
    // Okay, but how do you press dpad up and down at the same time?
    if (gp2!!.dpad_up) adjust++
    if (gp2!!.dpad_down) adjust--
    return 10 * adjust
  }
}
