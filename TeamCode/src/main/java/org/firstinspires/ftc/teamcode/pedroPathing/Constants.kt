package org.firstinspires.ftc.teamcode.pedroPathing

import com.pedropathing.follower.Follower
import com.pedropathing.follower.FollowerConstants
import com.pedropathing.ftc.FollowerBuilder
import com.pedropathing.ftc.drivetrains.MecanumConstants
import com.pedropathing.ftc.localization.Encoder
import com.pedropathing.ftc.localization.constants.DriveEncoderConstants
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
      .driveEncoderLocalizer(localizerConstants)
      .build()
  }

  var driveConstants: MecanumConstants? = MecanumConstants()
    .maxPower(1.0)
    .rightFrontMotorName("rfD")
    .rightRearMotorName("rbD")
    .leftRearMotorName("lbD")
    .leftFrontMotorName("lfD")
    .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
    .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
    .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
    .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)

  var localizerConstants: DriveEncoderConstants? = DriveEncoderConstants()
    .rightFrontMotorName("rfD")
    .rightRearMotorName("rbD")
    .leftRearMotorName("lbD")
    .leftFrontMotorName("lfD")
    .leftFrontEncoderDirection(Encoder.FORWARD)
    .leftRearEncoderDirection(Encoder.FORWARD)
    .rightFrontEncoderDirection(Encoder.REVERSE)
    .rightRearEncoderDirection(Encoder.REVERSE)
    .robotLength(8.42)
    .robotWidth(14.33)
//    .forwardTicksToInches()
  // TODO NEXT: Forward tuner.
}