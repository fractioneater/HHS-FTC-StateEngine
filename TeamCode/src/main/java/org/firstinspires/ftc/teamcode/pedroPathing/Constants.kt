package org.firstinspires.ftc.teamcode.pedroPathing

import com.pedropathing.follower.Follower
import com.pedropathing.follower.FollowerConstants
import com.pedropathing.ftc.FollowerBuilder
import com.pedropathing.ftc.drivetrains.MecanumConstants
import com.pedropathing.ftc.localization.Encoder
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants
import com.pedropathing.paths.PathConstraints
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap

object Constants {
  val followerConstants = FollowerConstants().mass(7.0)!! // TODO!

  val pathConstraints: PathConstraints = PathConstraints(0.99, 100.0, 1.0, 1.0)

  @JvmStatic
  fun createFollower(hardwareMap: HardwareMap): Follower {
    return FollowerBuilder(followerConstants, hardwareMap)
      .pathConstraints(pathConstraints)
      .mecanumDrivetrain(driveConstants)
      .threeWheelLocalizer(
        try {
          if (hardwareMap.get("this-is-6383") != null) localizerConstants6383 else localizerConstants15317
        } catch (_: Exception) {
          localizerConstants15317
        }
      )
      .build()
  }

  val driveConstants: MecanumConstants = MecanumConstants()
    .maxPower(1.0)
    .rightFrontMotorName("rfD")
    .rightRearMotorName("rbD")
    .leftRearMotorName("lbD")
    .leftFrontMotorName("lfD")
    .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
    .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
    .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
    .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)

  var baseLocalizerConstants: ThreeWheelConstants = ThreeWheelConstants()
    .forwardTicksToInches(.001989436789)
    .strafeTicksToInches(.001989436789)
    .turnTicksToInches(.001989436789)
    .leftEncoder_HardwareMapName("lfD") // TODO: the names of these three, possibly put these into robot-based constants
    .rightEncoder_HardwareMapName("rbD") // ...
    .strafeEncoder_HardwareMapName("rfD") // ...
    .leftEncoderDirection(Encoder.FORWARD)
    .rightEncoderDirection(Encoder.FORWARD)
    .strafeEncoderDirection(Encoder.FORWARD)

  val localizerConstants6383: ThreeWheelConstants = baseLocalizerConstants
    .leftPodY(5.433) // Done by CAD measurement and WRONG (TODO)
    .rightPodY(-5.433)
    .strafePodX(-2.5) // TODO! Onshape can't give this one.

  val localizerConstants15317: ThreeWheelConstants = baseLocalizerConstants
    .leftPodY(5.9) // Done by CAD measurement
    .rightPodY(-5.9)
    .strafePodX(-2.5) // TODO! Onshape can't give this one.
}