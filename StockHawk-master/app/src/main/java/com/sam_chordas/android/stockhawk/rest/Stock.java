package com.sam_chordas.android.stockhawk.rest;

/**
 * Created by Suj🌠 on 07-01-2017.
 */

public class Stock {

    private String sym;
    private String change;
    private String bidPrice;
    private boolean isUp;

    public Stock(){}

    public Stock(String symbol, String bidPrice, String change, boolean isUp) {
        this.sym = symbol;
        this.bidPrice = bidPrice;
        this.change = change;
        this.isUp = isUp;
    }


    public void setSymbol(String symbol){
        this.sym = symbol;
    }
    public String getSymbol(){
        return sym;
    }

    public void setBidPrice(String bidPrice){
        this.bidPrice = bidPrice;
    }
    public String getBidPrice(){
        return bidPrice;
    }

    public void setChange(String change){
        this.change = change;
    }
    public String getChange(){
        return change;
    }

    public boolean GetIsUp() {
        return isUp;
    }
    public void setIsUp(boolean isUp) {
        isUp = isUp;
    }
}

