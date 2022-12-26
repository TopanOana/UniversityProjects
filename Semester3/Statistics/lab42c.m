p=input('p=');
n=20;
N=input('Number of simulations=');
trial=rand;
for i=1:N
    X(i)=0;
    U=rand;
    aux=1;
    while U<p && aux<n
        X(i)=X(i)+(U<p);
        U=rand;
        aux=aux+1;
    end
end

%unique values obtained
U_X = unique(X);
%frequency of the values
n_X = hist(X,length(U_X));
rel_freq=n_X/N; %the relative frequency

[U_X;rel_freq];

plot(U_X, rel_freq,"x",0:n,geopdf(0:n,p),"o");
legend('approximation','geopdf');


