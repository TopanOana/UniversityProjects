p=input('p=');
N=input('Number of simulations=');

for i=1:N
    %the ith simulation
    U=rand;
    X(i)=(U<p);
end

%unique values obtained
U_X = unique(X);
%frequency of the values
n_X = hist(X,length(U_X));

[U_X;n_X];
rel_freq=n_X/N%the relative frequency

