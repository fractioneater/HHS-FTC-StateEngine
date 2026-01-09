package org.firstinspires.ftc.teamcode.hardware

import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.ServoControl

class SortHardware(private val rh: RobotHardware) : Hardware {
  private lateinit var servo: ServoControl

  override fun initialize() {
    servo = ServoControl(rh, "sort")
    servo.setPositions(doubleArrayOf(0.0, 1.0 / 6, 1.0 / 3, 1.0 / 2, 2.0 / 3, 5.0 / 6, 1.0))
  }

  fun nextPos() {
    if (servo.positionsIndex == servo.positionsCount - 1) null
//      servo.positionsIndex = 0
    else
      servo.goToPresetPosition(servo.positionsIndex + 1)
  }

  fun prevPos() {
    if (servo.positionsIndex == 0) null
//      servo.positionsIndex = servo.positionsCount - 1
    else
      servo.goToPresetPosition(servo.positionsIndex - 1)
  }

  override fun update() {}

  override fun telemetry() {
    rh.op.telemetry.addLine("\nsort\n----")
    // Will show something like this: "position 3 of 7 (launch)"
    rh.op.telemetry.addLine("position ${servo.positionsIndex + 1} of ${servo.positionsCount} (${if (servo.positionsIndex and 1 == 1) "launch" else "intake"})")
  }
}