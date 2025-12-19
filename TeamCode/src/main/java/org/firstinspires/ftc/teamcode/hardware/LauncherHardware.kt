package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import java.util.Locale

class LauncherHardware(@JvmField val rh: RobotHardware) : Hardware {
  private lateinit var flywheel: DcMotorEx
  private lateinit var intake: DcMotorEx

  var flywheelSpeed: Double = 0.0
    set(value) {
      flywheel.power = value.coerceIn(-1.0..1.0)
    }

  var intakeSpeed: Double = 0.0
    set(value) {
      intake.power = value.coerceIn(-1.0..1.0)
    }

  override fun initialize() {
    flywheel = rh.op.hardwareMap.get(DcMotorEx::class.java, "flywheel")
    intake = rh.op.hardwareMap.get(DcMotorEx::class.java, "intake")
  }

  override fun update() {}

  override fun telemetry() {
    fun f(x: Double) = String.format(Locale.US, "%.2f", x)

    rh.op.telemetry.addLine("\nflywheel / intake\n----")

    rh.op.telemetry.addLine("speed: ${f(flywheelSpeed)} / ${f(intakeSpeed)}")
  }
}