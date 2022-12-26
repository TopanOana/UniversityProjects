p=input('p=');
n=input('nr of trials=');
N=input('Number of simulations=');

for i=1:N
    %the ith simulation
    X(i)=0;
    for j=1:n
        U=rand;
        X(i)=X(i)+(U<p);
    end
end


%unique values obtained
U_X = unique(X);
%frequency of the values
n_X = hist(X,length(U_X));
rel_freq=n_X/N; %the relative frequency

[U_X;rel_freq]

plot(U_X, rel_freq,"x",0:n,binopdf(0:n,n,p),"o");
legend('approximation','binopdf');

