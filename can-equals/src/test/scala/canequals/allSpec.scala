package canequals

import hedgehog._
import hedgehog.runner._

import language.strictEquality

/** @author Kevin Lee
  * @since 2021-05-19
  */
object allSpec extends Properties {
  override def tests: List[Test] = List(
    property("test Some == Some", testSomeEqualsSome),
    property("test Some != Some", testSomeNotEqualSome),
    property("test Some == None", testSomeEqualsNone),
    property("test None == Some", testNoneEqualsSome),
    property("test Some != None", testSomeNotEqualNone),
    property("test None != Some", testNoneNotEqualSome),
    example("test None == None", testNoneEqualsNone),
    example("test None != None", testNoneNotEqualNone),
    property("test Some pattern matching", testSomeEqualsSomePatternMatching),
  ) ++ List(
    property("test Right == Right", testRightEqualsRight),
    property("test Right != Right", testRightNotEqualsRight),
    property("test Left == Left", testLeftEqualsLeft),
    property("test Left != Left", testLeftNotEqualsLeft),
    property("test Right == Left", testRightEqualsLeft),
    property("test Left == Right", testLeftEqualsRight),
    property("test Right != Left", testRightNotEqualsLeft),
    property("test Left != Right", testLeftNotEqualsRight),
  ) ++ List(
    property("(Int, String) == (Int, String)", testTuple2EqualsTuple2),
    property("(Int, String, Boolean) == (Int, String, Boolean)", testTuple3EqualsTuple3),
    property(
      "(Int, String, Boolean, Char) == (Int, String, Boolean, Char)",
      testTuple4EqualsTuple4
    ),
    property(
      "(Int, String, Boolean, Char, Double) == (Int, String, Boolean, Char, Double)",
      testTuple5EqualsTuple5
    ),
    property(
      "(Int, String, Boolean, Char, Double, Int) == (Int, String, Boolean, Char, Double, Int)",
      testTuple5EqualsTuple6
    ),
    property(
      "(Int, String, Boolean, Char, Double, Int, String) == (Int, String, Boolean, Char, Double, Int, String)",
      testTuple5EqualsTuple7
    ),
  )

  import canequals.all.given

  def testSomeEqualsSome: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {

    val o1: Option[Int] = Some(a)
    val o2: Option[Int] = Some(a)
    Result.diffNamed("Some == Some", o1, o2)(_ == _)
  }

  def testSomeNotEqualSome: Property = for {
    a1 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a1")
    a2 <- Gen
            .int(Range.linear(Int.MinValue, Int.MaxValue))
            .map { n =>
              if (n == a1)
                n + 1
              else
                n
            }
            .log("a2")
  } yield {

    val o1: Option[Int] = Some(a1)
    val o2: Option[Int] = Some(a2)
    Result.diffNamed("Some != Some", o1, o2)(_ != _)
  }

  def testSomeEqualsNone: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {

    val o1: Option[Int] = Some(a)
    val o2: Option[Int] = None
    Result.diffNamed("Some == None should be false", o1, o2)((x1, x2) => (x1 == x2) == false)
  }

  def testNoneEqualsSome: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {

    val o1: Option[Int] = None
    val o2: Option[Int] = Some(a)
    Result.diffNamed("Some == None should be false", o1, o2)((x1, x2) => (x1 == x2) == false)
  }

  def testSomeNotEqualNone: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {

    val o1: Option[Int] = Some(a)
    val o2: Option[Int] = None
    Result.diffNamed("Some != None should be true", o1, o2)(_ != _)
  }

  def testNoneNotEqualSome: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {

    val o1: Option[Int] = None
    val o2: Option[Int] = Some(a)
    Result.diffNamed("Some != None should be true", o1, o2)(_ != _)
  }

  def testNoneEqualsNone: Result = {

    val o1: Option[Int] = None
    val o2: Option[Int] = None
    Result.diffNamed("None == None should be true", o1, o2)(_ == _)
  }

  def testNoneNotEqualNone: Result = {

    val o1: Option[Int] = None
    val o2: Option[Int] = None
    Result.diffNamed("None != None should be false", o1, o2)((x1, x2) => (x1 != x2) == false)
  }

  def testSomeEqualsSomePatternMatching: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {

    val o1: Option[Int] = Some(a)
    o1 match {
      case Some(a) =>
        Result.success
      case None    =>
        Result.failure.log(s"Pattern matching for Option does not work. $o1")
    }
  }

