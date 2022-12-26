; 14. Write a function that returns the number of atoms in a list, at any level.

;mathematical model
; nr_atoms(l1...ln)
; = 0, n=0
; = 1, n=1
; = nr_atoms(l1) + nr_atoms(l2) + ... + nr_atoms(ln), otherwise

(defun nr_atoms (L)
  (cond 
   ((null L) 0)
   ((atom L) 1)
   (T (apply #'+ (mapcar #'(lambda(x)(nr_atoms x)) L)))
  )
)