package org.firstinspires.ftc.teamcode.states.teleop

import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State
import java.util.Locale

class AimTState : State {
  private lateinit var rh: RobotHardware

  // Blue goal is 20, red is 24
  private val targetID = 20

  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    // TODO AFTER COMP!!!
    //  - Ask if red or blue
    //  - Automatic control for turret aim
    var hPower = 0.0
    if (rh.controls.dpadR1())
      hPower += 0.7
    if (rh.controls.dpadL1())
      hPower -= 0.7
    rh.turretH.horizontalSpeed = hPower

    if (rh.controls.dpadD1())
      rh.turretH.verticalPosition = 0.0
    else if (rh.controls.dpadU1())
      rh.turretH.verticalPosition = 1.0
  }

  /*override fun run() {
    val aprilTags = rh.cameraH.aprilTags

    val found = aprilTags.find { it.id == targetID }
    if (found != null) {
      val h = found.ftcPose.yaw
      val dist = found.ftcPose.range
//      rh.turretH.horizontalSpeed = h * 0.7
//      rh.turretH.verticalPosition = (dist / 20).coerceIn(0.0..1.0)

      fun f(x: Double) = String.format(Locale.US, "%.2f", x)

      rh.op.telemetry.addLine("@@@@@@\ntarget detected! yaw: ${f(h)}, dist: ${f(dist)}\n@@@@@@")
    }
  }*/

  override val isDone = false
}