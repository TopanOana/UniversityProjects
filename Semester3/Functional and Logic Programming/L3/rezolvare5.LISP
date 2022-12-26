;5. Write a function that computes the sum of even numbers and decrease the sum of odd numbers, at any level of a list.

; suma nr pare  - suma nr impare

;mathematical model
; diferenta(l1...ln)
; = 0, n=0
; = l, n=1 l even number
; = -l, n=1 l odd number
; = 0, n=1 l not number
; = diferenta(l1) + diferenta(l2) +...+ diferenta(ln), otherwise 

(defun diferenta (L)
  (cond 
   ((null L) 0)
   ((and (numberp L) (evenp L)) L)
   ((and (numberp L) (oddp L)) (- 0 L))
   ((atom L) 0)
   (T (apply #'+ (mapcar #'(lambda(x) (diferenta X)) L)))
  )
)