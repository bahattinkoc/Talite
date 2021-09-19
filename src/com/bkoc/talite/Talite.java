package com.bkoc.talite;

import com.sun.xml.internal.ws.resources.UtilMessages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Talite {

    public static enum MA_TYPE{
        SMA,
        EMA,
        RMA
    };

    /**
     * SMA Indicator
     * */
    public static List<BigDecimal> SMA(List<BigDecimal> closes, int timeperiod){
        //int index = 0;
        if(closes.size() > timeperiod) {
            List<BigDecimal> sma = new LinkedList<>();
            for (int i = 0; i < closes.size(); i++) {
                if (i >= timeperiod - 1) {
                    BigDecimal sum = closes.get(i);
                    for (int j = 1; j < timeperiod; j++)
                        sum = sum.add(closes.get(i - j));
                    sma.add(sum.divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros());
                } else {
                    sma.add(BigDecimal.valueOf(0));
                }
            }
            return sma;
        }
        else return null;
    }

    /**
     * EMA Indicator
     * */
    public static List<BigDecimal> EMA(List<BigDecimal> closes, int timeperiod){
        if(closes.size() > timeperiod) {
            BigDecimal alpha = BigDecimal.valueOf(2).divide(BigDecimal.valueOf(timeperiod + 1), 10, RoundingMode.HALF_UP).stripTrailingZeros();
            List<BigDecimal> ema = new LinkedList<>();
            emaCalculate(closes, ema, closes.size() - 1, alpha);
            return ema;
        }
        else return null;
    }

    private static BigDecimal emaCalculate(List<BigDecimal> closes, List<BigDecimal> ema, int index, BigDecimal alpha){
        if(index == 0) {
            ema.add(closes.get(index).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros());
            return closes.get(index).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        }
        BigDecimal emaValue = closes.get(index).multiply(alpha).add(emaCalculate(closes, ema, index-1, alpha).multiply(BigDecimal.valueOf(1).subtract(alpha)));
        ema.add(emaValue.setScale(10, RoundingMode.HALF_UP).stripTrailingZeros());
        return emaValue;
    }

    /**
     * RMA Indicator
     * */
    public static List<BigDecimal> RMA(List<BigDecimal> closes, int timeperiod){
        if (closes.size() > timeperiod) {
            BigDecimal alpha = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
            List<BigDecimal> rma = new LinkedList<>();
            rmaCalculate(closes, rma, closes.size() - 1, alpha);
            return rma;
        }
        else return null;
    }

    private static BigDecimal rmaCalculate(List<BigDecimal> closes, List<BigDecimal> rma, int index, BigDecimal alpha){
        if(index == 0) {
            rma.add(closes.get(index).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros());
            return closes.get(index).setScale(10, RoundingMode.HALF_UP).stripTrailingZeros();
        }
        BigDecimal rmaValue = closes.get(index).multiply(alpha).add(rmaCalculate(closes, rma, index-1, alpha).multiply(BigDecimal.valueOf(1).subtract(alpha)));
        rma.add(rmaValue.setScale(10, RoundingMode.HALF_UP).stripTrailingZeros());
        return rmaValue;
    }

    /**
     * WMA Indicator
     * */
    public static List<BigDecimal> WMA(List<BigDecimal> close, int timeperiod){
        List<BigDecimal> wma = new LinkedList<>();

        for (int i = 0; i < timeperiod; i++)
            wma.add(BigDecimal.valueOf(0));

        for (int i = timeperiod; i < close.size() + 1; i++){
            int mult = timeperiod;
            BigDecimal sum = BigDecimal.valueOf(0);
            for (int j = i - 1; j > i - timeperiod - 1; j--)
                sum = sum.add(close.get(j).multiply(BigDecimal.valueOf(mult--)));

            mult = timeperiod * (timeperiod + 1) / 2;
            sum = sum.divide(BigDecimal.valueOf(mult), 10, RoundingMode.HALF_UP).stripTrailingZeros();
            wma.add(sum);
        }
        return wma;
    }

    /**
     * RSI Indicator
     * */
    public static List<BigDecimal> RSI(List<BigDecimal> closes, int timeperiod){
        if (closes.size() > timeperiod) {
            List<BigDecimal> rsi = new LinkedList<>();
            BigDecimal poz = BigDecimal.valueOf(0);
            BigDecimal neg = BigDecimal.valueOf(0);

            for (int i = 0; i < timeperiod; i++)
                rsi.add(BigDecimal.valueOf(0));

            for (int j = timeperiod; j > 0; j--) {
                if (closes.get(j).compareTo(closes.get(j - 1)) > 0)
                    poz = poz.add(closes.get(j).subtract(closes.get(j - 1)));
                else if (closes.get(j).compareTo(closes.get(j - 1)) < 0)
                    neg = neg.add(closes.get(j - 1).subtract(closes.get(j)));
            }
            poz = poz.divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
            neg = neg.divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
            if (neg.compareTo(BigDecimal.valueOf(0)) == 0)
                rsi.add(BigDecimal.valueOf(100));
            else if (poz.divide(neg, 10, RoundingMode.HALF_UP).compareTo(BigDecimal.valueOf(-1)) == 0)
                rsi.add(BigDecimal.valueOf(50));
            else
                rsi.add(BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(100).divide(BigDecimal.valueOf(1).add(poz.divide(neg, 10, RoundingMode.HALF_UP)), 10, RoundingMode.HALF_UP)).stripTrailingZeros());

            for (int i = timeperiod + 1; i < closes.size(); i++) {
                if (closes.get(i).compareTo(closes.get(i - 1)) > 0) {
                    poz = poz.multiply(BigDecimal.valueOf(timeperiod - 1)).add(closes.get(i).subtract(closes.get(i - 1))).divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
                    neg = neg.multiply(BigDecimal.valueOf(timeperiod - 1)).add(BigDecimal.valueOf(0)).divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
                } else if (closes.get(i).compareTo(closes.get(i - 1)) < 0) {
                    neg = neg.multiply(BigDecimal.valueOf(timeperiod - 1)).add(closes.get(i - 1).subtract(closes.get(i))).divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
                    poz = poz.multiply(BigDecimal.valueOf(timeperiod - 1)).add(BigDecimal.valueOf(0)).divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
                } else {
                    neg = neg.multiply(BigDecimal.valueOf(timeperiod - 1)).add(BigDecimal.valueOf(0)).divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
                    poz = poz.multiply(BigDecimal.valueOf(timeperiod - 1)).add(BigDecimal.valueOf(0)).divide(BigDecimal.valueOf(timeperiod), 10, RoundingMode.HALF_UP).stripTrailingZeros();
                }
                if (neg.compareTo(BigDecimal.valueOf(0)) == 0)
                    rsi.add(BigDecimal.valueOf(100));
                else
                    rsi.add(BigDecimal.valueOf(100).subtract(BigDecimal.valueOf(100).divide(BigDecimal.valueOf(1).add(poz.divide(neg, 10, RoundingMode.HALF_UP)), 10, RoundingMode.HALF_UP)).stripTrailingZeros());
            }
            return rsi;
        }
        else return null;
    }

    /**
     * STOCH Indicator
     * */
    public static HashMap<String, List<BigDecimal>> STOCH(List<BigDecimal> close, List<BigDecimal> high, List<BigDecimal> low, int periodK, int smoothK, int smoothD){
        if (close.size() > periodK) {
            HashMap<String, List<BigDecimal>> result = new HashMap<>();
            List<BigDecimal> stoch = new LinkedList<>();
            List<BigDecimal> stochSMA_K;
            List<BigDecimal> stochSMA_D;

            for (int i = 0; i < periodK; i++)
                stoch.add(BigDecimal.valueOf(0));

            for (int i = periodK; i < close.size(); i++) {
                BigDecimal lowest = BigDecimal.valueOf(Double.MAX_VALUE), highest = BigDecimal.valueOf(0);
                //lowest ve highest ı bul
                for (int j = i; j > i - periodK; j--) {
                    if (lowest.compareTo(low.get(j)) > 0)
                        lowest = low.get(j);
                    if (highest.compareTo(high.get(j)) < 0)
                        highest = high.get(j);
                }
                if (highest.subtract(lowest).compareTo(BigDecimal.valueOf(0)) == 0)
                    stoch.add(BigDecimal.valueOf(0));
                else
                    stoch.add(BigDecimal.valueOf(100).multiply(close.get(i).subtract(lowest)).divide(highest.subtract(lowest), 10, RoundingMode.HALF_UP).stripTrailingZeros());
            }
            //stoch oluştu
            stochSMA_K = SMA(stoch, smoothK);
            stochSMA_D = SMA(stochSMA_K, smoothD);

            result.put("k", stochSMA_K);
            result.put("d", stochSMA_D);

            return result;
        }else return null;
    }

    /**
     * STOCHRSI Indicator
     * */
    public static HashMap<String, List<BigDecimal>> STOCHRSI(List<BigDecimal> close, int smoothK, int smoothD, int lengthRSI, int lengthStoch){
        List<BigDecimal> rsi = RSI(close, lengthRSI);
        if (!Objects.isNull(rsi))
            return STOCH(rsi, rsi, rsi, lengthStoch, smoothK, smoothD);
        else return null;
    }

    /**
     * BOLLINGER BAND Indicator
     * */
    public static HashMap<String, List<BigDecimal>> BBANDS(List<BigDecimal> close, int length, double mult){
        if (close.size() > length) {
            HashMap<String, List<BigDecimal>> result = new HashMap<>();
            List<BigDecimal> middle = SMA(close, length);
            List<BigDecimal> upper = new LinkedList<>();
            List<BigDecimal> lower = new LinkedList<>();

            for (int i = 0; i < length - 1; i++) {
                upper.add(BigDecimal.valueOf(0));
                lower.add(BigDecimal.valueOf(0));
            }

            for (int i = length - 1; i < close.size(); i++) {
                BigDecimal sum = BigDecimal.valueOf(0);
                //kareler toplamı
                for (int j = i; j > i - length; j--)
                    sum = sum.add(close.get(j).subtract(middle.get(i).setScale(10, RoundingMode.HALF_UP)).pow(2));

                sum = sum.divide(BigDecimal.valueOf(length), 10, RoundingMode.HALF_UP);
                sum = BigDecimal.valueOf(Math.sqrt(sum.doubleValue())).multiply(BigDecimal.valueOf(mult)).setScale(10, RoundingMode.HALF_UP);

                upper.add(middle.get(i).add(sum.setScale(10, RoundingMode.HALF_UP)).setScale(10, RoundingMode.HALF_UP));
                lower.add(middle.get(i).subtract(sum).setScale(10, RoundingMode.HALF_UP));
            }
            result.put("upper", upper);
            result.put("lower", lower);
            result.put("middle", middle);
            return result;
        }else return null;
    }

    /**
     * MACD Indicator (SMA ve EMA kullanılıyor)
     * */
    public static HashMap<String, List<BigDecimal>> MACD(List<BigDecimal> close, int fastLength, int slowLength, int signalSmoothingLength, MA_TYPE oscillatorMAType, MA_TYPE signalLineMAType){
        if (close.size() < fastLength || close.size() < slowLength || close.size() < signalSmoothingLength)
            return null;

        List<BigDecimal> fast_ma = oscillatorMAType == MA_TYPE.SMA ? SMA(close, fastLength) : EMA(close, fastLength);
        List<BigDecimal> slow_ma = oscillatorMAType == MA_TYPE.SMA ? SMA(close, slowLength) : EMA(close, slowLength);
        List<BigDecimal> macd = new LinkedList<>();

        for (int i = 0; i < fast_ma.size(); i++)
            macd.add(fast_ma.get(i).subtract(slow_ma.get(i)).stripTrailingZeros());

        List<BigDecimal> signal = signalLineMAType == MA_TYPE.SMA ? SMA(macd, signalSmoothingLength) : EMA(macd, signalSmoothingLength);

        List<BigDecimal> histogram = new LinkedList<>();
        for (int i = 0; i < macd.size(); i++)
            histogram.add(macd.get(i).subtract(signal.get(i)).stripTrailingZeros());

        HashMap<String, List<BigDecimal>> hash = new HashMap<>();
        hash.put("macd", macd);
        hash.put("signal", signal);
        hash.put("hist", histogram);
        return hash;
    }

    /**
     * MavilimW Indicator (WMA kullanılıyor)
     * Kıvanç Özbilgiç
     * */
    public static List<BigDecimal> MavilimW(List<BigDecimal> close, int firstMALength, int secondMALength){
        int tmal = firstMALength + secondMALength;
        int Fmal = secondMALength + tmal;
        int Ftmal = Fmal + tmal;
        int Smal = Ftmal + Fmal;

        List<BigDecimal> M1 = WMA(close, firstMALength);
        M1 = WMA(M1, secondMALength);
        M1 = WMA(M1, tmal);
        M1 = WMA(M1, Fmal);
        M1 = WMA(M1, Ftmal);
        M1 = WMA(M1, Smal);

        return M1;
    }

    /**
     * Commodity Channel Index (CCI)
     * */
    public static List<BigDecimal> CCI(List<BigDecimal> high, List<BigDecimal> low, List<BigDecimal> close, int length){
        List<BigDecimal> ma;
        List<BigDecimal> cci = new LinkedList<>();
        List<BigDecimal> src = new LinkedList<>();
        for (int i = 0; i < high.size(); i++) // hlc3 doğru
            src.add(high.get(i).add(low.get(i).add(close.get(i))).divide(BigDecimal.valueOf(3), 8, RoundingMode.HALF_UP));

        ma = SMA(src, length);

        for (int i = 0; i < length - 1; i++)
            cci.add(BigDecimal.valueOf(0));

        for (int i = length - 1; i < high.size(); i++){
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i; j > i - length; j--) // indexlerde doğru
                sum = sum.add(BigDecimal.valueOf(Math.abs(src.get(j).subtract(ma.get(i)).floatValue())));

            sum = sum.divide(BigDecimal.valueOf(length), 8, RoundingMode.HALF_UP);
            BigDecimal srcminusma = src.get(i).subtract(ma.get(i));
            BigDecimal devmultnumber = sum.multiply(BigDecimal.valueOf(0.015));
            cci.add(srcminusma.divide(devmultnumber, 8, RoundingMode.HALF_UP));
        }

        return cci;
    }

    /**
     * Ichimoku Cloud Indicator
     * */
    public static HashMap<String, List<BigDecimal>> ICHIMOKU(List<BigDecimal> high, List<BigDecimal> low, List<BigDecimal> close, int conversionLineLength, int baseLineLength, int laggingSpan2Periods, int displacement){
        BigDecimal max, min;
        HashMap<String, List<BigDecimal>> hash = new HashMap<>();
        List<BigDecimal> laggingSpan = new LinkedList<>(close);
        List<BigDecimal> conversionLine = new LinkedList<>(); // Dönüş çizgisi 9 (Tenkan Sen)
        List<BigDecimal> baseLine = new LinkedList<>(); // Temel çizgi 26 (Kijun Sen)
        List<BigDecimal> leadLine1 = new LinkedList<>(); // Öncü A (Senkou Span A) ((Tenkan Sen + Kijun Sen) / 2)
        List<BigDecimal> leadLine2 = new LinkedList<>(); // Öncü B (Senkou Span B)

        for (int i = 0; i < displacement - 1; i++){
            conversionLine.add(BigDecimal.ZERO);
            baseLine.add(BigDecimal.ZERO);
            laggingSpan.add(BigDecimal.ZERO);
            laggingSpan.add(BigDecimal.ZERO);
            leadLine1.add(BigDecimal.ZERO);
            leadLine1.add(BigDecimal.ZERO);
            leadLine2.add(BigDecimal.ZERO);
            leadLine2.add(BigDecimal.ZERO);
        }

        int bigger = (conversionLineLength > baseLineLength) ? conversionLineLength : baseLineLength;
        for (int i = 0; i < bigger - 1; i++)
            leadLine1.add(BigDecimal.ZERO);

        for (int i = 0; i < laggingSpan2Periods - 1; i++)
            leadLine2.add(BigDecimal.ZERO);

        for (int i = 0; i < high.size(); i++){
            int k = 0;
            if (i >= conversionLineLength - 1){
                k++;
                max = BigDecimal.ZERO;
                min = BigDecimal.valueOf(Integer.MAX_VALUE);
                for (int j = i; j > i - conversionLineLength; j--){
                    max = (high.get(j).compareTo(max) > 0) ? high.get(j) : max;
                    min = (low.get(j).compareTo(min) < 0) ? low.get(j) : min;
                }
                conversionLine.add(max.add(min).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP));
            } else conversionLine.add(BigDecimal.ZERO);

            if (i >= baseLineLength - 1){
                k++;
                max = BigDecimal.ZERO;
                min = BigDecimal.valueOf(Integer.MAX_VALUE);

                for (int j = i; j > i - baseLineLength; j--){
                    max = (high.get(j).compareTo(max) > 0) ? high.get(j) : max;
                    min = (low.get(j).compareTo(min) < 0) ? low.get(j) : min;
                }
                baseLine.add(max.add(min).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP));
            } else baseLine.add(BigDecimal.ZERO);

            if (i >= laggingSpan2Periods - 1){
                max = BigDecimal.ZERO;
                min = BigDecimal.valueOf(Integer.MAX_VALUE);

                for (int j = i; j > i - laggingSpan2Periods; j--){
                    max = (high.get(j).compareTo(max) > 0) ? high.get(j) : max;
                    min = (low.get(j).compareTo(min) < 0) ? low.get(j) : min;
                }
                leadLine2.add(max.add(min).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP));
            }

            if (k == 2)
                leadLine1.add(conversionLine.get(displacement - 1 + i).add(baseLine.get(displacement - 1 + i)).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP));
        }

        for (int i = 0; i < displacement - 1; i++){
            conversionLine.add(BigDecimal.ZERO);
            baseLine.add(BigDecimal.ZERO);
        }

        hash.put("conversionLine", conversionLine);
        hash.put("baseLine", baseLine);
        hash.put("laggingSpan", laggingSpan);
        hash.put("leadLine1", leadLine1);
        hash.put("leadLine2", leadLine2);

        return hash;
    }

    /**
     * ATR - Average True Range
     * */
    public static List<BigDecimal> ATR(List<BigDecimal> high, List<BigDecimal> low, List<BigDecimal> close, int period, MA_TYPE ma_type){
        List<BigDecimal> tr = new LinkedList<>();
        tr.add(BigDecimal.ZERO);

        // TR Calculate
        for (int i = 1; i < high.size(); i++){
            BigDecimal a = high.get(i).subtract(low.get(i));
            BigDecimal b = BigDecimal.valueOf(Math.abs(high.get(i).subtract(close.get(i-1)).floatValue()));
            BigDecimal c = BigDecimal.valueOf(Math.abs(low.get(i).subtract(close.get(i-1)).floatValue()));
            BigDecimal temp = (a.compareTo(b) > 0) ? ((a.compareTo(c) > 0) ? a : c) : ((b.compareTo(c) > 0) ? b : c);
            tr.add(temp);
        }

        if (ma_type == MA_TYPE.SMA)
            return SMA(tr, period);
        else if (ma_type == MA_TYPE.EMA)
            return EMA(tr, period);
        else return RMA(tr, period);
    }

    /**
     * ATR - Average True Range
     * */
    public static List<BigDecimal> SUPERTREND(List<BigDecimal> high, List<BigDecimal> low, List<BigDecimal> close, int atr_period, float atr_multiplier){
        List<BigDecimal> atr = ATR(high, low, close, atr_period, MA_TYPE.RMA);
        List<BigDecimal> hl2 = new LinkedList<>();

        for (int i = 0; i < high.size(); i++) // hlc3 doğru
            hl2.add(high.get(i).add(low.get(i)).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP));

        BigDecimal previous_final_upperband = BigDecimal.ZERO;
        BigDecimal previous_final_lowerband = BigDecimal.ZERO;
        BigDecimal previous_close = BigDecimal.ZERO;
        BigDecimal previous_supertrend = BigDecimal.ZERO;
        BigDecimal supertrendc = BigDecimal.ZERO;
        BigDecimal final_upperband = BigDecimal.ZERO;
        BigDecimal final_lowerband = BigDecimal.ZERO;
        List<BigDecimal> supertrend = new LinkedList<>();

        for (int i = 0; i < close.size(); i++){
            BigDecimal highc = high.get(i);
            BigDecimal lowc = low.get(i);
            BigDecimal atrc = atr.get(i);
            BigDecimal closec = hl2.get(i);

            BigDecimal basic_upperband = highc.add(lowc).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP).add(atrc.multiply(BigDecimal.valueOf(atr_multiplier)));
            BigDecimal basic_lowerband = highc.add(lowc).divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP).subtract(atrc.multiply(BigDecimal.valueOf(atr_multiplier)));

            if (basic_upperband.compareTo(previous_final_upperband) < 0 || previous_close.compareTo(previous_final_upperband) > 0)
                final_upperband = basic_upperband;
            else final_upperband = previous_final_upperband;

            if (basic_lowerband.compareTo(previous_final_lowerband) > 0 || previous_close.compareTo(previous_final_lowerband) < 0)
                final_lowerband = basic_lowerband;
            else final_lowerband = previous_final_lowerband;

            if (previous_supertrend.compareTo(previous_final_upperband) == 0 && closec.compareTo(final_upperband) <= 0)
                supertrendc = final_upperband;
            else
                if (previous_supertrend.compareTo(previous_final_upperband) == 0 && closec.compareTo(final_upperband) >= 0)
                    supertrendc = final_lowerband;
                else
                    if (previous_supertrend.compareTo(previous_final_lowerband) == 0 && closec.compareTo(final_lowerband) >= 0)
                        supertrendc = final_lowerband;
                    else if (previous_supertrend.compareTo(previous_final_lowerband) == 0 && closec.compareTo(final_lowerband) <= 0)
                        supertrendc = final_upperband;

            supertrend.add(supertrendc);
            previous_close = closec;
            previous_final_upperband = final_upperband;
            previous_final_lowerband = final_lowerband;
            previous_supertrend = supertrendc;
        }

        return supertrend;
    }
}
