package canequal

import hedgehog._
import hedgehog.runner._

import language.strictEquality

/** @author Kevin Lee
  * @since 2021-05-19
  */
object EitherInstancesSpec extends Properties {
  override def tests: List[Test] = List(
    property("test Right == Right", testRightEqualsRight),
    property("test Right != Right", testRightNotEqualsRight),
    property("test Left == Left", testLeftEqualsLeft),
    property("test Left != Left", testLeftNotEqualsLeft),
    property("test Right == Left", testRightEqualsLeft),
    property("test Left == Right", testLeftEqualsRight),
    property("test Right != Left", testRightNotEqualsLeft),
    property("test Left != Right", testLeftNotEqualsRight),
  )

  def testRightEqualsRight: Property = for {
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {
    import canequal.eithers.canEqualEither

    val r1: Either[String, Int] = Right(b)
    val r2: Either[String, Int] = Right(b)
    Result.diffNamed("Right == Right", r1, r2)(_ == _)
  }

  def testRightNotEqualsRight: Property = for {
    b1 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b1")
    b2 <- Gen
            .int(Range.linear(Int.MinValue, Int.MaxValue))
            .map { n =>
              if (n == b1)
                n + 1
              else
                n
            }
            .log("b2")
  } yield {
    import canequal.eithers.canEqualEither

    val r1: Either[String, Int] = Right(b1)
    val r2: Either[String, Int] = Right(b2)
    Result.diffNamed("Right != Right", r1, r2)(_ != _)
  }

  def testLeftEqualsLeft: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
  } yield {
    import canequal.eithers.canEqualEither

    val l1: Either[String, Int] = Left(a)
    val l2: Either[String, Int] = Left(a)
    Result.diffNamed("Left == Left", l1, l2)(_ == _)
  }

  def testLeftNotEqualsLeft: Property = for {
    a1 <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a1")
    a2 <- Gen
            .string(Gen.unicodeAll, Range.linear(0, 20))
            .map { s =>
              if (s == a1)
                s + "a"
              else
                s
            }
            .log("a2")
  } yield {
    import canequal.eithers.canEqualEither

    val l1: Either[String, Int] = Left(a1)
    val l2: Either[String, Int] = Left(a2)
    Result.diffNamed("Left == Left", l1, l2)(_ != _)
  }

  def testRightEqualsLeft: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {
    import canequal.eithers.canEqualEither

    val r1: Either[String, Int] = Right(b)
    val r2: Either[String, Int] = Left(a)
    Result.diffNamed("Right == Left should be false", r1, r2)((x, y) => (x == y) == false)
  }

  def testLeftEqualsRight: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {
    import canequal.eithers.canEqualEither

    val r1: Either[String, Int] = Left(a)
    val r2: Either[String, Int] = Right(b)
    Result.diffNamed("Left == Right should be false", r1, r2)((x, y) => (x == y) == false)
  }

  def testRightNotEqualsLeft: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {
    import canequal.eithers.canEqualEither

    val r1: Either[String, Int] = Right(b)
    val r2: Either[String, Int] = Left(a)
    Result.diffNamed("Right != Left should be true", r1, r2)(_ != _)
  }

  def testLeftNotEqualsRight: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {
    import canequal.eithers.canEqualEither

    val r1: Either[String, Int] = Left(a)
    val r2: Either[String, Int] = Right(b)
    Result.diffNamed("Left != Right should be true", r1, r2)(_ != _)
  }

}
