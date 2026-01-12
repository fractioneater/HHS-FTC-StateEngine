package org.firstinspires.ftc.teamcode.opmodes.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.SeriesStack
import org.firstinspires.ftc.teamcode.stateengine.State

@Suppress("unused")
@Disabled
@Autonomous(name = "Sample Series OpMode", group = "Samples")
class SampleOpModeAuto : LinearOpMode() {
  private val rh = RobotHardware(this)

  override fun runOpMode() {
    rh.initialize()

    val states = arrayOf<State>()
    val stack = SeriesStack(states)

    stack.init(rh)

    rh.telemetry()

    // Wait for the game to start (driver presses PLAY)
    telemetry.addLine("initialized")
    telemetry.update()

    waitForStart()
    rh.runtime.reset()

    // Run until the end of the match (driver presses STOP)
    while (opModeIsActive()) {
      if (!stack.isDone) {
        stack.run()
        rh.update()
        rh.controls.updateButtonControls()
      }

      telemetry.addLine("runtime: ${rh.runtime}")
      rh.telemetry()
      telemetry.update()
    }
  }
}
