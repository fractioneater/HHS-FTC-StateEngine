package org.firstinspires.ftc.teamcode.hardware

import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.ServoControl

class ChuteHardware(@JvmField val rh: RobotHardware) : Hardware {
  private lateinit var horizontal: ServoControl
  private lateinit var vertical: ServoControl

  override fun initialize() {
    horizontal = ServoControl(rh, "horizontal")
    vertical = ServoControl(rh, "vertical")
  }

  override fun update() {
    TODO("Not yet implemented")
  }

  override fun telemetry() {
    // Nothing yet.
    // TODO: I need to put some stuff here, though.
  }
}