package org.firstinspires.ftc.teamcode.hardware.examples

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import kotlin.math.abs
import kotlin.math.max

class ExampleDriveHardware(private val rh: RobotHardware) : Hardware {
  private lateinit var leftFrontDrive: DcMotorEx
  private lateinit var leftBackDrive: DcMotorEx
  private lateinit var rightFrontDrive: DcMotorEx
  private lateinit var rightBackDrive: DcMotorEx

  // Directional inputs for telemetry
  private var axial = 0.0
  private var lateral = 0.0
  private var yaw = 0.0
  private var maximum = 1.0

  override fun initialize() {
    leftFrontDrive = rh.op.hardwareMap.get(DcMotorEx::class.java, "lfD")
    leftBackDrive = rh.op.hardwareMap.get(DcMotorEx::class.java, "lbD")
    rightFrontDrive = rh.op.hardwareMap.get(DcMotorEx::class.java, "rfD")
    rightBackDrive = rh.op.hardwareMap.get(DcMotorEx::class.java, "rbD")

    leftFrontDrive.direction = Direction.REVERSE
    leftBackDrive.direction = Direction.REVERSE
    rightFrontDrive.direction = Direction.FORWARD
    rightBackDrive.direction = Direction.FORWARD
  }

  fun setPower(powerLF: Double, powerRF: Double, powerLB: Double, powerRB: Double) {
    leftFrontDrive.power = powerLF
    rightFrontDrive.power = powerRF
    leftBackDrive.power = powerLB
    rightBackDrive.power = powerRB
  }

  fun driveSmooth(axial: Double, lateral: Double, yaw: Double, maximum: Double) {
    drive(
      rh.controls.curveInput(axial),
      rh.controls.curveInput(lateral),
      rh.controls.curveInput(yaw),
      maximum
    )
  }

  fun drive(axial: Double, lateral: Double, yaw: Double, maximum: Double) {
    this.axial = axial
    this.lateral = lateral
    this.yaw = yaw
    this.maximum = maximum

    // Individual power for each wheel combining each input
    var leftFrontPower = -axial + lateral + yaw
    var rightFrontPower = -axial - lateral - yaw
    var leftBackPower = -axial - lateral + yaw
    var rightBackPower = -axial + lateral - yaw

    // Finds the maximum primitive power (can go as large as 3.0)
    var divisor = max(abs(leftFrontPower), abs(rightFrontPower))
    divisor = max(divisor, abs(leftBackPower))
    divisor = max(divisor, abs(rightBackPower))

    // Check if any power exceeds 1.0
    if (divisor > 1.0) {
      // Divide power by the maximum power so that no power exceeds 1.0
      leftFrontPower /= divisor
      rightFrontPower /= divisor
      leftBackPower /= divisor
      rightBackPower /= divisor
    }

    // Scale the powers to be relative to the maximum speed
    leftFrontPower *= maximum
    rightFrontPower *= maximum
    leftBackPower *= maximum
    rightBackPower *= maximum

    // Set wheel power
    setPower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower)
  }

  override fun update() {}

  override fun telemetry() {
    rh.op.telemetry.addLine("drive\n----")

    rh.op.telemetry.addLine("axial $axial, lateral $lateral, yaw $yaw")
    rh.op.telemetry.addLine("maximum power $maximum")
    rh.op.telemetry.addLine()

    rh.op.telemetry.addLine("lf ${leftFrontDrive.power}\trf ${rightFrontDrive.power}")
    rh.op.telemetry.addLine("lb ${leftBackDrive.power}\trb ${rightBackDrive.power}")
  }
}
