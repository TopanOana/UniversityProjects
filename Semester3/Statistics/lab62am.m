clear;
X1=[22.4 21.7 24.5 23.4 21.6 23.3 22.4 21.6 24.8 20.0];
n1=length(X1);
X2=[17.7 14.8 19.6 19.6 12.1 14.8 15.4 12.6 14.0 12.2];
n2=length(X2);

alpha=input('significance level=');
tail=0;%two tailed test

%a
[H,P,CI,STATS]=vartest2(X1,X2,alpha,tail);
tt1=finv(alpha/2,n1-1,n2-1);
tt2=finv(1-alpha/2,n1-1,n2-1);

fprintf("H0=%f\n",H);
fprintf("P=%f\n",P);
fprintf("TS0=%f\n",STATS.fstat);
fprintf("The rejection region is: (%f,%f)U(%f,%f)\n",-Inf,tt1,tt2,Inf);

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
if (STATS.fstat<=tt2 && STATS.fstat>=tt1)
    fprintf("The standard is met\nWe do not reject H0\n");
end
if (STATS.fstat<tt1 && STATS.fstat>tt2)
    fprintf("The standard is not met\nWe reject H0\n");
end


%b 
% 
% if (H==0)
%     [H,P,CI,STATS]=ttest2(X1,X2,alpha,tail,'equal');
% end
% if (H==1)
%     [H,P,CI,STATS]=ttest2(X1,X2,alpha,tail,'unequal');
% end
% fprintf("H0=%f\n",H);
% fprintf("P=%f\n",P);
% fprintf("TS0=%f\n",STATS.fstat);
% fprintf("The rejection region is: (%f,%f)U(%f,%f)\n",-Inf,tt1,tt2,Inf);










