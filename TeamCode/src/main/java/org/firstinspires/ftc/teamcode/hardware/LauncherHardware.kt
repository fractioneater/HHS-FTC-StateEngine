package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import java.util.Locale

class LauncherHardware(@JvmField val rh: RobotHardware) : Hardware {
  private lateinit var l: DcMotorEx
  private lateinit var r: DcMotorEx

  var speed: Double = 0.0
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
    fun f(x: Double) = String.format(Locale.US, "%.2f", x)

    rh.op.telemetry.addLine("flywheels\n----")

    rh.op.telemetry.addLine("target speed: $speed")
    rh.op.telemetry.addLine("current:\n${f(l.power)}\t${f(r.power)}")
  }
}