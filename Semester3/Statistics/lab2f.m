function y=lab2f()
k=0;
coin1=rand;
if coin1 < 0.5
    fprintf("Coin toss 1: tails\n");
else
    fprintf("Coin toss 1: head\n");
    k=k+1;
end
coin2=rand;
if coin2 < 0.5
    fprintf("Coin toss 2: tails\n");
else
    fprintf("Coin toss 2: head\n");
    k=k+1;
end
coin3=rand;
if coin3 < 0.5
    fprintf("Coin toss 3: tails\n");
else
    fprintf("Coin toss 3: head\n");
    k=k+1;
end
fprintf("X = %d\n",k);
fprintf("P(X) = %4.4f\n", binopdf(k,3,0.5));


