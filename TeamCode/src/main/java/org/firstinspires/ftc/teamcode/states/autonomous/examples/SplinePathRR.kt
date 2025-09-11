package org.firstinspires.ftc.teamcode.states.autonomous.examples

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.ftc.runBlocking
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive
import org.firstinspires.ftc.teamcode.stateengine.State

class SplinePathRR(val poses: Array<Pose2d>) : State {
  private lateinit var rh: RobotHardware
  override var isDone: Boolean = false

  private val drive = MecanumDrive(rh.op.hardwareMap, poses[0])
  private val builder = drive.actionBuilder(poses[0])

  override fun init(rh: RobotHardware) {
    for (pose in poses) {
      builder.splineTo(pose.position, pose.heading)
    }
  }

  override fun run() {
    isDone = true
    runBlocking(builder.build()) // TODO: Test this new RR 1.0.x code
  }

  override fun stop() {
    // TODO.
  }
}
