package canequals

import hedgehog._
import hedgehog.runner._

import language.strictEquality

/** @author Kevin Lee
  * @since 2021-05-19
  */
object OptionInstancesSpec extends Properties {
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
  )

  def testSomeEqualsSome: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {
    import canequals.options.canEqualOptions
    val o1: Option[Int] = Some(a)
    val o2: Option[Int] = Some(a)
    Result.diffNamed("Some == Some", o1, o2)(_ == _)
  }

  def testSomeNotEqualSome: Property = for {
    a1 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a1")
    a2 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue))
      .map { n =>
        if (n == a1) n + 1 else n
      }
      .log("a2")
  } yield {
    import canequals.options.canEqualOptions
    val o1: Option[Int] = Some(a1)
    val o2: Option[Int] = Some(a2)
    Result.diffNamed("Some != Some", o1, o2)(_ != _)
  }

  def testSomeEqualsNone: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {
    import canequals.options.canEqualOptions
    val o1: Option[Int] = Some(a)
    val o2: Option[Int] = None
    Result.diffNamed("Some == None should be false", o1, o2)((x1, x2) => (x1 == x2) == false)
  }

  def testNoneEqualsSome: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {
    import canequals.options.canEqualOptions
    val o1: Option[Int] = None
    val o2: Option[Int] = Some(a)
    Result.diffNamed("Some == None should be false", o1, o2)((x1, x2) => (x1 == x2) == false)
  }

  def testSomeNotEqualNone: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {
    import canequals.options.canEqualOptions
    val o1: Option[Int] = Some(a)
    val o2: Option[Int] = None
    Result.diffNamed("Some != None should be true", o1, o2)(_ != _)
  }

  def testNoneNotEqualSome: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {
    import canequals.options.canEqualOptions
    val o1: Option[Int] = None
    val o2: Option[Int] = Some(a)
    Result.diffNamed("Some != None should be true", o1, o2)(_ != _)
  }

  def testNoneEqualsNone: Result = {
    import canequals.options.canEqualOptions
    val o1: Option[Int] = None
    val o2: Option[Int] = None
    Result.diffNamed("None == None should be true", o1, o2)(_ == _)
  }

  def testNoneNotEqualNone: Result = {
    import canequals.options.canEqualOptions
    val o1: Option[Int] = None
    val o2: Option[Int] = None
    Result.diffNamed("None != None should be false", o1, o2)((x1, x2) => (x1 != x2) == false)
  }

  def testSomeEqualsSomePatternMatching: Property = for {
    a <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("a")
  } yield {
    import canequals.options.canEqualOption
    val o1: Option[Int] = Some(a)
    o1 match {
      case Some(a) => Result.success
      case None => Result.failure.log(s"Pattern matching for Option does not work. $o1")
    }
  }

}
