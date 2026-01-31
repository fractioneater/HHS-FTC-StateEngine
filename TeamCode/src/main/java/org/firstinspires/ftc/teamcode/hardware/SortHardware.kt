package org.firstinspires.ftc.teamcode.hardware

import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.ServoControl

class SortHardware(private val rh: RobotHardware) : Hardware {
  private lateinit var servo: ServoControl

  override fun initialize() {
    servo = ServoControl(rh, "sort")
  }

  fun initializeLater() {
    servo.setPositions(doubleArrayOf(0.5, 1.0))
  }

  fun launch() {
    servo.goToPresetPosition(1)
  }

  fun intake() {
    servo.goToPresetPosition(0)
  }

//  fun slot0() {
//    servo.goToPresetPosition(1)
//  }

//  fun slot1() {
//    servo.goToPresetPosition(3)
//  }
//
//  fun slot2() {
//    servo.goToPresetPosition(5)
//  }

//  fun nearestIntake() {
//    if (servo.positionsIndex and 1 == 1) // If in launch position
//      nextPos()
//    else { // If in intake position
//      if (servo.positionsIndex == servo.positionsCount - 1)
//        servo.goToPresetPosition(2)
//      else
//        servo.goToPresetPosition(servo.positionsIndex + 2)
//    }
//  }
//
//  fun nextPos() {
//    if (servo.positionsIndex != servo.positionsCount - 1)
//      servo.goToPresetPosition(servo.positionsIndex + 1)
//  }
//
  fun inLaunchPosition() = servo.positionsIndex and 1 == 1
  fun inIntakePosition() = servo.positionsIndex and 1 == 0

  override fun update() {}

  override fun telemetry() {
    rh.op.telemetry.addLine("\nsort\n----")
    // Will show something like this: "position 3 of 7 (launch)"
    rh.op.telemetry.addLine("position ${servo.positionsIndex + 1} of ${servo.positionsCount} (${if (inLaunchPosition()) "launch" else "intake"})")
  }
}