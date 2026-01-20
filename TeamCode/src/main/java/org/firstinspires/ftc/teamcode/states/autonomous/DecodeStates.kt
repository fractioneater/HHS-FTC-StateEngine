package org.firstinspires.ftc.teamcode.states.autonomous

import com.pedropathing.follower.Follower
import com.pedropathing.geometry.Pose
import com.pedropathing.paths.PathChain
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.teamcode.stateengine.State

object Poses {
  val startingPose = Pose(72.0, 120.0, Math.toRadians(90.0)) // This is the only one that can't be private.
  val scorePose = Pose(72.0, 20.0, Math.toRadians(115.0))
  val PPGPose = Pose(100.0, 83.5, Math.toRadians(0.0))
  val PGPPose = Pose(100.0, 59.5, Math.toRadians(0.0))
  val GPPPose = Pose(100.0, 35.5, Math.toRadians(0.0))
}

class PathState(val follower: Follower, val path: PathChain) : State {
  override fun init(rh: RobotHardware) {}

  override fun run() {
    follower.followPath(path)
  }

  override val isDone: Boolean
    get() = !follower.isBusy
}

class AutoState : State {
  private lateinit var rh: RobotHardware
  override fun init(rh: RobotHardware) {
    this.rh = rh
  }

  override fun run() {
    if (rh.runtime.seconds() < 0.5) {
      rh.driveH.drive(0.7, 0.0, 0.0, 1.0)
    } else {
      rh.driveH.drive(0.0, 0.0, 0.0, 1.0)
    }
  }

  override val isDone: Boolean
    get() = false
}
