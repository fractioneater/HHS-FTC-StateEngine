package org.firstinspires.ftc.teamcode.opmodes.autonomous

import com.pedropathing.geometry.BezierLine
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.pedroPathing.Constants
import org.firstinspires.ftc.teamcode.stateengine.SeriesStack
import org.firstinspires.ftc.teamcode.stateengine.State
import org.firstinspires.ftc.teamcode.states.autonomous.PathState
import org.firstinspires.ftc.teamcode.states.autonomous.Poses

@Suppress("unused")
@Autonomous(name = "2025-26 Decode AUTONOMOUS", group = "\"maybe the top?\"")
class DecodeAuto : LinearOpMode() {
  private val rh = RobotHardware(this)

  override fun runOpMode() {
    rh.initialize()

    val follower = Constants.createFollower(hardwareMap)
    follower.setStartingPose(Poses.startingPose)

    val states = arrayOf<State>(
      PathState(follower,
        follower.pathBuilder()
          .addPath(BezierLine(Poses.startingPose, Poses.GPPPose))
          .setLinearHeadingInterpolation(Poses.startingPose.heading, Poses.GPPPose.heading)
          .build()
      )
    )
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
        follower.update()
        stack.run()
        rh.update()
        val currentPose = follower.pose
        // TODO: Pose telemetry.
      }

      telemetry.addLine("runtime: ${rh.runtime}")
      rh.telemetry()
      telemetry.update()
    }
  }
}
