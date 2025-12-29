package org.firstinspires.ftc.teamcode.hardware

import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.CRServoControl

class ChuteHardware(@JvmField val rh: RobotHardware) : Hardware {
  private lateinit var horizontal: CRServoControl
  private lateinit var vertical: CRServoControl

  override fun initialize() {
    horizontal = CRServoControl(rh, "horizontal")
    vertical = CRServoControl(rh, "vertical")
  }

  fun doNothing() {}

  override fun update() {}

  override fun telemetry() {
    rh.op.telemetry.addLine("\nchute\n----")
    horizontal.telemetry()
    vertical.telemetry()
  }
}