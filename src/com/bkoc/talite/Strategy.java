package com.bkoc.talite;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Strategy {
    public enum StrategyType {
        BUY,
        SELL
    }

    //GOLDEN & DEATH CROSS
    public static StrategyType GOLDENDEATHCROSSVALIDATION(List<BigDecimal> close, int shortMA, int longMA) {
        List<BigDecimal> ma50 = Talite.SMA(close, shortMA);
        List<BigDecimal> ma200 = Talite.SMA(close, longMA);

        if (!Objects.isNull(ma50) && !Objects.isNull(ma200)) {
            int last50 = ma50.size() - 1;
            int last200 = ma200.size() - 1;
            if (ma50.get(last50 - 2).compareTo(ma200.get(last200 - 2)) >= 0 &&
                    ma50.get(last50 - 1).compareTo(ma200.get(last200 - 1)) >= 0 &&
                    ma50.get(last50).compareTo(ma200.get(last200)) < 0)
                return StrategyType.SELL;
            else if (ma50.get(last50 - 2).compareTo(ma200.get(last200 - 2)) <= 0 &&
                    ma50.get(last50 - 1).compareTo(ma200.get(last200 - 1)) <= 0 &&
                    ma50.get(last50).compareTo(ma200.get(last200)) > 0)
                return StrategyType.BUY;
        }
        return null;
    }

    //OTT
    public static StrategyType OTT(List<BigDecimal> close, int ottLength, float ottPercent, Talite.MA_TYPE ottMAType) {
        HashMap<String, List<BigDecimal>> ottHash = Talite.OTT(close, ottLength, ottPercent, ottMAType);
        if (!Objects.isNull(ottHash)) {
            List<BigDecimal> ottMA = ottHash.get("MA");
            List<BigDecimal> ott = ottHash.get("OTT");
            if (!Objects.isNull(ottMA) && !Objects.isNull(ott)) {
                int lastOtt = ott.size() - 1;
                if (ottMA.get(lastOtt - 1).compareTo(ott.get(lastOtt - 1)) <= 0 && ottMA.get(lastOtt).compareTo(ott.get(lastOtt)) > 0) // BUY
                    return StrategyType.BUY;
                else if (ottMA.get(lastOtt - 1).compareTo(ott.get(lastOtt - 1)) >= 0 && ottMA.get(lastOtt).compareTo(ott.get(lastOtt)) < 0) // SELL
                    return StrategyType.SELL;
            }
        }
        return null;
    }

    //PMAX
    public static StrategyType PMAX(List<BigDecimal> high, List<BigDecimal> low, List<BigDecimal> close, int pmaxAtrLength, float pmaxAtrMult, int pmaxMALen, Talite.MA_TYPE pmaxMAType) {
        HashMap<String, List<BigDecimal>> pmaxHash = Talite.PMAX(high, low, close, pmaxAtrLength, pmaxAtrMult, pmaxMALen, pmaxMAType);
        if (!Objects.isNull(pmaxHash)) {
            List<BigDecimal> pmaxMA = pmaxHash.get("MA");
            List<BigDecimal> pmax = pmaxHash.get("PMAX");
            if (!Objects.isNull(pmaxMA) && !Objects.isNull(pmax)) {
                int lastPmax = pmax.size() - 1;
                if (pmaxMA.get(lastPmax - 2).compareTo(pmax.get(lastPmax - 2)) >= 0 && pmaxMA.get(lastPmax - 1).compareTo(pmax.get(lastPmax - 1)) >= 0 && pmaxMA.get(lastPmax).compareTo(pmax.get(lastPmax)) < 0) //BUY
                    return StrategyType.BUY;
                else if (pmaxMA.get(lastPmax - 2).compareTo(pmax.get(lastPmax - 2)) <= 0 && pmaxMA.get(lastPmax - 1).compareTo(pmax.get(lastPmax - 1)) <= 0 && pmaxMA.get(lastPmax).compareTo(pmax.get(lastPmax)) > 0) // SELL
                    return StrategyType.SELL;
            }
        }
        return null;
    }

    //STOCH RSI
    public static StrategyType STOCHRSI(List<BigDecimal> close, int smoothK, int smoothD, int lengthRSI, int lengthStoch, int oversold, int overbought) {
        HashMap<String, List<BigDecimal>> stochRsi = Talite.STOCHRSI(close, smoothK, smoothD, lengthRSI, lengthStoch);
        if (!Objects.isNull(stochRsi)) {
            List<BigDecimal> slowk = stochRsi.get("k");
            List<BigDecimal> slowd = stochRsi.get("d");
            if (!Objects.isNull(slowk) && !Objects.isNull(slowd)) {
                int lastSlow = slowk.size() - 1;
                if (slowk.get(lastSlow).compareTo(BigDecimal.valueOf(overbought)) >= 0 &&
                        slowd.get(lastSlow).compareTo(BigDecimal.valueOf(overbought)) >= 0 &&
                        slowk.get(lastSlow).compareTo(slowd.get(lastSlow)) < 0)
                    return StrategyType.SELL;
                else if (slowk.get(lastSlow).compareTo(BigDecimal.valueOf(oversold)) <= 0 &&
                        slowd.get(lastSlow).compareTo(BigDecimal.valueOf(oversold)) <= 0 &&
                        slowk.get(lastSlow).compareTo(slowd.get(lastSlow)) >= 0)
                    return StrategyType.BUY;
            }
        }
        return null;
    }

    //BOLLINGER BANDS
    public static StrategyType BOLLINGERBANDS(List<BigDecimal> close, int length, float stdDev) {
        HashMap<String, List<BigDecimal>> bb = Talite.BBANDS(close, length, stdDev);
        if (!Objects.isNull(bb)) {
            List<BigDecimal> bb_upper = bb.get("upper");
            List<BigDecimal> bb_lower = bb.get("lower");
            if (!Objects.isNull(bb_upper) && !Objects.isNull(bb_lower)) {
                int lastBB = bb_upper.size() - 1;
                if (close.get(close.size() - 1).compareTo(bb_lower.get(lastBB)) <= 0)
                    return StrategyType.BUY;
                else if (close.get(close.size() - 1).compareTo(bb_upper.get(lastBB)) >= 0)
                    return StrategyType.SELL;
            }
        }
        return null;
    }

    //MACD
    public static HashMap<String, StrategyType> MACD(List<BigDecimal> close, int fastLength, int slowLength, int signalSmoothLen, Talite.MA_TYPE oscMAType, Talite.MA_TYPE signalMAType) {
        HashMap<String, List<BigDecimal>> macdHash = Talite.MACD(close, fastLength, slowLength, signalSmoothLen, oscMAType, signalMAType);
        if (!Objects.isNull(macdHash)) {
            List<BigDecimal> macd = macdHash.get("macd");
            List<BigDecimal> macdSignal = macdHash.get("signal");
            HashMap<String, StrategyType> macdStrategy = new HashMap<>();

            if (!Objects.isNull(macd) && !Objects.isNull(macdSignal)) {
                int lastMacd = macd.size() - 1;

                //Signal-Line Crossover
                //NEGATİF bölgede macd, sinyali yukarı yönlü kesmelidir (al sinyali)
                if (macd.get(lastMacd).compareTo(BigDecimal.valueOf(0)) < 0 && macdSignal.get(lastMacd).compareTo(BigDecimal.valueOf(0)) < 0 &&
                        macd.get(lastMacd).compareTo(macdSignal.get(lastMacd)) > 0 && macd.get(lastMacd - 1).compareTo(macdSignal.get(lastMacd - 1)) <= 0)
                    macdStrategy.put("signal-line-crossover", StrategyType.BUY);
                    //POZİTİF bölgede macd, sinyali aşağı yönlü kesmelidir (sat sinyali)
                else if (macd.get(lastMacd).compareTo(BigDecimal.valueOf(0)) > 0 && macdSignal.get(lastMacd).compareTo(BigDecimal.valueOf(0)) > 0 &&
                        macd.get(lastMacd).compareTo(macdSignal.get(lastMacd)) < 0 && macd.get(lastMacd - 1).compareTo(macdSignal.get(lastMacd - 1)) >= 0)
                    macdStrategy.put("signal-line-crossover", StrategyType.SELL);

                //Zero Crossover (MACD Line crossover zero)
                //MACD Line break down zero line (sat sinyali)
                if (macd.get(lastMacd - 1).compareTo(BigDecimal.ZERO) > 0 && macd.get(lastMacd).compareTo(BigDecimal.ZERO) < 0)
                    macdStrategy.put("zero-line-crossover", StrategyType.SELL);
                    //MACD Line break up zero line (al sinyali)
                else if (macd.get(lastMacd - 1).compareTo(BigDecimal.ZERO) < 0 && macd.get(lastMacd).compareTo(BigDecimal.ZERO) > 0)
                    macdStrategy.put("zero-line-crossover", StrategyType.BUY);

                if (macdStrategy.size() != 0) return macdStrategy;
            }
        }
        return null;
    }

    //MAVILIMW
    public static StrategyType MAVILIMW(List<BigDecimal> close, int firstMALen, int secondMALen) {
        List<BigDecimal> mavilimw = Talite.MavilimW(close, firstMALen, secondMALen);
        if (!Objects.isNull(mavilimw)) {
            int lastMavilim = mavilimw.size() - 1;
            //support - destek
            if (mavilimw.get(lastMavilim).compareTo(mavilimw.get(lastMavilim - 1)) < 0 && close.get(close.size() - 2).compareTo(mavilimw.get(lastMavilim - 1)) < 0 && close.get(close.size() - 1).compareTo(mavilimw.get(lastMavilim)) >= 0)
                return StrategyType.BUY;
            else if (mavilimw.get(lastMavilim).compareTo(mavilimw.get(lastMavilim - 1)) > 0 && close.get(close.size() - 2).compareTo(mavilimw.get(lastMavilim - 1)) > 0 && close.get(close.size() - 1).compareTo(mavilimw.get(lastMavilim)) <= 0)
                return StrategyType.SELL;
        }
        return null;
    }

    //CCI
    public static StrategyType CCI(List<BigDecimal> high, List<BigDecimal> low, List<BigDecimal> close, int length, int oversold, int overbought) {
        List<BigDecimal> cci = Talite.CCI(high, low, close, length);
        if (!Objects.isNull(cci)) {
            int lastCci = cci.size() - 1;
            if (cci.get(lastCci).compareTo(BigDecimal.valueOf(oversold)) < 0) {
                return StrategyType.BUY;
            } else if (cci.get(lastCci).compareTo(BigDecimal.valueOf(overbought)) > 0) {
                return StrategyType.SELL;
            }
        }
        return null;
    }

    //ICHIMOKU
    public static HashMap<String, StrategyType> ICHIMOKU(List<BigDecimal> high, List<BigDecimal> low, List<BigDecimal> close, int conversionLineLen, int baseLineLen, int laggingSpan2Per, int displacement) {
        HashMap<String, List<BigDecimal>> ichimoku = Talite.ICHIMOKU(high, low, close, conversionLineLen, baseLineLen, laggingSpan2Per, displacement);
        if (!Objects.isNull(ichimoku)) {
            List<BigDecimal> tenkanSen = ichimoku.get("conversionLine");
            List<BigDecimal> kijunSen = ichimoku.get("baseLine");
            List<BigDecimal> chikoSpan = ichimoku.get("laggingSpan");
            List<BigDecimal> senkouSpanA = ichimoku.get("leadLine1");
            List<BigDecimal> senkouSpanB = ichimoku.get("leadLine2");
            if (!Objects.isNull(tenkanSen) && !Objects.isNull(kijunSen) && !Objects.isNull(chikoSpan) && !Objects.isNull(senkouSpanA) && !Objects.isNull(senkouSpanB)) {
                int lastSensouSpan = senkouSpanA.size() - 1;
                int lastTenkanSen = tenkanSen.size() - displacement;
                BigDecimal cloudTop = (senkouSpanA.get(lastTenkanSen).compareTo(senkouSpanB.get(lastTenkanSen)) > 0) ? senkouSpanA.get(lastTenkanSen) : senkouSpanB.get(lastTenkanSen);
                BigDecimal cloudBottom = (senkouSpanA.get(lastTenkanSen).compareTo(senkouSpanB.get(lastTenkanSen)) < 0) ? senkouSpanA.get(lastTenkanSen) : senkouSpanB.get(lastTenkanSen);
                int ichimoku_verification = 0;
                HashMap<String, StrategyType> ichimokuStrategy = new HashMap<>();

                /*Tenkan Sen - Kijun Sen Kesişimleri*/
                if (tenkanSen.get(lastTenkanSen).compareTo(kijunSen.get(lastTenkanSen)) > 0 && tenkanSen.get(lastTenkanSen - 1).compareTo(kijunSen.get(lastTenkanSen - 1)) < 0 &&
                        tenkanSen.get(lastTenkanSen).compareTo(cloudTop) > 0)
                    ichimokuStrategy.put("tenkansen-kijunsen-crossover", StrategyType.BUY);
                else if (tenkanSen.get(lastTenkanSen).compareTo(kijunSen.get(lastTenkanSen)) < 0 && tenkanSen.get(lastTenkanSen - 1).compareTo(kijunSen.get(lastTenkanSen - 1)) > 0 &&
                        tenkanSen.get(lastTenkanSen).compareTo(cloudBottom) < 0)
                    ichimokuStrategy.put("tenkansen-kijunsen-crossover", StrategyType.SELL);

                /*Ichimoku Cloud Kesişimleri*/
                if (senkouSpanA.get(lastSensouSpan).compareTo(senkouSpanB.get(lastSensouSpan)) > 0 && senkouSpanA.get(lastSensouSpan - 1).compareTo(senkouSpanB.get(lastSensouSpan - 1)) < 0)
                    ichimokuStrategy.put("ichimoku-cloud", StrategyType.BUY);
                else if (senkouSpanA.get(lastSensouSpan).compareTo(senkouSpanB.get(lastSensouSpan)) < 0 && senkouSpanA.get(lastSensouSpan - 1).compareTo(senkouSpanB.get(lastSensouSpan - 1)) > 0)
                    ichimokuStrategy.put("ichimoku-cloud", StrategyType.SELL);

                if (ichimokuStrategy.size() != 0) return ichimokuStrategy;
            }
        }
        return null;
    }

    //SUPERTREND
    public static StrategyType SUPERTREND(List<BigDecimal> high, List<BigDecimal> low, List<BigDecimal> close, int atr_period, float atr_multiplier) {
        List<BigDecimal> supertrend = Talite.SUPERTREND(high, low, close, atr_period, atr_multiplier);
        if (!Objects.isNull(supertrend)) {
            int lastST = supertrend.size() - 1;
            if (supertrend.get(lastST - 2).compareTo(close.get((close.size() - 1) - 2)) > 0 && supertrend.get(lastST - 1).compareTo(close.get((close.size() - 1) - 1)) < 0)
                return StrategyType.BUY;
            else if (supertrend.get(lastST - 2).compareTo(close.get((close.size() - 1) - 2)) < 0 && supertrend.get(lastST - 1).compareTo(close.get((close.size() - 1) - 1)) > 0)
                return StrategyType.SELL;
        }
        return null;
    }

    //RSI
    public static HashMap<String, String> RSI(List<BigDecimal> close, int rsiLength, int oversold, int overbought) {
        HashMap<String, String> hashRSI = new HashMap<>();
        List<BigDecimal> rsi = Talite.RSI(close, rsiLength);
        if (!Objects.isNull(rsi)) {
            int lastRsi = rsi.size() - 1;
            if (rsi.get(lastRsi).compareTo(BigDecimal.valueOf(oversold)) < 0) {
                hashRSI.put("strategy", StrategyType.BUY.toString());
                hashRSI.put("rsiValue", rsi.get(lastRsi).toString());
                return hashRSI;
            } else if (rsi.get(lastRsi).compareTo(BigDecimal.valueOf(overbought)) > 0) {
                hashRSI.put("strategy", StrategyType.SELL.toString());
                hashRSI.put("rsiValue", rsi.get(lastRsi).toString());
                return hashRSI;
            }
        }
        return null;
    }
}
