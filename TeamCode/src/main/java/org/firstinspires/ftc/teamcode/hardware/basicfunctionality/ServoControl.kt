package org.firstinspires.ftc.teamcode.hardware.basicfunctionality

import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import kotlin.math.abs
import kotlin.math.withSign

/**
 * Class for controlling a servo motor.
 *
 * This class provides methods for setting the servo's position, moving it to a target position,
 * and checking if it is currently moving. It also allows for defining a set of preset positions
 * that the servo can be moved to.
 *
 * @property rh The [RobotHardware] instance.
 * @property name The name of the servo in the robot's configuration.
 * @property stepSize (Optional) The amount the servo moves in each step when moving to a target position.
 *                    Defaults to 0.15.
 */
class ServoControl(val rh: RobotHardware, val name: String, val stepSize: Double = 0.15) {
  private var servo: Servo = rh.op.hardwareMap.get(Servo::class.java, name)

  private var positions: DoubleArray = doubleArrayOf()
  var positionsIndex: Int = -1

  var currentPosition: Double = 0.0
  var targetPosition: Double = 0.0

  constructor(rh: RobotHardware, name: String) : this(rh, name, 0.15 /* TODO: Step size */)

  fun move() {
    if (abs(targetPosition - this.currentPosition) <= stepSize) {
      // If distance to target is smaller than the step size, set position directly to target
      this.currentPosition = targetPosition
    } else {
      // Adjust the position by the step size, in the direction of the target
      this.currentPosition += stepSize.withSign(targetPosition - this.currentPosition)
    }
    servo.setPosition(this.currentPosition)
  }

  val isMoving: Boolean
    get() = 0.0 != (this.currentPosition - this.targetPosition)

  fun setPositions(positions: DoubleArray) { // Should ONLY be called in initialization
    this.positions = positions

    servo.setPosition(positions[0])
    positionsIndex = 0
    this.currentPosition = positions[0]
    targetPosition = positions[0]
  }

  fun goToPresetPosition(index: Int) {
    goToPosition(positions[index])
    positionsIndex = index
  }

  fun goToPosition(newTargetPosition: Double) {
    targetPosition = newTargetPosition
    positionsIndex = -1
  }

  fun telemetry() {
    rh.op.telemetry.addLine("\nservo '$name'")
    rh.op.telemetry.addLine("    MIN is 0, MAX is 1")
    rh.op.telemetry.addLine("    at position $currentPosition, target is $targetPosition")
    rh.op.telemetry.addLine("    position #$positionsIndex, ${if (this.isMoving) "IS" else "NOT"} moving")
  }
}
