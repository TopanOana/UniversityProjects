function y=lab2amatrix()
n=input("Enter number of trials: ");
p=input("Enter the probability of success: ");
k=0:n;
y=[0:n ; binopdf(k,n,p)];