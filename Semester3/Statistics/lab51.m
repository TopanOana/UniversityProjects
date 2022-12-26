clear;
X = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7];
fprintf("a)\n");
lengthX = length(X);

sigma = 5;
confidence_level=input('confidence level=');
alpha = 1 - confidence_level;

lower_bounda = mean(X) - (sigma/sqrt(lengthX))*(norminv(1-alpha/2));
upper_bounda = mean(X) - (sigma/sqrt(lengthX))*(norminv(alpha/2));

fprintf("the confidence interval for the mean(sigma known) is (%4.4f , %4.4f)\n",lower_bounda, upper_bounda);


fprintf("b)\n");

lower_boundb = mean(X) - (std(X)/sqrt(lengthX))*tinv(1-alpha/2, lengthX-1);
upper_boundb = mean(X) - (std(X)/sqrt(lengthX))*tinv(alpha/2, lengthX-1);

fprintf("the confidence interval for the mean(sigma unknown) is (%4.4f, %4.4f)\n", lower_boundb, upper_boundb);

fprintf("c)\n");

lower_boundc = (lengthX-1)*(var(X))/chi2inv(1-alpha/2, lengthX-1);
upper_boundc = (lengthX-1)*(var(X))/chi2inv(alpha/2, lengthX-1);

fprintf("the confidence interval of the variance is (%4.4f, %4.4f)\n", lower_boundc, upper_boundc);

lower_boundc1 = sqrt(lower_boundc);
upper_boundc1 = sqrt(upper_boundc);

fprintf("the confidence interval of the standard deviation is (%4.4f, %4.4f)\n", lower_boundc1, upper_boundc1);


