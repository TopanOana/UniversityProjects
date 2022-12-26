;1 again
; mathematical model
; linearization_with_true(l1..ln, E) =
;  = (), if n=0
;  = (T), if n=1 and l1=E
;  = (NIL), if n=1 and l1!=E
;  = linearization_with_true(l1,E) U linearization_with_true(l2,E) U ... U linearization_with_true(l3,E), otherwise 
(defun linearization_with_true (L E)
  (cond
   ((NULL L) ())
   ((and (atom L) (equal L E)) (LIST T))
   ((atom L) (LIST NIL))
   (T (mapcan #'(lambda(x) (linearization_with_true x E)) L))
  )
)



; mathematical model
; or_list(l1...ln) = 
;  = NIL, if n=0
;  = T, if l1=T
;  = or_list(l2...ln), otherwise
(defun or_list (L)
  (cond 
    ((NULL L) NIL)
    ((equal (car L) T) T)
    (T (or_list (cdr L)))
   )
)

(defun main (L E)
  (or_list (linearization_with_true L E))
)