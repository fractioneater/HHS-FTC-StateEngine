package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.hardware.dfrobot.HuskyLens
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.CRServoControl
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.MotorControl
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.ServoControl
import java.util.Locale

class TurretHardware(@JvmField val rh: RobotHardware) : Hardware {
  private lateinit var flywheel: DcMotorEx
  private lateinit var intake: DcMotorEx
  private lateinit var horizontalAim: DcMotorEx
  private lateinit var verticalAim: ServoControl

  var flywheelSpeed: Double
    get() = flywheel.power
    set(value) {
      flywheel.power = value.coerceIn(-1.0..1.0)
    }

  var intakeSpeed: Double
    get() = intake.power
    set(value) {
      intake.power = value.coerceIn(-1.0..1.0)
    }

  var horizontalSpeed: Double
    get() = horizontalAim.power
    set(value) {
      horizontalAim.power = value / 3.0
    }

  var verticalPosition: Double
    get() = verticalAim.targetPosition
    set(value) {
      verticalAim.targetPosition = value.coerceIn(0.0..1.0)
    }

  override fun initialize() {
    flywheel = rh.op.hardwareMap.get(DcMotorEx::class.java, "flywheel")
    intake = rh.op.hardwareMap.get(DcMotorEx::class.java, "intake")
    horizontalAim = rh.op.hardwareMap.get(DcMotorEx::class.java, "horizontal")
    verticalAim = ServoControl(rh, "vertical")

    flywheel.direction = Direction.REVERSE

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

    rh.op.telemetry.addLine("\naim\n----")
    rh.op.telemetry.addLine("horizontal power: ${f(horizontalSpeed)}")
    verticalAim.telemetry()
  }
}