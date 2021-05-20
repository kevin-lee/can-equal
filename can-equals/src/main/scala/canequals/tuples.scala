package canequals

/** @author Kevin Lee
  * @since 2021-05-19
  */
object tuples extends TupleInstances

trait TupleInstances {

  given canEqualEmptyTuple: CanEqual[EmptyTuple, EmptyTuple] = CanEqual.derived

  given canEqualTuple[H1, T1 <: Tuple, H2, T2 <: Tuple](
    using eqHead: CanEqual[H1, H2],
    eqTail: CanEqual[T1, T2]
  ): CanEqual[H1 *: T1, H2 *: T2] = CanEqual.derived

}
