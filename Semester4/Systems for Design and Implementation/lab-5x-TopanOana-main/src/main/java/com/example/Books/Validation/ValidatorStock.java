package com.example.Books.Validation;

import com.example.Books.Exception.StockValidationException;
import com.example.Books.Model.Stock;
import com.example.Books.Repository.StockRepository;

public class ValidatorStock implements Validator<Stock>{

    private StockRepository stockRepository;
    public ValidatorStock(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }
    @Override
    public void validate(Stock stock) {
        StringBuilder errors = new StringBuilder();
        if(stock.getQuantity()<1 || stock.getQuantity()>1000)
            errors.append("stock quantity invalid (quantity <1 or quantity>1000)\n");
        if (stockRepository.getStockByBookIdAndStoreId(stock.getBook().getId(), stock.getStore().getId())!=null)
            errors.append("stock already exists in the store for the book\n");
        if (!errors.isEmpty())
            throw new StockValidationException(errors.toString());
    }
}
