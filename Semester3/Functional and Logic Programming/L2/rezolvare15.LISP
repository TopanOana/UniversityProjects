;15. Determine the list of nodes accesed in postorder in a tree of type (2).
;cadr L ->left subtree
;caddr L -> right subtree
;(A (B) (C (D) (E))) -> type 2

;appendLists 
(defun appendLists (L P)
  (cond
   ((NULL L) P)
   ((NULL P) L)
   (T (CONS (CAR L) (appendLists (CDR L) P)))
  )
)

(defun postorderTraversal (L)
  (cond 
   ((NULL L) NIL)
   ((NULL (CADR L)) (LIST (CAR L)))
   ((NULL (CADDR L)) (LIST (postorderTraversal (CADR L)) (CAR L)))
   (T (appendLists (appendLists (postorderTraversal (CADR L)) (postorderTraversal (CADDR L))) (LIST (CAR L))))
  )
)
