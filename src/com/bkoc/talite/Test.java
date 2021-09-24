package com.bkoc.talite;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;

import com.bkoc.exchangeapi.*;
import com.bkoc.exchangeapi.exchanges.Binance;
import com.bkoc.exchangeapi.exchanges.FTX;

public class Test {
    public static void main(String[] args) throws Exception {
        List<Candlestick> candle = Binance.klines("BTCUSDT", Interval.INT_1MONTH, 300);
        List<BigDecimal> close = General.getValuesOfCandlestics(candle, General.OHLCV.CLOSE);
        List<BigDecimal> high = General.getValuesOfCandlestics(candle, General.OHLCV.HIGH);
        List<BigDecimal> low = General.getValuesOfCandlestics(candle, General.OHLCV.LOW);

//        System.out.println("SMA:");
//        List<BigDecimal> sma = Talite.SMAIndicator(close, 14);
//        for (BigDecimal x : sma)
//            System.out.println(x);

//        System.out.println("EMA:");
//        List<BigDecimal> ema = Talite.EMAIndicator(close, 9);
//        for (BigDecimal x : ema)
//            System.out.println(x);

//        System.out.println("RMA:");
//        List<BigDecimal> rma = Talite.RMAIndicator(close, 10);
//        for (BigDecimal x : rma)
//            System.out.println(x);

//        System.out.println("WMA:");
//        List<BigDecimal> wma = Talite.WMAIndicator(close, 9);
//        for (BigDecimal x : wma)
//            System.out.println(x);

//        System.out.println("RSI:");
//        List<BigDecimal> rsi = Talite.RSIIndicator(close, 14);
//        for (BigDecimal x : rsi)
//            System.out.println(x);

//        System.out.println("STOCH:");
//        HashMap<String, List<BigDecimal>> stoch = Talite.STOCHIndicator(close,high,low,14, 3, 3);
//        List<BigDecimal> stoch_k = stoch.get("k");
//        List<BigDecimal> stoch_d = stoch.get("d");
//        for (int i = 0; i < stoch_d.size(); i++)
//            System.out.println("k: " + stoch_k.get(i) + " -> d: " + stoch_d.get(i));

//        System.out.println("STOCH_RSI:");
//        HashMap<String, List<BigDecimal>> stoch = Talite.STOCHRSIIndicator(close,3, 3, 14, 14);
//        List<BigDecimal> stoch_k = stoch.get("k");
//        List<BigDecimal> stoch_d = stoch.get("d");
//        for (int i = 0; i < stoch_d.size(); i++)
//            System.out.println("k: " + stoch_k.get(i) + " -> d: " + stoch_d.get(i));

//        System.out.println("BOLLINGER BAND:");
//        HashMap<String, List<BigDecimal>> stoch = Talite.BBANDSIndicator(close,20, 2);
//        List<BigDecimal> upper = stoch.get("upper");
//        List<BigDecimal> middle = stoch.get("middle");
//        List<BigDecimal> lower = stoch.get("lower");
//        for (int i = 0; i < middle.size(); i++)
//            System.out.println("Middle: " + middle.get(i) + " -> Upper: " + upper.get(i) + " -> Lower: " + lower.get(i));

//        System.out.println("MACD:");
//        HashMap<String, List<BigDecimal>> macdHash = Talite.MACDIndicator(close,12, 26, 9, Talite.MA_TYPE.SMA, Talite.MA_TYPE.SMA);
//        List<BigDecimal> macd = macdHash.get("macd");
//        List<BigDecimal> signal = macdHash.get("signal");
//        List<BigDecimal> hist = macdHash.get("hist");
//        for (int i = 0; i < macd.size(); i++)
//            System.out.println("macd: " + macd.get(i) + " -> signal: " + signal.get(i) + " -> hist: " + hist.get(i));

//        System.out.println("MavilimW:");
//        List<BigDecimal> mavW = Talite.MavilimW(close, 3, 5);
//        for (BigDecimal x : mavW)
//            System.out.println(x);

//        System.out.println("CCI:");
//        List<BigDecimal> cci = Talite.CCI(high, low, close, 20);
//        for (BigDecimal x : cci)
//            System.out.println(x);

//        System.out.println("ICHIMOKU:");
//        HashMap<String, List<BigDecimal>> ichimoku = Talite.ICHIMOKU(high, low, close, 9, 26, 52, 26);
//        List<BigDecimal> conversionLine = ichimoku.get("conversionLine");
//        List<BigDecimal> baseLine = ichimoku.get("baseLine");
//        List<BigDecimal> laggingSpan = ichimoku.get("laggingSpan");
//        List<BigDecimal> leadLine1 = ichimoku.get("leadLine1");
//        List<BigDecimal> leadLine2 = ichimoku.get("leadLine2");
//        for (int i = 0; i < conversionLine.size(); i++)
//            System.out.println("conversionLine: " + conversionLine.get(i)
//                    + " -> baseLine: " + baseLine.get(i)
//                    + " -> laggingSpan: " + laggingSpan.get(i)
//                    + " -> leadLine1: " + leadLine1.get(i)
//                    + " -> leadLine2: " + leadLine2.get(i));
//        System.out.println("ConversionLineSize: " + conversionLine.size()
//                + "\nbaseLineSize: " + baseLine.size()
//                + "\nlaggingSpanSize: " + laggingSpan.size()
//                + "\nleadLine1Size: " + leadLine1.size()
//                + "\nleadLine2Size: " + leadLine2.size());
//
//        int lastTenkanSen = conversionLine.size() - 26;
//        System.out.println("Last Conversion: " + conversionLine.get(lastTenkanSen) + "\nLast Base Line: " + baseLine.get(lastTenkanSen));

//        System.out.println("ATR:");
//        List<BigDecimal> atr = Talite.ATR(high, low, close, 10, Talite.MA_TYPE.RMA);
//        for (BigDecimal x : atr)
//            System.out.println(x);

//        System.out.println("SUPERTREND:");
//        List<BigDecimal> supertrend = Talite.SUPERTREND(high, low, close, 10, 3);
//        for (BigDecimal x : supertrend)
//            System.out.println(x);

//        System.out.println("VAR");
//        HashMap<String, List<BigDecimal>> hash = Talite.OTT(close, 2, 1.4f);
//        List<BigDecimal> VAR = hash.get("VAR");
//        List<BigDecimal> OTT = hash.get("OTT");
//        for (int i = 0; i < VAR.size(); i++)
//            System.out.println("VAR: " + VAR.get(i) + " -> OTT: " + OTT.get(i));
    }
}
