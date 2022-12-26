; 1. Write a function to check if an atom is member of a list (the list is non-liniar)

;mathematical model
; f4(l1...ln, e)
; = nil, n=0
; = T, n=1 l=e
; = nil, n=1 l!=e
; = f4(l1, e) U f4(l2, e) U ... U f4(ln, e), otherwise
(defun f4 (L E)
  (cond
   ((null L) NIL)
   ((atom L) (EQUAL L E))
   (T (mapcar #'(lambda(x) (f4 x E)) L))
  )
)

;mathematical model
; f6(l1...ln)
; = nil, n=0
; = l, n=1
; = f6(l1) or f6(l2...ln), otherwise
(defun f6 (L)
  (cond
   ((null L) NIL)
   ((atom L)  L)
   (T (OR (f6 (car L)) (f6 (cdr L))))
   )
)

(defun final (L E)
  (f6 (f4 L E))
)


