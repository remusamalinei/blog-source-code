package ra.bstchecker

/**
 * @author Remus Amalinei
 */

object BstChecker {

  def check(root: Node): Boolean = {

    def go(node: Option[Node], min: Int, max: Int): Boolean = {
      node match {
        case Some(n) =>
          if (betweenChildren(n) && betweenAncestors(n.value, min, max)) {
            go(n.left, min, n.value) && go(n.right, n.value, max)
          } else {
            false
          }
        case None => true
      }
    }

    def betweenChildren(node: Node): Boolean = {
      greaterThanLeft(node) && lessThanRight(node)
    }

    def greaterThanLeft(node: Node): Boolean = {
      node.left match {
        case Some(l) => l.value <= node.value
        case None => true
      }
    }

    def lessThanRight(node: Node): Boolean = {
      node.right match {
        case Some(r) => r.value >= node.value
        case None => true
      }
    }

    def betweenAncestors(value: Int, min: Int, max: Int): Boolean = (min <= value) && (value <= max)

    go(Some(root), Int.MinValue, Int.MaxValue)
  }
}
