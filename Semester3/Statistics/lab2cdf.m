function y=lab2cdf()
n=input("Enter number of trials: ");
p=input("Enter the probability of success: ");
xx=0:0.01:n;
k=0:n;
y=[0:n ; binopdf(k,n,p)];
y0=binopdf(k,n,p);
y1=binocdf(xx,n,p);
plot(k,y0, "+")
hold on
plot(xx,y1);
axis([-0.1,3.1,-0.1,1.1]);
hold off
title('Graphs')
legend("pdf", "cdf");