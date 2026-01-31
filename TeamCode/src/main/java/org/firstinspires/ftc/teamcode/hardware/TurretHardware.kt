package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.ServoControl
import java.util.Locale
import kotlin.math.abs

class TurretHardware(@JvmField val rh: RobotHardware) : Hardware {
  private lateinit var flywheel: DcMotorEx
  private lateinit var intake: DcMotorEx
  private lateinit var horizontalAim: DcMotorEx
  private lateinit var verticalAim: ServoControl
  private lateinit var pusherServo: Servo
  private lateinit var pusherMotor: DcMotorEx

  private var pusherIsUp = false

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

  fun pusherDown() {
    if (pusherIsUp) {
      pusherIsUp = false
      pusherServo.position = 0.0
      pusherMotor.targetPosition = 1
    }
  }

  fun pusherUp() {
    if (!pusherIsUp) {
      pusherIsUp = true
      pusherServo.position = 0.5
      pusherMotor.targetPosition = -127
    }
  }

  fun updatePusherPower() {
    val dist = abs(pusherMotor.currentPosition - pusherMotor.targetPosition)
    if (dist < 6) pusherMotor.power = 0.0
    else if (dist < 30) pusherMotor.power = 0.4
    else pusherMotor.power = 0.8
  }

  override fun initialize() {
    flywheel = rh.op.hardwareMap.get(DcMotorEx::class.java, "flywheel")
    intake = rh.op.hardwareMap.get(DcMotorEx::class.java, "intake")
    horizontalAim = rh.op.hardwareMap.get(DcMotorEx::class.java, "horizontal")
    verticalAim = ServoControl(rh, "vertical")

    pusherServo = rh.op.hardwareMap.get(Servo::class.java, "pusherServo")
    pusherMotor = rh.op.hardwareMap.get(DcMotorEx::class.java, "pusher")

    pusherMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    pusherMotor.targetPosition = 0
    pusherMotor.mode = DcMotor.RunMode.RUN_TO_POSITION

    intake.direction = try {
      if (rh.op.hardwareMap.get("this-is-6383") != null) Direction.REVERSE else Direction.FORWARD
    } catch (_: Exception) { // 15317
      Direction.FORWARD
    }

    flywheel.direction = try {
      if (rh.op.hardwareMap.get("this-is-6383") != null) Direction.FORWARD else Direction.REVERSE
    } catch (_: Exception) { // 15317
      Direction.REVERSE
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

    rh.op.telemetry.addLine("\npusher\n----")
    rh.op.telemetry.addLine("pusher: current ${pusherMotor.currentPosition}, target ${pusherMotor.targetPosition}")
    verticalAim.telemetry()
  }
}