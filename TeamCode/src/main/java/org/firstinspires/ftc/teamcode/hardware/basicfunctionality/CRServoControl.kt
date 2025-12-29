package org.firstinspires.ftc.teamcode.hardware.basicfunctionality

import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.hardware.RobotHardware

/**
 * Class for controlling a continuous rotation servo.
 *
 * This class provides methods for setting the servo's power, and that's actually it.
 *
 * @property rh The [RobotHardware] instance.
 * @property name The name of the servo in the robot's configuration.
 */
class CRServoControl(val rh: RobotHardware, val name: String) {
  private var servo: CRServo = rh.op.hardwareMap.get(CRServo::class.java, name)

  var power: Double
    get() = servo.power
    set(value) {
      servo.power = value
    }

  var direction: DcMotorSimple.Direction
    get() = servo.direction
    set(value) {
      servo.direction = value
    }

  val isMoving: Boolean
    get() = 0.0 != servo.power

  fun telemetry() {
    rh.op.telemetry.addLine("CR servo '$name'")
    if (isMoving) {
      rh.op.telemetry.addLine("    moving, power: $power")
    } else
      rh.op.telemetry.addLine("    NOT moving")

  }
}