  def testRightEqualsRight: Property = for {
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {

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

    val r1: Either[String, Int] = Right(b1)
    val r2: Either[String, Int] = Right(b2)
    Result.diffNamed("Right != Right", r1, r2)(_ != _)
  }

  def testLeftEqualsLeft: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
  } yield {

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

    val l1: Either[String, Int] = Left(a1)
    val l2: Either[String, Int] = Left(a2)
    Result.diffNamed("Left == Left", l1, l2)(_ != _)
  }

  def testRightEqualsLeft: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {

    val r1: Either[String, Int] = Right(b)
    val r2: Either[String, Int] = Left(a)
    Result.diffNamed("Right == Left should be false", r1, r2)((x, y) => (x == y) == false)
  }

  def testLeftEqualsRight: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {

    val r1: Either[String, Int] = Left(a)
    val r2: Either[String, Int] = Right(b)
    Result.diffNamed("Left == Right should be false", r1, r2)((x, y) => (x == y) == false)
  }

  def testRightNotEqualsLeft: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {

    val r1: Either[String, Int] = Right(b)
    val r2: Either[String, Int] = Left(a)
    Result.diffNamed("Right != Left should be true", r1, r2)(_ != _)
  }

  def testLeftNotEqualsRight: Property = for {
    a <- Gen.string(Gen.unicodeAll, Range.linear(0, 20)).log("a")
    b <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("b")
  } yield {

    val r1: Either[String, Int] = Left(a)
    val r2: Either[String, Int] = Right(b)
    Result.diffNamed("Left != Right should be true", r1, r2)(_ != _)
  }

  def genTuple5: Gen[(Int, String, Boolean, Char, Double)] = for {
    t1 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue))
    t2 <- Gen.string(Gen.unicodeAll, Range.linear(0, 10))
    t3 <- Gen.boolean
    t4 <- Gen.unicodeAll
    t5 <- Gen.double(Range.linearFrac(Double.MinValue, Double.MaxValue))
  } yield (t1, t2, t3, t4, t5)

  def testTuple2EqualsTuple2: Property = for {
    tuple <- genTuple5.map(_.take(2)).log("tuple")
  } yield {

    Result.diffNamed("(Int, String) == (Int, String)", tuple, tuple)(_ == _)
  }

  def testTuple3EqualsTuple3: Property = for {
    tuple <- genTuple5.map(_.take(3)).log("tuple")
  } yield {
    import canequals.tuples.canEqualEmptyTuple
    import canequals.tuples.canEqualTuple

    Result.diffNamed("(Int, String, Boolean) == (Int, String, Boolean)", tuple, tuple)(_ == _)
  }

  def testTuple4EqualsTuple4: Property = for {
    tuple <- genTuple5.map(_.take(4)).log("tuple")
  } yield {

    Result.diffNamed(
      "(Int, String, Boolean, Char) == (Int, String, Boolean, Char)",
      tuple,
      tuple
    )(_ == _)
  }

  def testTuple5EqualsTuple5: Property = for {
    tuple <- genTuple5.log("tuple")
  } yield {
    import canequals.tuples.canEqualEmptyTuple
    import canequals.tuples.canEqualTuple

    Result.diffNamed(
      "(Int, String, Boolean, Char, Double) == (Int, String, Boolean, Char, Double)",
      tuple,
      tuple
    )(_ == _)
  }

  def testTuple5EqualsTuple6: Property = for {
    t1    <- genTuple5.log("t1")
    tuple <- genTuple5.map(t2 => (t1 ++ t2).take(6)).log("tuple")
  } yield {

    Result.diffNamed(
      "(Int, String, Boolean, Char, Double, Int) == (Int, String, Boolean, Char, Double, Int)",
      tuple,
      tuple
    )(_ == _)
  }

  def testTuple5EqualsTuple7: Property = for {
    t1    <- genTuple5.log("t1")
    tuple <- genTuple5.map(t2 => (t1 ++ t2).take(7)).log("tuple")
  } yield {

    Result.diffNamed(
      "(Int, String, Boolean, Char, Double, Int, String) == (Int, String, Boolean, Char, Double, Int, String)",
      tuple,
      tuple
    )(_ == _)
  }

}
