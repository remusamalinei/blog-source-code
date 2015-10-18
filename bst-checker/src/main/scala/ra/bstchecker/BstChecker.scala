package ra.bstchecker

/**
 * @author Remus Amalinei
 */

object BstChecker {

  def check(root: Node): Boolean = {

    def go(node: Option[Node], min: Int, max: Int): Boolean = {
      node match {
        case Some(n) =>
          if ((n.left.isEmpty || n.left.get.value <= n.value) &&
            (n.right.isEmpty || n.right.get.value >= n.value) &&
            min <= n.value && n.value <= max) {

            go(n.left, min, n.value) && go(n.right, n.value, max)
          } else {
            false
          }
        case None => true
      }
    }

    go(Some(root), Int.MinValue, Int.MaxValue)
  }
}
