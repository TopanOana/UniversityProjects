; 16. Write a function that produces the linear list of all atoms of a given list, from all levels,
; and written in the same order. Eg.: (((A B) C) (D E)) --> (A B C D E)


;mathematical model
; linearization (l1...ln)
; = (), n=0
; = (l1), n=1
; = linearization(l1) U linearization(l2) U ... U linearization(ln), otherwise


(defun linearization (L)
  (cond
   ((null L) NIL)
   ((atom L) (LIST L))
   (T (mapcan #'(lambda(x) (linearization x)) L))
  )
)