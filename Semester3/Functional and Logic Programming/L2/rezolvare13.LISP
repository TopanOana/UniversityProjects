; 13. For a given tree of type (2) return the path from the root node to a certain given node X.

;cadr L ->left subtree
;caddr L->right subtree
;(A (B) (C (D) (E))) ->TYPE 2

(defun pathNode (L X)
  (cond
   ((NULL L) NIL)
   ((EQUAL (CAR L) X) (LIST X))
   ((MEMBER X (SETQ AUX (pathNode (CADR L) X))) (CONS (CAR L) AUX))
   ((MEMBER X (SETQ AUX (pathNode (CADDR L) X))) (CONS (CAR L) AUX))
   (T NIL)
  )
)
