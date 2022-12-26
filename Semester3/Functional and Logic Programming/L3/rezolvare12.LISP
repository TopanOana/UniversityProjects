; 12. Write a function that substitutes an element through another one at all levels of a given list.

;mathematical model
; replce(e a l1...ln)
; = (), n=0
; = a, if l1 = e 
; = l1, if l1 atom
; = replce(e a l1) U replce(e a l2) U ... U replce(e a ln), otherwise 


(defun replce (E A L)
  (cond 
   ((null L) nil)
   ((and (atom L) (equal E L)) A)
   ((atom L) L)
   (T (mapcar #'(lambda(x)(replce E A x)) L))
  )
)