package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.hardware.dfrobot.HuskyLens
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
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

    try {
      rh.op.hardwareMap.get(HuskyLens::class.java, "this-is-6383")
      intake.direction = Direction.REVERSE
    } catch (_: Exception) {
      intake.direction = Direction.REVERSE
    }
  }

  override fun update() {}

  override fun telemetry() {
    fun f(x: Double) = String.format(Locale.US, "%.2f", x)

    rh.op.telemetry.addLine("\nflywheel / intake\n----")

    rh.op.telemetry.addLine("speed: ${f(flywheelSpeed)} / ${f(intakeSpeed)}")
    rh.op.telemetry.addLine("intake direction: ${intake.direction}")
  }
}