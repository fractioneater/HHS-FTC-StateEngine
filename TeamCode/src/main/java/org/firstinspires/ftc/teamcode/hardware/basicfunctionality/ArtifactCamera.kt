package org.firstinspires.ftc.teamcode.hardware.basicfunctionality

import android.graphics.Color
import android.util.Size
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.hardware.RobotHardware
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor
import org.firstinspires.ftc.vision.opencv.ColorRange
import org.firstinspires.ftc.vision.opencv.ImageRegion

// A couple of helpful subclasses.
enum class ArtifactColor { GREEN, PURPLE }
class VisibleArtifact(val color: ArtifactColor, val blob: ColorBlobLocatorProcessor.Blob)

// The majority of this code is from samples/ConceptVisionColorLocator_Circle
class ArtifactCamera(val rh: RobotHardware, val name: String) {
  private val greenLocator = ColorBlobLocatorProcessor.Builder()
    .setTargetColorRange(ColorRange.ARTIFACT_GREEN)
    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
    .setRoi(ImageRegion.asUnityCenterCoordinates(-1.0, 1.0, 1.0, -1.0))
    .setDrawContours(true)
    .setBoxFitColor(0)
    .setCircleFitColor(Color.rgb(0, 255, 0))
    .setBlurSize(5)
    .setDilateSize(15)
    .setErodeSize(15)
    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
    .build()

  private val purpleLocator = ColorBlobLocatorProcessor.Builder()
    .setTargetColorRange(ColorRange.ARTIFACT_PURPLE)
    .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY)
    .setRoi(ImageRegion.asUnityCenterCoordinates(-1.0, 1.0, 1.0, -1.0))
    .setDrawContours(true)
    .setBoxFitColor(0)
    .setCircleFitColor(Color.rgb(200, 0, 255))
    .setBlurSize(5)
    .setDilateSize(15)
    .setErodeSize(15)
    .setMorphOperationType(ColorBlobLocatorProcessor.MorphOperationType.CLOSING)
    .build()

  private val aprilTag = AprilTagProcessor.Builder()
    .setDrawTagID(true)
    .setDrawTagOutline(true)
    .setDrawAxes(false)
    .setDrawCubeProjection(true)
    .build()

  @Suppress("unused")
  private val portal = VisionPortal.Builder()
    .addProcessors(greenLocator, purpleLocator, aprilTag)
    .setCameraResolution(Size(320, 240)) // Higher resolution is WORSE for this purpose
    .setCamera(rh.op.hardwareMap.get(WebcamName::class.java, "cam"))
    .build()

  init {
    aprilTag.setDecimation(3f)
  }

  // --- PROPERTIES ---
  // Getter syntax: instead of calling camera.getArtifacts(), we can just write camera.artifacts and it will evaluate this code.

  val artifacts: List<VisibleArtifact>
    get() {
      val greenBlobs = greenLocator.blobs
      val purpleBlobs = purpleLocator.blobs

      // Filter by area of blob. We may want to adjust this range.
      ColorBlobLocatorProcessor.Util.filterByCriteria(ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA, 50.0, 20_000.0, greenBlobs)
      ColorBlobLocatorProcessor.Util.filterByCriteria(ColorBlobLocatorProcessor.BlobCriteria.BY_CONTOUR_AREA, 50.0, 20_000.0, purpleBlobs)

      // Filter by how circular the blobs are. Shadows will affect this, so we'll need to test on each field.
      ColorBlobLocatorProcessor.Util.filterByCriteria(ColorBlobLocatorProcessor.BlobCriteria.BY_CIRCULARITY, 0.6, 1.0, greenBlobs)
      ColorBlobLocatorProcessor.Util.filterByCriteria(ColorBlobLocatorProcessor.BlobCriteria.BY_CIRCULARITY, 0.6, 1.0, purpleBlobs)
      
      return greenBlobs.map { it -> VisibleArtifact(ArtifactColor.GREEN, it) } +
        purpleBlobs.map { it -> VisibleArtifact(ArtifactColor.PURPLE, it) }
    }

  val aprilTags: List<AprilTagDetection>
    get() {
      return aprilTag.detections
    }

  val freshAprilTags: List<AprilTagDetection>
    get() {
      return aprilTag.freshDetections
    }
}