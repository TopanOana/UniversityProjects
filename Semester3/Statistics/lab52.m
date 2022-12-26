clear;
X1=[22.4 21.7 24.5 23.4 21.6 23.3 22.4 21.6 24.8 20.0];
n1=length(X1);
X2=[17.7 14.8 19.6 19.6 12.1 14.8 15.4 12.6 14.0 12.2];
n2=length(X2);

confidence_level=input('confidence level=');
alpha=1-confidence_level;

fprintf("a)\n");
sp=((n1-1)*(var(X1))+(n2-1)*(var(X2)))/(n1+n2-2);
lower_bounda = mean(X1)-mean(X2) - tinv(1-alpha/2, n1+n2-2)*sqrt(sp)*sqrt(1/n1+1/n2);
upper_bounda = mean(X1)-mean(X2) + tinv(1-alpha/2, n1+n2-2)*sqrt(sp)*sqrt(1/n1+1/n2);

fprintf("the confidence interval for the difference (%4.4f, %4.4f)\n", lower_bounda, upper_bounda);

fprintf("b)\n");
c = (var(X1)/n1)/((var(X1)/n1)+(var(X2)/n2));
invn = (c^2)/(n1-1)+((1-c)^2)/(n2-1);
n = (invn)^(-1);
lower_boundb = mean(X1)-mean(X2) - tinv(1-alpha/2, n)*sqrt((var(X1)/n1)+(var(X2)/n2));
upper_boundb = mean(X1)-mean(X2) + tinv(1-alpha/2, n)*sqrt((var(X1)/n1)+(var(X2)/n2));

fprintf("the confidence interval for the difference (%4.4f, %4.4f)\n", lower_boundb, upper_boundb);

fprintf("c)\n");



lower_boundc = 1/finv(1-alpha/2, n1-1, n2-1)*var(X1)/var(X2);
upper_boundc = 1/finv(alpha/2, n1-1, n2-1)*var(X1)/var(X2);

fprintf("the confidence interval for the difference (%4.4f, %4.4f)\n", lower_boundc, upper_boundc);


lower_boundc1 = sqrt(lower_boundc);
upper_boundc1 = sqrt(upper_boundc);

fprintf("the confidence interval for the difference (%4.4f, %4.4f)\n", lower_boundc1, upper_boundc1);
