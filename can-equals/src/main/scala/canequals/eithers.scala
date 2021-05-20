package canequals

/** @author Kevin Lee
  * @since 2021-05-19
  */
object eithers extends EitherInstances

trait EitherInstances {

  given canEqualEither[L1, R1, L2, R2](
    using eqL: CanEqual[L1, L2],
    eqR: CanEqual[R1, R2]
  ): CanEqual[Either[L1, R1], Either[L2, R2]] = CanEqual.derived

}
