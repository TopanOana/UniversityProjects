function y=lab2cde()
n=input("Enter number of trials: ");
p=input("Enter the probability of success: ");
xx=0:0.01:n;
k=0:n;
valuex0=binopdf(0,n,p);
fprintf("P(X=0) = %4.4f\n", valuex0);
valuex1=1-binopdf(1,n,p);
fprintf("P(X!=1) = %4.4f\n", valuex1);
valuelessequal2=binocdf(2,n,p);
fprintf("P(X<=2) = %4.4f\n", valuelessequal2);
valueless2=binocdf(1,n,p);
fprintf("P(X<2) = %4.4f\n", valueless2);
valuegreaterequal1=1-binocdf(0,n,p);
fprintf("P(X>=1) = %4.4f\n", valuegreaterequal1);
valuegreater1=1-binocdf(1,n,p);
fprintf("P(X>1) = %4.4f\n", valuegreater1);