; 10. Define a function that replaces one node with another one in a n-tree represented as: 
; root list_of_nodes_subtree1... list_of_nodes_subtreen)
; Eg: tree is (a (b (c)) (d) (e (f))) and node 'b' will be replace with node 'g' => tree (a (g (c)) (d) (e (f)))}

;mathematical model
; inloc_tree (e a l1..ln)
; = (), n=0
; = a, l=e
; = l, n=1
; = inloc_tree(e a l1) U inloc_tree(e a l2) U ... U inloc_tree(e a ln), otherwise

(defun inloc_tree (E A L)
   (cond 
    ((null L) nil)
    ((and (atom L) (equal L E) A))
    ((atom L) L)
    (T (mapcar #'(lambda(x) (inloc_tree E A x)) L))
   )
)