package org.firstinspires.ftc.teamcode.opmodes.autonomous.examples

import com.acmerobotics.roadrunner.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.SeriesStack
import org.firstinspires.ftc.teamcode.stateengine.State
import org.firstinspires.ftc.teamcode.states.autonomous.examples.SplinePathRR

@Suppress("unused")
@Autonomous(name = "RoadRunner Testing", group = "Examples")
class ExampleRoadRunnerAutonomous : LinearOpMode() {
  private val rh = RobotHardware(this)

  override fun runOpMode() {
    rh.initialize()

    val states = arrayOf<State>(
      SplinePathRR(rh,
        arrayOf(
          Pose2d(0.0, 0.0, 0.0),  // start Pose2d
          Pose2d(20.0, 20.0, 0.0),
          Pose2d(20.0, -20.0, 0.0),  // start Pose2d of next SplinePathRR
        )
      ),
      SplinePathRR(rh,
        arrayOf(
          Pose2d(20.0, -20.0, 0.0),  // start Pose2d
          Pose2d(40.0, 0.0, 0.0),
          Pose2d(0.0, 0.0, 0.0),  // start Pose2d of next SplinePathRR
        )
      ),
    )
    val stack = SeriesStack(states)

    stack.init()

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
      }

      telemetry.addLine("runtime: ${rh.runtime}")
      rh.telemetry()
      telemetry.update()
    }
  }
}
