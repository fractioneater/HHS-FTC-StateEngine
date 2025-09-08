package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.basicfunctionality.Hardware
import org.firstinspires.ftc.teamcode.hardware.examples.ExampleClawHardware
import org.firstinspires.ftc.teamcode.hardware.examples.ExampleDriveHardware
import org.firstinspires.ftc.teamcode.hardware.examples.ExampleLiftHardware

class RobotHardware(@JvmField var op: OpMode) : Hardware {
  @JvmField
  var runtime: ElapsedTime?

  @JvmField
  var controls: Controls = Controls(this)
  var hardware: Array<Hardware>

  /* TODO
      This is where you will declare all your hardware objects:
      ex) public SampleHardware sampleH; */
  @JvmField
  var exampleClawH: ExampleClawHardware? = null
  @JvmField
  var exampleDriveH: ExampleDriveHardware? = null
  @JvmField
  var exampleLiftH: ExampleLiftHardware? = null

  init {

    hardware = arrayOf<Hardware>(
      /* TODO
          Here you will setup all your hardware objects in this list. The list is here for the opmode to loop through.
          ex) sampleH = new SampleHardware(this), */
      ExampleClawHardware(this).also { exampleClawH = it }, ExampleDriveHardware(this).also { exampleDriveH = it },
      ExampleLiftHardware(this).also { exampleLiftH = it },
    )

    runtime = ElapsedTime()
  }

  // Initialize all the hardware objects
  override fun initialize() {
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
