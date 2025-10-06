package org.firstinspires.ftc.teamcode.hardware.basicfunctionality

import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/**
 * A motor, generally one designed to operate with encoders. For example, a lift.
 *
 * This class provides functionalities beyond the basic `DcMotorEx` interface,
 * including:
 * - Setting operational range (min/max encoder positions).
 * - Defining preset positions for quick navigation.
 * - Clamping target positions within the defined range.
 * - Toggling between "dumb" (direct power control) and encoder-based movement.
 * - Implementing a power curve for smoother acceleration and deceleration.
 * - Separate speed controls for increasing and decreasing movements (e.g., for lifts).
 * - Telemetry output for debugging and monitoring.
 *
 * @property rh An instance of [RobotHardware] providing access to the op mode and hardware map.
 * @property name The name of the motor as set in the hardware configuration on the driver hub.
 * @property direction The [Direction] of the motor (FORWARD or REVERSE).
 * @property zeroPowerBehavior (Optional) The [ZeroPowerBehavior] of the motor (BRAKE or FLOAT) when power is zero.
 *                             Defaults to BRAKE.
 */
class MotorControl(val rh: RobotHardware, val name: String, val direction: Direction, val zeroPowerBehavior: ZeroPowerBehavior) {
  private var motor: DcMotorEx = rh.op.hardwareMap.get(DcMotorEx::class.java, name)

  private var min = 0
  private var max = 0

  private var positions: IntArray = intArrayOf()

  // These separate increasing and decreasing speeds for cases such as
  // going slower down so that gravity doesn't make a lift system damage the robot
  private var increasingSpeed = 1.0
  private var decreasingSpeed = 1.0

  init {
    motor.targetPosition = 0

    motor.mode = RunMode.RUN_USING_ENCODER
    motor.direction = direction
    motor.zeroPowerBehavior = zeroPowerBehavior
  }

  // Default ZeroPowerBehavior is BRAKE
  constructor(rh: RobotHardware, name: String, direction: Direction) : this(rh, name, direction, ZeroPowerBehavior.BRAKE)

  fun move() {
    motor.power = powerCurve()
  }

  val isMoving: Boolean
    get() {
      return if (this.isDumbMode) 0.0 != motor.power
      else 0.0 != (motor.currentPosition - motor.targetPosition).toDouble()
    }

  fun setRange(min: Int, max: Int) {
    this.min = min
    this.max = max
  }

  fun setPositions(positions: IntArray) {
    this.positions = positions
  }

  fun goToPresetPosition(index: Int) {
    motor.targetPosition = positions[index]
  }

  fun clampTargetInRange() {
    var target = min(max, motor.targetPosition)
    target = max(min, target)
    motor.targetPosition = target
  }

  // --------------------------------------------------------------------------------------------
  var isDumbMode: Boolean = true
    private set

  fun disableEncoder() {
    this.isDumbMode = true
    motor.mode = RunMode.RUN_WITHOUT_ENCODER
  }

  fun enableEncoder() {
    this.isDumbMode = false
    motor.mode = RunMode.STOP_AND_RESET_ENCODER
    motor.mode = RunMode.RUN_USING_ENCODER
    motor.targetPosition = motor.currentPosition
  }

  fun moveDumb(power: Double) {
    motor.power = power
  }

  // --------------------------------------------------------------------------------------------
  fun goToPosition(targetPosition: Int) {
    motor.targetPosition = targetPosition
  }

  val currentPosition: Int
    get() = motor.currentPosition

  val targetPosition: Int
    get() = motor.targetPosition

  // --------------------------------------------------------------------------------------------
  fun setSpeedControls(speed: Double) {
    this.increasingSpeed = speed
    this.decreasingSpeed = speed
  }

  fun setSpeedControls(increasingSpeed: Double, decreasingSpeed: Double) {
    this.increasingSpeed = increasingSpeed
    this.decreasingSpeed = decreasingSpeed
  }

  fun setPowerControls(easingDistance: Int, brakingDistance: Int, stretch: Double) {
    this.easingDistance = easingDistance
    this.brakingDistance = brakingDistance
    this.stretch = stretch
  }

  // Variables only for the power curve method
  private var easingDistance = 1000 // Max speed is used above this distance
  private var brakingDistance = 30 // Brake below this distance from target
  private var stretch = 100.0 // For a base of 2 this is the distance at which 50% speed will be used

  // Above this distance it will approach 100% speed
  // Below this distance it will approach 0% speed with a deadzone near 0 distance
  fun powerCurve(): Double {
    if (this.isDumbMode) return 0.0

    // Find signed distance to target
    var distance = motor.targetPosition - motor.currentPosition

    // Set the correct speed for the direction of motion
    var speed = increasingSpeed
    if (distance < 0) {
      speed = decreasingSpeed
    }

    // Make the distance positive
    distance = abs(distance)

    // Returns 0.0 when within braking distance
    if (distance <= brakingDistance) {
      return 0.0
    }

    // Returns speed when above easing distance
    if (distance >= easingDistance) {
      return speed
    }

    // When inside easing distance, use a decreasing speed for closer distances
    // The exponential base (2) shouldn't be changed
    val power = 2.0.pow(-stretch / distance)
    return power * speed

    // y = 2^( -s / x ) * v
    // Where y is power, x is distance to target (+), s is the stretch factor, and v is the speed
  }

  fun telemetry() {
    rh.op.telemetry.addLine("\nmotor '$name'")
    rh.op.telemetry.addLine("    MIN is $min, MAX is $max")
    rh.op.telemetry.addLine("    positions: CURRENT ${motor.currentPosition}, TARGET ${motor.targetPosition}")
    rh.op.telemetry.addLine("    ${if (this.isMoving) "IS" else "NOT"} moving${if (this.isMoving) " at speed ${powerCurve()}" else ""}")
  }
}
