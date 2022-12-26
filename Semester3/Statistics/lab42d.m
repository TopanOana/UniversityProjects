p=input('p=');
n=20;
np=input('nr of successes=');
N=input('Number of simulations=');


for i=1:N
    X(i)=0;
    U=rand;
    aux_success=0;
    while aux_success<np 
       
        if U>=p
            X(i)=X(i)+(U<p);
        end
        U=rand;
        if U<p
            aux_success=aux_success+1;
        end
         
    end
end

%unique values obtained
U_X = unique(X);
%frequency of the values
n_X = hist(X,length(U_X));
rel_freq=n_X/N; %the relative frequency

[U_X;rel_freq];

plot(U_X, rel_freq,"x",0:n,nbinpdf(0:20,np,p),"o");
legend('approximation','nbinpdf');


