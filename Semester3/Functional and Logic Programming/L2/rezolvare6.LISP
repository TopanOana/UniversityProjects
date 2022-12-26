; 6. Return the list of nodes of a tree of type (1) accessed inorder.
;(A 2 B 0 C 2 D 0 E 0) (1) ->type 1
; inorder traversal : B A D C E 


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



;inorderTraversal
(defun inorderTraversal (L)
  (cond 
   ((NULL L) NIL)
   ((NULL (getLeftSubtree L)) (LIST (CAR L)))
   ((NULL (getRightSubtree L)) (LIST (inorderTraversal (getLeftSubtree L)) (CAR L)))
   (T (appendLists (appendLists (inorderTraversal (getLeftSubtree L)) (LIST (CAR L))) (inorderTraversal (getRightSubtree L))))
   )
)


