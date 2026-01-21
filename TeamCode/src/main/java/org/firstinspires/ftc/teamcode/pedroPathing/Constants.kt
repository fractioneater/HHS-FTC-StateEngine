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
  var followerConstants = FollowerConstants().mass(7.0)!! // TODO!

  var pathConstraints: PathConstraints = PathConstraints(0.99, 100.0, 1.0, 1.0)

  @JvmStatic
  fun createFollower(hardwareMap: HardwareMap): Follower {
    return FollowerBuilder(followerConstants, hardwareMap)
      .pathConstraints(pathConstraints)
      .mecanumDrivetrain(driveConstants)
      .threeWheelLocalizer(localizerConstants)
      .build()
  }

  var driveConstants: MecanumConstants = MecanumConstants()
    .maxPower(1.0)
    .rightFrontMotorName("rfD")
    .rightRearMotorName("rbD")
    .leftRearMotorName("lbD")
    .leftFrontMotorName("lfD")
    .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
    .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
    .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
    .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)

  var localizerConstants: ThreeWheelConstants = ThreeWheelConstants()
    .forwardTicksToInches(.001989436789)
    .strafeTicksToInches(.001989436789)
    .turnTicksToInches(.001989436789)
    .leftPodY(5.433) // Done by CAD measurement FOR 6383! TODO: 15317
    .rightPodY(-5.433) // Done by CAD measurement FOR 6383! Same TODO as above.
    .strafePodX(-2.5) // TODO! Onshape can't give this one.
    .leftEncoder_HardwareMapName("lfD") // TODO: the names of these three
    .rightEncoder_HardwareMapName("rbD") // ...
    .strafeEncoder_HardwareMapName("rfD") // ...
    .leftEncoderDirection(Encoder.FORWARD)
    .rightEncoderDirection(Encoder.FORWARD)
    .strafeEncoderDirection(Encoder.FORWARD)
}