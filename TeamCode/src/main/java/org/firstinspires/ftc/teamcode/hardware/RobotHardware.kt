package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware

class RobotHardware(@JvmField var op: OpMode) : Hardware {
  @JvmField
  var runtime: ElapsedTime = ElapsedTime()

  @JvmField
  var controls: Controls = Controls(this)
  var hardware: Array<Hardware> = arrayOf()

  /* TODO: This is where you will declare all your hardware objects.
   *   ex) var sampleH: SampleHardware? */
  @JvmField
  var driveH: DriveHardware = DriveHardware(this)
  @JvmField
  var cameraH: ArtifactCameraHardware = ArtifactCameraHardware(this)
  @JvmField
  var turretH: TurretHardware = TurretHardware(this)
  @JvmField
  var sortH: SortHardware? = null

  // Initialize all the hardware objects
  override fun initialize() {
    try {
      if (op.hardwareMap.get("this-is-6383") != null)
        hardware = arrayOf(driveH, cameraH, turretH)
      else {
        sortH = SortHardware(this)
        hardware = arrayOf(driveH, cameraH, turretH, sortH!!)
      }
    } catch (_: Exception ) {
      sortH = SortHardware(this)
      hardware = arrayOf(driveH, cameraH, turretH, sortH!!)
    }

    runtime.reset() // Generally this should be called after waitForStart() anyway, but it's not causing any harm here.
    controls.initialize()
    hardware.forEach { it.initialize() }
  }

  // Update all the hardware objects so that they will have accurate data and update the target positions of motors, servos, etc.
  override fun update() {
    hardware.forEach { it.update() }
  }

  // Print out the telemetry data for each hardware object
  override fun telemetry() {
    for (hw in hardware) {
      hw.telemetry()
      op.telemetry.addLine()
    }
  }
}
