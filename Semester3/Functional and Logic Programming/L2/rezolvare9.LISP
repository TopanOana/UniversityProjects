; 9. Convert a tree of type (1) to type (2).

;type 2: (A (B) (C (D E)))
;type 1: (A 2 B 0 C 2 D 0 E 0)

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


;convert12
(defun convert12 (L)
  (cond 
   ((null L) nil)
   ((equal (cadr L) 0) (LIST (car L)))
   (T (LIST (car L) (convert12 (getLeftSubtree L)) (convert12 (getRightSubtree L))))
  )
)