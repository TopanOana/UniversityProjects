clear;
X = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7];
%a.
alpha=input('significance level=');
m=5.5;
n=length(X);
tail=1;%RIGHT-tailed test

[H,P,CI,STATS]=ttest(X,m,alpha,tail);
tt=tinv(1-alpha,n-1);

fprintf("H0=%f\n",H);
fprintf("The rejection region is: (%f,%f)\n", tt, Inf);
fprintf("P=%f\n",P);
fprintf("TS0=%f\n",STATS.tstat);
fprintf("Check by H\n");
if (H==0)
    fprintf("The standard is met\nWe do not reject H0\n");
end
if(H==1)
    fprintf("The standard is not met\nWe reject H0\n");
end

fprintf("Check by significance\n");
if(alpha>=P)
    fprintf("The standard is not met\nWe reject H0\n");
end
if(alpha<P)
    fprintf("The standard is met\nWe do not reject H0\n");
end

fprintf("Check by hypothesis\n")
if (STATS.tstat<=tt)
    fprintf("The standard is met\nWe do not reject H0\n");
end
if (STATS.tstat>tt)
    fprintf("The standard is not met\nWe reject H0\n");
end


