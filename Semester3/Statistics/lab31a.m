function y=lab31a()
string=input('DISTRIBUTIONS: \na)normal \nb)Student \nc)chi2 \nd)Fischer\n','s');
a1=0;
a2=0;
b1=0;
b2=0;
c=0;
d=0;
switch(string)
    case 'normal'
        fprintf('Normal distribution\n');
        mu=input('mu=');
        sigma=input('\nsigma=');
        rezNorm=normcdf(0,mu,sigma);
        a1=rezNorm;
        a2=1-rezNorm;
        b1=normcdf(1,mu,sigma)-normcdf(-1,mu,sigma);
        b2=1-b1;
        alpha=input('alpha=');
        if(alpha>=1 || alpha<=0)
            fprintf('error!\n')
            return;
        else
            c=norminv(alpha,mu,sigma);
        end
        beta=input('beta=');
        if(beta>=1 || beta<=0)
            fprintf('error!\n')
            return;
        else
            d=norminv(1-beta,mu,sigma);
        end
    case 'Student'
        fprintf('Student distribution\n');
        n=input('n=');
        rezStudent=tcdf(0,n);
        a1=rezStudent;
        a2=1-rezStudent;
        b1=tcdf(1,n)-tcdf(-1,n);
        b2=1-b1;
        alpha=input('alpha=');
        if(alpha>=1 || alpha<=0)
            fprintf('error!\n');
            return;
        else
            c=tinv(alpha,n);
        end
        beta=input('beta=');
        if(beta>=1 || beta<=0)
            fprintf('error!\n');
            return;
        else
            d=tinv(1-beta,n);
        end
    case 'Fischer'
        fprintf('Fischer distribution\n');
        n=input('n=');
        m=input('m=');
        rezFischer=fcdf(0,n,m);
        a1=rezFischer;
        a2=1-rezFischer;
        b1=fcdf(1,n,m)-fcdf(-1,n,m);
        b2=1-b1;
        alpha=input('alpha=');
        if(alpha>=1 || alpha<=0)
            fprintf('error!\n')
            return;
        else
            c=finv(alpha,n,m);
        end
        beta=input('beta=');
        if(beta>=1 || beta<=0)
            fprintf('error!\n');
            return;
        else
            d=finv(1-beta,n,m);
        end
    case 'chi2'
        fprintf('Chi2 distribution\n');
        n=input('n=');
        rezChi2=chi2cdf(0,n);
        a1=rezChi2;
        a2=1-rezChi2;
        b1=chi2cdf(1,n)-chi2cdf(-1,n);
        b2=1-b1;
        alpha=input('alpha=');
        if(alpha>=1 || alpha<=0)
            fprintf('error!\n');
            return;
        else
            c=chi2inv(alpha,n);
        end
        beta=input('beta=');
        if(beta>=1 || beta<=0)
            fprintf('error!\n');
            return;
        else
            d=chi2inv(1-beta,n);
        end
    otherwise
        fprintf('Option unavailable\n');
        return;
end
fprintf('a\n')
fprintf('\nP(x<=0)=%4.4f\n',a1);
fprintf('\nP(x>=0)=%4.4f\n',a2);
fprintf('b\n');
fprintf('\nP(-1<=X<=1)=%4.4f\n',b1);
fprintf('\nP(x<=-1 or x>=1)=%4.4f\n',b2);
fprintf('c\n');
fprintf('xalpha=%4.4f where P(x<xalpha)=alpha\n',c);
fprintf('d\n');
fprintf('xbeta=%4.4f where P(x>xbetaa)=beta\n',d);
