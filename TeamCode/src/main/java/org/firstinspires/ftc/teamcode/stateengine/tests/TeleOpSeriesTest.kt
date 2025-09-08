package org.firstinspires.ftc.teamcode.stateengine.tests

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.SeriesStack
import org.firstinspires.ftc.teamcode.stateengine.State
import org.firstinspires.ftc.teamcode.states.teleop.examples.DriveTeleop

@Suppress("unused")
@TeleOp(name = "Series Test", group = "Testing")
class TeleOpSeriesTest : LinearOpMode() {
  private val runtime = ElapsedTime()
  private val rh = RobotHardware(this)

  override fun runOpMode() {
    rh.initialize()

    val states = arrayOf<State>(DriveTeleop())
    val stack = SeriesStack(states)

    stack.init(rh)

    // Wait for the game to start (driver presses PLAY)
    telemetry.addLine("initialized")
    telemetry.update()

    waitForStart()
    runtime.reset()

    // run until the end of the match (driver presses STOP)
    while (opModeIsActive()) {
      stack.run()

      telemetry.addLine("runtime: $runtime")
      telemetry.update()
    }
  }
}
