package org.firstinspires.ftc.teamcode.opmodes.teleop.examples

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.ParallelStack
import org.firstinspires.ftc.teamcode.states.teleop.examples.ClawTeleop
import org.firstinspires.ftc.teamcode.states.teleop.examples.DriveTeleop
import org.firstinspires.ftc.teamcode.states.teleop.examples.LiftTeleop

@Suppress("unused")
@TeleOp(name = "Example OpMode", group = "Examples")
class ExampleOpMode : LinearOpMode() {
  private val rh = RobotHardware(this)

  override fun runOpMode() {
    rh.initialize()

    val states = arrayOf(
      DriveTeleop(rh),
      ClawTeleop(rh),
      LiftTeleop(rh),
    )
    val stack = ParallelStack(states)

    stack.init(rh)

    rh.telemetry()

    // Wait for the game to start (driver presses PLAY)
    telemetry.addData("Status", "Initialized")
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
