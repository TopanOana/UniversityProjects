function y=lab32b()
p=input('p=');
n=input('n=');
if(p>0.05 || n<30)
    fprintf('error\n');
    return;
end
x=0:n;
y1=binopdf(x,n,p);
y2=poisspdf(x,n*p);
plot(x,y1,x,y2);

legend("binopdf", "poisspdf");
pause(0.5);
