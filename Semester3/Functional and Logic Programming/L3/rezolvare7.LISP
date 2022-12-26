; 7. Write a function that substitutes an element E with all elements of a list L1 at all levels of a given list L.

;mathematical model
; inlocuire( e l1..ln, L1)
; = (), n=0
; = L1, n=1 and l=e
; = L, n=1 and l!=e
; = inlocuire(e l1 L1) U inlocuire(e l2 L1) U ... U inlocuire(e l3 L1), otherwise
(defun inlocuire (E L L1)
  (cond 
   ((null L) NIL)
   ((and (atom L) (equal L E)) L1)
   ((atom L) L)
   (T (mapcar #'(lambda(x) (inlocuire E x L1)) L))
  )
)