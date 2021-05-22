package canequal

import hedgehog._
import hedgehog.runner._

import language.strictEquality

/** Tests here are not so important. The important thing is that the code here compiles.
  * @author Kevin Lee
  * @since 2021-05-21
  */
object CollectionInstancesSpec extends Properties {

  override def tests: List[Test] = List(
    property("test non-empty List pattern matching", testNonEmptyListPatternMatching),
    example("test Nil pattern matching", testNilPatternMatching),
    example("test non-empty List == Nil", testNonEmptyListEqualsNil),
    example("test non-empty List == List()", testNonEmptyListEqualsEmptyList),
    example("test non-empty Seq == Seq()", testNonEmptySeqEqualsEmptySeq),
  )

  def testNonEmptyListPatternMatching: Property = for {
    ns <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).list(Range.linear(1, 10)).log("ns")
  } yield {
    import canequal.collections.canEqualSeq
    ns match {
      case x :: xs =>
        Result.success
      case Nil     =>
        Result.failure.log("ns should not be Nil")
    }
  }

  def testNilPatternMatching: Result = {
    import canequal.collections.canEqualSeq
    val ns: List[Int] = Nil
    ns match {
      case x :: xs =>
        Result.failure.log("ns should not be non-empty List")
      case Nil     =>
        Result.success
    }
  }

  def testNonEmptyListEqualsNil: Result = {
    import canequal.collections.canEqualSeq
    val ns = List(1, 2, 3)
    Result.diffNamed("List(1, 2, 3) == Nil should return false", ns, Nil)((a, b) => (a == b) == false)
  }

  def testNonEmptyListEqualsEmptyList: Result = {
    import canequal.collections.canEqualSeq
    val ns = List(1, 2, 3)
    Result.diffNamed("List(1, 2, 3) == Nil should return false", ns, List())((a, b) => (a == b) == false)
  }

  def testNonEmptySeqEqualsEmptySeq: Result = {
    import canequal.collections.canEqualSeq
    val ns = Seq(1, 2, 3)
    Result.diffNamed("Seq(1, 2, 3) == Seq should return false", ns, List())((a, b) => (a == b) == false)
  }

}
