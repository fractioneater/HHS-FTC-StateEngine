package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware

class RobotHardware(@JvmField var op: OpMode) : Hardware {
  @JvmField
  var runtime: ElapsedTime = ElapsedTime()

  @JvmField
  var controls: Controls = Controls(this)
  var hardware: Array<Hardware>

  /* TODO
      This is where you will declare all your hardware objects:
      ex) var sampleH = SampleHardware(this) */
  @JvmField
  var driveH = DriveHardware(this)
  @JvmField
  var cameraH = ArtifactCameraHardware(this)
  @JvmField
  var launcherH = LauncherHardware(this)
//  @JvmField
//  var chuteH = ChuteHardware(this)

  init {
    hardware = arrayOf<Hardware>(
      /* TODO
          Here you will setup all your hardware objects in this list. The list is here for the opmode to loop through.
          ex) sampleH, otherH, thirdH */
      driveH, cameraH, launcherH, /*chuteH*/
    )
  }

  // Initialize all the hardware objects
  override fun initialize() {
    runtime.reset() // Generally this should be called after waitForStart() anyway, but it's not causing any harm here.
    controls.initialize()
    for (hw in hardware) {
      hw.initialize()
    }
  }

  // Update all the hardware objects so that they will have accurate data and update the target positions of motors, servos, etc.
  override fun update() {
    for (hw in hardware) {
      hw.update()
    }
  }

  // Print out the telemetry data for each hardware object
  override fun telemetry() {
    for (hw in hardware) {
      hw.telemetry()
      op.telemetry.addLine()
    }
  }
}
