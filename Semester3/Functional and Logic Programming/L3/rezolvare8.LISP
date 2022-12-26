; 8. Write a function to determine the number of nodes on the level k from a n-tree
; represented as follows: (root list_nodes_subtree1 ... list_nodes_subtreen)
; Eg: tree is (a (b (c)) (d) (e (f))) and k=1 => 3 nodes


;mathematical model
; nr_nodes_k(k, l1...ln)
; = 0 , n=0
; = 1, n=1 and k=0
; = 0, n=1 and k!=0
; = nr_nodes_k(k, l1)+ nr_nodes_k(k-1, l2) + ... + nr_nodes_k(k-m, ln), otherwise

(defun nr_nodes_k (K L)
  (cond
   ((null L) 0)
   ((and (atom L) (equal K 0)) 1)
   ((atom L) 0)
   (T (apply #'+ (mapcar #'(lambda(x) (nr_nodes_k (- K 1) x)) L)))
  )
)