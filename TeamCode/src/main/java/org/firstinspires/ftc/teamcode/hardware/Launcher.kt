package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware

class Launcher(@JvmField val rh: RobotHardware) : Hardware {
  private lateinit var l: DcMotorEx
  private lateinit var r: DcMotorEx

  private var speed: Double = 0.0
    set(value) {
      l.power = value.coerceIn(-1.0..1.0)
      r.power = value.coerceIn(-1.0..1.0)
    }

  override fun initialize() {
    l = rh.op.hardwareMap.get(DcMotorEx::class.java, "flywheelL")
    r = rh.op.hardwareMap.get(DcMotorEx::class.java, "flywheelR")

    r.direction = Direction.REVERSE
  }

  override fun update() {}

  override fun telemetry() {
    rh.op.telemetry.addLine("flywheels\n----")

    rh.op.telemetry.addLine("target speed: $speed")
    rh.op.telemetry.addLine("current:\n${l.power}\t${r.power}")
    TODO("Get encoder speeds and check if the wheels are turning at the same velocity. Show a warning if not.")
  }
}