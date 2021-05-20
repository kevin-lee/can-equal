package canequals

import hedgehog._
import hedgehog.runner._

import language.strictEquality

/** @author Kevin Lee
  * @since 2021-05-19
  */
object TupleInstancesSpec extends Properties {
  override def tests: List[Test] = List(
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
    import canequals.tuples.canEqualEmptyTuple
    import canequals.tuples.canEqualTuple

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
    import canequals.tuples.canEqualEmptyTuple
    import canequals.tuples.canEqualTuple

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
    import canequals.tuples.canEqualEmptyTuple
    import canequals.tuples.canEqualTuple

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
    import canequals.tuples.canEqualEmptyTuple
    import canequals.tuples.canEqualTuple

    Result.diffNamed(
      "(Int, String, Boolean, Char, Double, Int, String) == (Int, String, Boolean, Char, Double, Int, String)",
      tuple,
      tuple
    )(_ == _)
  }

}
