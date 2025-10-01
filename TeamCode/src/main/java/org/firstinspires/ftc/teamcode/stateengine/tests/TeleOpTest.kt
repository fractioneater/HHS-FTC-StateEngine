package org.firstinspires.ftc.teamcode.stateengine.tests

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State
import org.firstinspires.ftc.teamcode.states.teleop.examples.DriveTeleop

@Suppress("unused")
@TeleOp(name = "TeleOp Test", group = "Testing")
class TeleOpTest : LinearOpMode() {
  private val runtime = ElapsedTime()
  private val rh = RobotHardware(this)

  override fun runOpMode() {
    rh.initialize()

    val driveTest: State = DriveTeleop()
    driveTest.init(rh)

    // Wait for the game to start (driver presses PLAY)
    telemetry.addLine("initialized")
    telemetry.update()

    waitForStart()
    runtime.reset()

    // run until the end of the match (driver presses STOP)
    while (opModeIsActive()) {
      if (!driveTest.isDone) {
        driveTest.run()
      }

      telemetry.addLine("runtime: $runtime")
      telemetry.update()
    }
  }
}
