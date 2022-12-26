;14. Determine the list of nodes accesed in postorder in a tree of type (1).

;goLeft ->returns the left subtree
(defun goLeft (L nrNodes nrEdges)
  (cond
   ((null l) nil)
   ((EQUAL nrNodes (+ 1 nrEdges)) nil)
   (T (cons (car L) (cons (cadr L) (goLeft (cddr L) (+ 1 nrNodes) (+ (cadr L) nrEdges)))))
  )
)


;getLeftSubTree
(defun getLeftSubtree (L)
  (goLeft (cddr L) 0 0)
)


;goRight -> returns the right subtree
(defun goRight (L nrNodes nrEdges)
  (cond
   ((null L) nil)
   ((EQUAL nrNodes (+ 1 nrEdges)) L)
   (T (goRight (cddr L) (+ 1 nrNodes) (+ (cadr L) nrEdges)))
  )
)


;getRightSubtree
(defun getRightSubtree (L)
  (goRight (cddr L) 0 0)
)


;appendLists 
(defun appendLists (L P)
  (cond
   ((NULL L) P)
   ((NULL P) L)
   (T (CONS (CAR L) (appendLists (CDR L) P)))
  )
)


;postorderTraversal
(defun postorderTraversal (L)
  (cond 
   ((NULL L) NIL)
   ((NULL (getLeftSubtree L)) (LIST (CAR L)))
   ((NULL (getRightSubtree L)) (LIST (postorderTraversal (getLeftSubtree L)) (CAR L)))
   (T (appendLists (appendLists (postorderTraversal (getLeftSubtree L)) (postorderTraversal (getRightSubtree L))) (LIST (CAR L))))
  )
)