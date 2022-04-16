package ra.sab

import org.scalameter.api.LoggingReporter
import org.scalameter.execution.SeparateJvmsExecutor
import org.scalameter.picklers.Implicits._
import org.scalameter.{Aggregator, Bench, Executor, Gen, Key, Measurer, Persistor}

/**
  * @author Remus Amalinei
  */
object SortAlgorithmsBenchmark extends Bench[Double] {

  override lazy val executor = SeparateJvmsExecutor(
    new Executor.Warmer.Default,
    Aggregator.average,
    measurer)

  override lazy val measurer = new Measurer.Default
  override lazy val reporter = new LoggingReporter[Double]
  override lazy val persistor = Persistor.None

  val size = 200000

  val exponentialSizes = Gen.exponential(s"0% initially sorted; size")(1, Math.pow(2, 18).asInstanceOf[Int], 2)
  val exponentialSequences = for {
    es <- exponentialSizes
  } yield SortAlgorithms.partiallySortedArray(es, 0)

  val bigPercents = Gen.enumeration(s"size = $size; initially sorted percentage")(80, 85, 90, 95, 98, 99, 100)
  val bigPercentSequences = for {
    p <- bigPercents
  } yield {
    val inPlaceSize = (p / 100.0) * size
    SortAlgorithms.partiallySortedArray(size, inPlaceSize.asInstanceOf[Int])
  }

  val percents = Gen.enumeration(s"size = $size; initially sorted percentage")(0, 1, 2, 4, 8, 16, 32, 48, 64, 80, 85, 90, 95, 98, 99, 100)
  val percentSequences = for {
    p <- percents
  } yield {
    val inPlaceSize = (p / 100.0) * size
    SortAlgorithms.partiallySortedArray(size, inPlaceSize.asInstanceOf[Int])
  }

  val benchRuns = Key.exec.benchRuns -> 128
  
  performance of "Sort algorithms" in {

    measure method "sort with exponential sizes and 0% initially sorted" config benchRuns in {

      using(exponentialSequences) in { es =>
        SortAlgorithms.insertionSort(es)
      }

      using(exponentialSequences) in { es =>
        SortAlgorithms.heapsort(es)
      }

      using(exponentialSequences) in { es =>
        es.sorted
      }
    }

    measure method "sort with various initially sorted percentages" config benchRuns in {

      using(bigPercentSequences) in { s =>
        SortAlgorithms.insertionSort(s)
      }

      using(percentSequences) in { s =>
        SortAlgorithms.heapsort(s)
      }

      using(percentSequences) in { s =>
        s.sorted
      }
    }
  }
}
