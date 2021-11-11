package com.bkoc.talite;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;

import com.bkoc.exchangeapi.*;
import com.bkoc.exchangeapi.exchanges.*;

public class Test {
    public static void main(String[] args) throws Exception {
        List<Candlestick> candle = Exmo.klines("BTC_USDT", Interval.INT_1WEEK);
        List<BigDecimal> close = General.getValuesOfCandlestics(candle, General.OHLCV.CLOSE);
        List<BigDecimal> high = General.getValuesOfCandlestics(candle, General.OHLCV.HIGH);
        List<BigDecimal> low = General.getValuesOfCandlestics(candle, General.OHLCV.LOW);
//        close = close.subList(close.size() - 100, close.size());
//        high = high.subList(high.size() - 100, high.size());
//        low = low.subList(low.size() - 100, low.size());

//        System.out.println("ATR:");
//        List<BigDecimal> atr = Talite.ATR(high, low, close, 14, Talite.MA_TYPE.EMA);
//        for (BigDecimal x : atr)
//            System.out.println(x);
//        System.out.println("Size: " + atr.size());

//        System.out.println("VAR:");
//        List<BigDecimal> var = Talite.VAR(close, 2);
//        for (BigDecimal x : var)
//            System.out.println(x);
//        System.out.println("Size: " + var.size());

//        System.out.println("SMA:");
//        List<BigDecimal> sma = Talite.SMA(close, 14);
//        for (BigDecimal x : sma)
//            System.out.println(x);

//        System.out.println("EMA:");
//        List<BigDecimal> ema = Talite.EMA(close, 14);
//        for (BigDecimal x : ema)
//            System.out.println(x);
//        System.out.println("Size: " + ema.size());

//        System.out.println("RMA:");
//        List<BigDecimal> rma = Talite.RMA(close, 14);
//        for (BigDecimal x : rma)
//            System.out.println(x);

//        System.out.println("WMA:");
//        List<BigDecimal> wma = Talite.WMA(close, 9);
//        for (BigDecimal x : wma)
//            System.out.println(x);

//        System.out.println("RSI:");
//        List<BigDecimal> rsi = Talite.RSI(close, 14);
//        for (BigDecimal x : rsi)
//            System.out.println(x);

//        System.out.println("STOCH:");
//        HashMap<String, List<BigDecimal>> stoch = Talite.STOCHIndicator(close,high,low,14, 3, 3);
//        List<BigDecimal> stoch_k = stoch.get("k");
//        List<BigDecimal> stoch_d = stoch.get("d");
//        for (int i = 0; i < stoch_d.size(); i++)
//            System.out.println("k: " + stoch_k.get(i) + " -> d: " + stoch_d.get(i));

//        System.out.println("STOCH_RSI:");
//        HashMap<String, List<BigDecimal>> stoch = Talite.STOCHRSI(close,3, 3, 14, 14);
//        List<BigDecimal> stoch_k = stoch.get("k");
//        List<BigDecimal> stoch_d = stoch.get("d");
//        for (int i = 0; i < stoch_d.size(); i++)
//            System.out.println("k: " + stoch_k.get(i) + " -> d: " + stoch_d.get(i));

//        System.out.println("BOLLINGER BAND:");
//        HashMap<String, List<BigDecimal>> stoch = Talite.BBANDS(close,20, 2);
//        List<BigDecimal> upper = stoch.get("upper");
//        List<BigDecimal> middle = stoch.get("middle");
//        List<BigDecimal> lower = stoch.get("lower");
//        for (int i = 0; i < middle.size(); i++)
//            System.out.println("Middle: " + middle.get(i) + " -> Upper: " + upper.get(i) + " -> Lower: " + lower.get(i));
//        System.out.println("Size: " + upper.size());

//        System.out.println("MACD:");
//        HashMap<String, List<BigDecimal>> macdHash = Talite.MACD(close,12, 26, 9, Talite.MA_TYPE.EMA, Talite.MA_TYPE.EMA);
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

//        System.out.println("OTT");
//        HashMap<String, List<BigDecimal>> hash = Talite.OTT(close, 2, 1.4f, Talite.MA_TYPE.VAR);
//        List<BigDecimal> VAR = hash.get("MA");
//        List<BigDecimal> OTT = hash.get("OTT");
//        for (int i = 0; i < VAR.size(); i++)
//            System.out.println("VAR: " + VAR.get(i) + " -> OTT: " + OTT.get(i));

//        System.out.println("PMax");
//        HashMap<String, List<BigDecimal>> hash = Talite.PMAX(high, low, close, 10, 3, 10, Talite.MA_TYPE.EMA);
//        if (!Objects.isNull(hash)) {
//            List<BigDecimal> EMA = hash.get("MA");
//            List<BigDecimal> PMAX = hash.get("PMAX");
//            System.out.println("EMA: " + EMA.size() + "\nPMAX: " + PMAX.size());
//            for (int i = 0; i < EMA.size(); i++)
//                System.out.println("VAR: " + EMA.get(i) + " -> PMAX: " + PMAX.get(i));
//        }
//        else
//            System.out.println("PMAX NULL!");
    }
}
