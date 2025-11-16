package org.firstinspires.ftc.teamcode.hardware.examples

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.ServoControl

class ExampleClawHardware(private val rh: RobotHardware) : Hardware {
  private lateinit var clawServo: ServoControl

  override fun initialize() {
    clawServo = ServoControl(rh, "claw")
    clawServo.setPositions(doubleArrayOf(1.0, 0.0))
  }

  override fun update() {
    clawServo.move()
  }

  fun clawMoving(): Boolean {
    return clawServo.isMoving
  }

  fun setClaw(closed: Boolean) {
    if (closed) {
      clawServo.goToPosition(0.0)
    } else {
      clawServo.goToPosition(1.0)
    }
  }

  val isClawClosed: Boolean
    get() = clawServo.positionsIndex == 0

  override fun telemetry() {
    rh.op.telemetry.addLine("claw\n----")
    clawServo.telemetry()
  }
}
