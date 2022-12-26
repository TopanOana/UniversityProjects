function y = lab2ainput()
n=input("Enter number of trials: ");
p=input("Enter the probability of success: ");
k=0:n;
y=binopdf(k,n,p);