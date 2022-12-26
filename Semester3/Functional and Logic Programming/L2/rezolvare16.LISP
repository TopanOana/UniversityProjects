;16. Determine if a tree of type (2) is ballanced (the difference between the depth of two subtrees is equal to 1).
;cadr L -> left subtree
;caddr L-> right subtree
;(A (B) (C (D) (E))) ->type 2

(defun depth (L)
  (cond 
   ((NULL L) 0)
   (T (+ 1 (MAX (DEPTH (CADR L)) (DEPTH (CADDR L)))))
  )
)

;CHECK BALANCED
(defun balanced (L)
  (cond 
   ((NULL L) NIL)
   (T (<= (- (max (depth (cadr L)) (depth (caddr L))) (min (depth (cadr L)) (depth (caddr L)))) 1))
  )
)