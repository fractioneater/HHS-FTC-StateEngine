package org.firstinspires.ftc.teamcode.opmodes.teleop.examples

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.ParallelStack
import org.firstinspires.ftc.teamcode.stateengine.State
import org.firstinspires.ftc.teamcode.states.teleop.examples.DriveTeleop

@Suppress("unused")
@TeleOp(name = "Example OpMode", group = "Examples")
class ExampleOpMode : LinearOpMode() {
  private val rh = RobotHardware(this)

  override fun runOpMode() {
    rh.initialize()

    val states = arrayOf<State>(
      DriveTeleop(),
    )
    val stack = ParallelStack(states)

    stack.init(rh)

    rh.telemetry()

    // Wait for the game to start (driver presses PLAY)
    telemetry.addLine("initialized")
    telemetry.update()

    waitForStart()
    rh.runtime.reset()

    while (opModeIsActive()) {
      stack.run()
      rh.update()

      telemetry.addLine("runtime: ${rh.runtime}")
      rh.telemetry()
      telemetry.update()
    }
  }
}
