package org.firstinspires.ftc.teamcode.hardware.examples

import com.qualcomm.robotcore.hardware.DcMotorSimple.*
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.MotorControl

class ExampleLiftHardware(private val rh: RobotHardware) : Hardware {
  private var leftLift: MotorControl = MotorControl(rh, "leftLift", Direction.FORWARD)
  private var rightLift: MotorControl = MotorControl(rh, "rightLift", Direction.REVERSE)

  override fun initialize() {
    // Set motor range
    leftLift.setRange(0, 1000)
    rightLift.setRange(0, 1000)

    // Init motor positions
    val positions = intArrayOf(0, 200, 400, 600, 800, 1000) // for autonomous
    leftLift.setPositions(positions)
    rightLift.setPositions(positions)

    // Set speeds
    val increasingSpeed = 0.5 // Maximum speed for lift motors in + click direction
    val decreasingSpeed = 0.3 // Maximum speed for lift motors in - click direction
    leftLift.setSpeedControls(increasingSpeed, decreasingSpeed)
    rightLift.setSpeedControls(increasingSpeed, decreasingSpeed)
  }

  override fun update() {
    leftLift.clampTargetInRange()
    rightLift.clampTargetInRange()

    leftLift.move()
    rightLift.move()
  }

  fun setLiftPosition(position: Int) {
    leftLift.goToPosition(position)
    rightLift.goToPosition(position)
  }

  fun adjustPosition(adjustment: Int) {
    leftLift.goToPosition(leftLift.targetPosition + adjustment)
    rightLift.goToPosition(rightLift.targetPosition + adjustment)
  }

  val height: Int
    get() = leftLift.targetPosition

  override fun telemetry() {
    rh.op.telemetry.addLine("lift\n----")
    rh.op.telemetry.addLine()

    leftLift.telemetry()
    rightLift.telemetry()
  }
}
