function y=lab32a()
p=input('p=');
if(p<0.05 || p>=0.95)
    fprintf('error\n');
    return;
end
for n=0:5:100
    x=0:n;
    y1=binopdf(x,n,p);
    y2=normpdf(x,n*p,sqrt(n*p*(1-p)));
    plot(x,y1,x,y2);
    title("BINO APROX FOR n="+n);
    legend("binopdf", "normpdf");
    pause(0.5);
end