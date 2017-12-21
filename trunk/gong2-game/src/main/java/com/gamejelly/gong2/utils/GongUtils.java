package com.gamejelly.gong2.utils;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gamejelly.gong2.config.data.*;
import com.gamejelly.gong2.gas.service.shared.SharedDataService;
import com.gamejelly.gong2.gas.service.user.GongHuiService;
import com.gamejelly.gong2.gas.service.user.MessageService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.service.user.ItemService;
import com.gamejelly.gong2.gas.service.user.UserService;
import com.hadoit.game.common.framework.http.SimpleHttpClient;
import com.hadoit.game.common.framework.property.PropertyLoader;
import com.hadoit.game.common.lang.DataUtils;
import com.hadoit.game.common.lang.Triple;
import com.hadoit.game.engine.core.rpc.simple.context.ServerChannelContext;
import com.hadoit.game.engine.guardian.gas.GasManager;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * @author bezy
 */
public class GongUtils {
    private static final MTRandom mtRandom = new MTRandom();
    private static final int ROUND_TABLE_BASE_10K = 10000;
    public static final int ROUND_TABLE_BASE_100 = 100;
    private static final List<String> tabooNames = new ArrayList<String>();

    private static GasManager gasManager;
    private static BeanFactory beanFactory;
    private static ItemService itemService;
    private static UserService userService;
    private static MessageService messageService;
    private static GongHuiService gongHuiService;
    private static SharedDataService sharedDataService;

    static {
        SimpleHttpClient.DEFAULT.setSocketTimeout(12000);
        SimpleHttpClient.DEFAULT.setConnectTimeout(12000);
    }

    static {
        String key;
        for (Object v : TabooNameData.data.values()) {
            key = ((LMap) v).getString("key");
            if (StringUtils.isNotBlank(key)) {
                tabooNames.add(key.trim());
            }
        }
    }

    private static Map<String, String> versionUrlMap = new HashMap<String, String>();

    static {
        loadVersionUrlMap();
    }

    public static Map<String, String> loadVersionUrlMap() {
        versionUrlMap.clear();
        Properties p = PropertyLoader.loadProperties("version.properties");
        for (Map.Entry<Object, Object> s : p.entrySet()) {
            String opr = StringUtils.substringAfter((String) s.getKey(), "version.");
            String url = (String) s.getValue();
            versionUrlMap.put(opr, url);
        }
        return versionUrlMap;
    }

    public static void setBeanFactory(BeanFactory _beanFactory) {
        beanFactory = _beanFactory;
        gasManager = (GasManager) beanFactory.getBean("gasManager");
        itemService = (ItemService) beanFactory.getBean("itemService");
        userService = (UserService) beanFactory.getBean("userService");
    }

    public static GasManager getGasManager() {
        return gasManager;
    }

    public static ItemService getItemService() {
        return itemService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static String HOTFIX_PATCH = null;
    public static String HOTFIX_MD5 = null;

    public static void hotfix() {
        HOTFIX_PATCH = null;
        HOTFIX_MD5 = null;
        LoggerHelper.infoParams("hotfix read patch file: " + GongConstants.getHotfixFile());
        String patch = null;
        try {
            if (StringUtils.isNotBlank(GongConstants.getHotfixFile())) {
                patch = IOUtils.toString(new FileInputStream(GongConstants.getHotfixFile()));
            }
        } catch (Exception e) {
            GuardianLogger.info("hotfix read patch file error!");
            return;
        }
        if (StringUtils.isBlank(patch)) {
            GuardianLogger.info("hotfix read patch file empty!");
            return;
        }
        HOTFIX_PATCH = patch;
        HOTFIX_MD5 = DigestUtils.md5Hex(patch);
        GuardianLogger.info("hotfix send patch: " + HOTFIX_MD5 + "\n" + HOTFIX_PATCH);
        for (ServerChannelContext cctx : SecurityUtils.getChannelContexts()) {
            if (SecurityUtils.isAccountLogined(cctx)) {
                GongCommonNotify.notifyHotfixRun(cctx);
            }
        }
        GuardianLogger.info("hotfix send patch done!");
    }

    /**
     * 获取IP地址
     *
     * @param hostIp 主机IP地址
     * @return 网络地址
     */
    public static long getInternalIpAddress(String hostIp) {
        try {
            byte[] bytes = InetAddress.getByName(hostIp).getAddress();
            // network order
            long ip = ((bytes[3] << 24) & 0xff000000) | ((bytes[2] << 16) & 0x00ff0000) | ((bytes[1] << 8) & 0x0000ff00)
                    | (bytes[0] & 0x000000ff);
            return ip;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获得网段信息（网络地址）
     *
     * @param hostIp 主机IP地址
     * @return 所在网段信息
     */
    public static long getInternalNetAddress(String hostIp) {
        long ip = getInternalIpAddress(hostIp);

        if ((ip & 0x00000080) == 0) {
            // A类地址
            return ip & 0x000000FF;
        } else if ((ip & 0x00000040) == 0) {
            // B类地址
            return ip & 0x0000FFFF;
        } else if ((ip & 0x00000020) == 0) {
            // C类地址
            return ip & 0x00FFFFFF;
        } else if ((ip & 0x00000010) == 0) {
            // D类地址
            return ip & 0xFFFFFFFF;
        } else {
            return ip;
        }
    }

    /**
     * 获取与目标主机在同一网络的本机IP地址
     *
     * @param hostIp 目标主机IP
     * @return 与目标主机在同一网络的本机IP地址，如果不存在，则返回null
     */
    public static String getHostIpAddressInSameNetworkWith(String hostIp) {
        try {
            long targetIp = getInternalNetAddress(hostIp);
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                if (ni.isVirtual() || !ni.isUp()) {
                    continue;
                }
                for (Enumeration<InetAddress> ias = ni.getInetAddresses(); ias.hasMoreElements(); ) {
                    InetAddress ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;
                    }
                    if (getInternalNetAddress(ia.getHostAddress()) == targetIp) {
                        return ia.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getNowMillisTimeInDay(Date curDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        return cal.get(Calendar.HOUR_OF_DAY) * DateUtils.MILLIS_PER_HOUR
                + cal.get(Calendar.MINUTE) * DateUtils.MILLIS_PER_MINUTE
                + cal.get(Calendar.SECOND) * DateUtils.MILLIS_PER_SECOND + cal.get(Calendar.MILLISECOND);
    }

    public static double halfUpScaleToDouble(double val, int scale) {
        if (scale < 0) {
            return val;
        }

        BigDecimal bd = new BigDecimal(val).setScale(scale, BigDecimal.ROUND_HALF_UP);

        return bd.doubleValue();
    }

    public static long halfUpScaleToLong(double val) {
        return new Double(halfUpScaleToDouble(val, 0)).longValue();
    }

    public static int halfUpScaleToInt(double val) {
        return new Double(halfUpScaleToDouble(val, 0)).intValue();
    }

    public static float halfUpScaleToFloat(double val, int scale) {
        return new Double(halfUpScaleToDouble(val, scale)).floatValue();
    }

    // 获取不重复范围随机数 count 数量 range 范围
    public static int[] getDefferentRandomRange(int count, int range) {
        // 获取不相同的随机范围数
        int iRandomRange[] = new int[count];
        for (int i = 0; i < iRandomRange.length; i++) {
            iRandomRange[i] = GongUtils.randomIntBetweenInclusive(0, range - 1);
            for (int j = 0; j < i; j++) {
                if (iRandomRange[i] == iRandomRange[j]) {
                    // 如果重复，退回去重新生成随机数
                    i--;
                    break;
                }
            }
        }

        return iRandomRange;
    }

    /**
     * 从键值序列的参数列表生成映射表
     *
     * @param args
     * @return
     */
    public static Map<String, Object> createHashMap(Object... args) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < args.length; i += 2) {
            map.put((String) args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * 从参数列表生成列表
     *
     * @param args
     * @return
     */
    public static <T> List<T> createList(T... args) {
        List<T> list = new ArrayList<T>();
        for (T arg : args) {
            list.add(arg);
        }
        return list;
    }

    /**
     * return [0, max)
     *
     * @param max
     * @return
     */
    public static int getRandomInt(int max) {
        if (max <= 0) {
            return 0;
        }
        return mtRandom.nextInt(max);
    }

    /**
     * return [0, max)
     *
     * @param max
     * @return
     */
    public static float getRandomFloat(float max) {
        if (max <= 0.0f) {
            return 0.0f;
        }
        return mtRandom.nextFloat() * max;
    }

    /**
     * 圆桌概率(存在返回-1） -1一个都没随到
     *
     * @param probs
     * @param total
     * @return
     */
    public static int randomRoundTable(Integer[] probs, int total) {
        Assert.isTrue(ArrayUtils.isNotEmpty(probs));

        int r = getRandomInt(total);
        int p = 0;
        int idx = -1;
        for (int i = 0; i < probs.length; ++i) {
            p += probs[i];
            if (p > r) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /**
     * 圆桌概率(存在返回-1）
     *
     * @param probs
     * @param probs
     * @return
     */
    public static int randomRoundTable(Float[] probs, float total) {
        Assert.isTrue(ArrayUtils.isNotEmpty(probs));

        float r = getRandomFloat(total);
        float p = 0.0f;
        int idx = -1;
        for (int i = 0; i < probs.length; ++i) {
            p += probs[i];
            if (p > r) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /**
     * 基于10K的圆桌概率(存在返回-1)
     *
     * @param probs
     * @return
     */
    public static int randomRoundTableBase10K(Integer[] probs) {
        return randomRoundTable(probs, ROUND_TABLE_BASE_10K);
    }

    /**
     * 基于100的圆桌概率(存在返回-1)
     *
     * @param probs
     * @return
     */
    public static int randomRoundTableBase100(Integer[] probs) {
        return randomRoundTable(probs, ROUND_TABLE_BASE_100);
    }

    public static int sum(Integer... args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }

    public static float sum(Float... args) {
        float sum = 0.0f;
        for (float arg : args) {
            sum += arg;
        }
        return sum;
    }

    /**
     * 从一堆prob里面抽取一个(非圆桌概率)
     *
     * @param probs
     * @return
     */
    public static int randomProb(Integer[] probs) {
        Assert.isTrue(ArrayUtils.isNotEmpty(probs));

        int total = sum(probs);
        Assert.isTrue(total > 0);

        return randomRoundTable(probs, total);
    }

    /**
     * 从一堆prob里面抽取一个(非圆桌概率)
     *
     * @param probs
     * @return
     */
    public static int randomProb(Float[] probs) {
        Assert.isTrue(ArrayUtils.isNotEmpty(probs));

        float total = sum(probs);
        Assert.isTrue(total > 0.0f);

        return randomRoundTable(probs, total);
    }

    public static <T> T randomFromList(List<T> srcList, Integer[] probs) {
        Assert.isTrue(srcList.size() == probs.length);

        int total = sum(probs);
        if (CollectionUtils.isEmpty(srcList) || total <= 0) {
            return null;
        }
        return srcList.get(randomRoundTable(probs, total));
    }

    public static <T> T randomFromList(List<T> srcList, Float[] probs) {
        Assert.isTrue(srcList.size() == probs.length);

        float total = sum(probs);
        if (CollectionUtils.isEmpty(srcList) || total <= 0.0f) {
            return null;
        }
        return srcList.get(randomRoundTable(probs, total));
    }

    public static <T> List<T> randomsFromList(List<T> srcList, Integer[] probs, int count) {
        Assert.isTrue(srcList.size() == probs.length);

        List<T> retList = new ArrayList<T>();
        int total = sum(probs);
        if (CollectionUtils.isEmpty(srcList) || count < 1 || total <= 0) {
            return retList;
        }
        for (int i = 0; i < count; ++i) {
            retList.add(srcList.get(randomRoundTable(probs, total)));
        }
        return retList;
    }

    public static <T> List<T> randomsFromList(List<T> srcList, Float[] probs, int count) {
        Assert.isTrue(srcList.size() == probs.length);

        List<T> retList = new ArrayList<T>();
        float total = sum(probs);
        if (CollectionUtils.isEmpty(srcList) || count < 1 || total <= 0.0f) {
            return retList;
        }
        for (int i = 0; i < count; ++i) {
            retList.add(srcList.get(randomRoundTable(probs, total)));
        }
        return retList;
    }

    public static <T> List<T> randomsDistinctFromList(List<T> srcList, Integer[] probs, int count) {
        Assert.isTrue(srcList.size() == probs.length);

        List<T> retList = new ArrayList<T>();
        int total = sum(probs);
        if (CollectionUtils.isEmpty(srcList) || count < 1 || total <= 0) {
            return retList;
        }
        Integer[] retProbs = new Integer[probs.length];
        System.arraycopy(probs, 0, retProbs, 0, probs.length);
        int len = Math.min(probs.length, count);
        int idx;
        for (int i = 0; i < len; ++i) {
            idx = randomRoundTable(retProbs, total);
            retList.add(srcList.get(idx));
            total -= retProbs[idx];
            retProbs[idx] = 0;
            if (total <= 0) {
                break;
            }
        }
        return retList;
    }

    public static List<Integer> randomsDistinctIdxFromList(Integer[] probs, int count) {
        List<Integer> retList = new ArrayList<Integer>();
        int total = sum(probs);
        if (ArrayUtils.isEmpty(probs) || count < 1 || total <= 0) {
            return retList;
        }
        Integer[] retProbs = new Integer[probs.length];
        System.arraycopy(probs, 0, retProbs, 0, probs.length);
        int len = Math.min(probs.length, count);
        int idx;
        for (int i = 0; i < len; ++i) {
            idx = randomRoundTable(retProbs, total);
            retList.add(idx);
            total -= retProbs[idx];
            retProbs[idx] = 0;
            if (total <= 0) {
                break;
            }
        }
        return retList;
    }

    public static <T> List<T> randomsDistinctFromList(List<T> srcList, Float[] probs, int count) {
        Assert.isTrue(srcList.size() == probs.length);

        List<T> retList = new ArrayList<T>();
        float total = sum(probs);
        if (CollectionUtils.isEmpty(srcList) || count < 1 || total <= 0.0f) {
            return retList;
        }
        Float[] retProbs = new Float[probs.length];
        System.arraycopy(probs, 0, retProbs, 0, probs.length);
        int len = Math.min(probs.length, count);
        int idx;
        for (int i = 0; i < len; ++i) {
            idx = randomRoundTable(retProbs, total);
            retList.add(srcList.get(idx));
            total -= retProbs[idx];
            retProbs[idx] = 0.0f;
            if (total <= 0.0f) {
                break;
            }
        }
        return retList;
    }

    /**
     * @param <T>
     * @param srcList
     * @param count
     * @param distinct
     * @return
     */
    public static <T> List<T> getRandomsFromList(Collection<T> srcList, int count, boolean distinct) {
        List<T> retList = new ArrayList<T>();
        if (CollectionUtils.isEmpty(srcList) || count < 1) {
            return retList;
        }
        List<T> distList = new ArrayList<T>(srcList);
        int len = distinct ? Math.min(distList.size(), count) : count;
        int idx;
        for (int i = 0; i < len; i++) {
            idx = getRandomInt(distList.size());
            if (distinct) {
                retList.add(distList.remove(idx));
            } else {
                retList.add(distList.get(idx));
            }
        }

        return retList;
    }

    /**
     * @param prob
     * @return
     */
    public static boolean canTakePlaceBase10K(int prob) {
        if (prob <= 0) {
            return false;
        }
        if (prob >= ROUND_TABLE_BASE_10K) {
            return true;
        }
        return getRandomInt(ROUND_TABLE_BASE_10K) < prob;
    }

    /**
     * 0~1
     *
     * @param prob
     * @return
     */
    public static boolean canTakePlaceBase1(float prob) {
        return canTakePlaceBase10K((int) (ROUND_TABLE_BASE_10K * prob));
    }

    /**
     * @param v1
     * @param v2
     * @param step
     * @return
     */
    public static int randomIntBetweenInclusive(int v1, int v2, int step) {
        Assert.isTrue(step > 0);
        if (v2 > v1) {
            int stepCount = (v2 - v1) / step;
            return v1 + step * getRandomInt(stepCount + 1);
        } else {
            int stepCount = (v1 - v2) / step;
            return v2 + step * getRandomInt(stepCount + 1);
        }
    }

    /**
     * @param v1
     * @param v2
     * @return
     */
    @JsExportMethod("between")
    public static int randomIntBetweenInclusive(int v1, int v2) {
        return randomIntBetweenInclusive(v1, v2, 1);
    }

    /**
     * @param v1
     * @param v2
     * @param step
     * @return
     */
    public static float randomFloatBetweenInclusive(float v1, float v2, float step) {
        Assert.isTrue(step > 0.0f);
        if (v2 > v1) {
            int stepCount = (int) ((v2 - v1) / step);
            return v1 + step * getRandomInt(stepCount + 1);
        } else {
            int stepCount = (int) ((v1 - v2) / step);
            return v2 + step * getRandomInt(stepCount + 1);
        }
    }

    public static int reAdjustNumberInRange(int v, int min, int max) {
        int temp = v;
        if (temp > max) {
            temp = temp % max;
        }
        return Math.min(Math.max(temp, min), max);
    }

    public static int adjustNumberInRange(int v, int min, int max) {
        return Math.min(Math.max(v, min), max);
    }

    public static long adjustNumberInRange(long v, long min, long max) {
        return Math.min(Math.max(v, min), max);
    }

    public static float adjustNumberInRange(float v, float min, float max) {
        return Math.min(Math.max(v, min), max);
    }

    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isSameHourForOffset(long time1, long time2, long offsetTime) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1 - offsetTime);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time2 - offsetTime);

        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
                && cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY));
    }

    public static boolean isSameDayForOffsetHour(long time1, long time2, int hour) {
        return DateUtils.isSameDay(new Date(time1 - hour * DateUtils.MILLIS_PER_HOUR),
                new Date(time2 - hour * DateUtils.MILLIS_PER_HOUR));
    }

    public static boolean isSameDayForOffset(long time1, long time2, long offsetTime) {
        return DateUtils.isSameDay(new Date(time1 - offsetTime), new Date(time2 - offsetTime));
    }

    public static boolean isSameWeekForOffset(long time1, long time2, long offsetTime) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.setTimeInMillis(time1 - offsetTime);
        cal2.setTimeInMillis(time2 - offsetTime);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        // 分别处理同一年和跨年的问题
        if (subYear == 0 || (subYear == 1 && cal2.get(Calendar.MONTH) == 11)
                || (subYear == -1 && cal1.get(Calendar.MONTH) == 11)) {
            return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                    && cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
        }
        return false;
    }

    public static boolean isSameMonthForOffset(long time1, long time2, long offsetTime) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1 - offsetTime);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time2 - offsetTime);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
    }

    /**
     * 拿当前距离24点的时间差(秒数)
     *
     * @return
     */
    public static int getOffsetTimeSecsToZero() {
        return getOffsetTimeSecsTo(24, 0, 0);
    }

    /**
     * 获取当前距下一个整点的时间差(秒数)
     *
     * @return
     */
    public static int getOffsetTimeSecsToNextHour() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return Long.valueOf((c.getTime().getTime() - System.currentTimeMillis()) / 1000).intValue();
    }

    /**
     * 周一0点的毫秒数
     *
     * @return
     */
    public static long getMillisOfNextMondayZero() {
        Calendar c1 = Calendar.getInstance();
        int day_of_week = c1.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c1.add(Calendar.DATE, -day_of_week + 8);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        return c1.getTimeInMillis();
    }

    /**
     * 周几几点的毫秒数 weekDay 周日~周六 （0,1,2,3,4,5,6）
     *
     * @return
     */
    public static long getMillisOfNextWeekDayTime(int weekDay, int hour, int min, int sec) {
        Calendar c1 = Calendar.getInstance();
        int day_of_week = c1.get(Calendar.DAY_OF_WEEK) - 1;

        int offset = weekDay;

        // if (weekDay == 0) {
        // offset = 7;
        // }

        int curHour = c1.get(Calendar.HOUR_OF_DAY);
        int curMin = c1.get(Calendar.MINUTE);
        int curSec = c1.get(Calendar.SECOND);

        int endHour = hour;
        int endMin = min;
        int endSec = sec;

        // 超过当前时间
        if ((day_of_week == weekDay && curHour > endHour)
                || ((day_of_week == weekDay) && (curHour >= endHour && curMin >= endMin))) {
            offset = 7 + day_of_week;
        }

        int distance = -day_of_week + offset;

        if (distance < 0) {
            distance = 7 + distance;
        }

        GuardianLogger.info("getMillisOfNextWeekDayTime day distance =", distance, " weekDay", weekDay, " hour=", hour,
                " min = ", min);

        c1.add(Calendar.DATE, distance);
        c1.set(Calendar.HOUR_OF_DAY, endHour);
        c1.set(Calendar.MINUTE, endMin);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        return c1.getTimeInMillis();
    }

    /**
     * 当前距离周几几点的时间差(秒数)
     *
     * @return
     */
    public static int getOffsetTimeSecsToNextWeekTime(int weekDay, int hour, int min, int sec) {
        long nextWeekdayTime = getMillisOfNextWeekDayTime(weekDay, hour, min, sec);
        long curTime = System.currentTimeMillis();
        return (int) ((nextWeekdayTime - curTime) / 1000);
    }

    /**
     * 当前距离周一0点的时间差(秒数) weekDay 周日~周六 （0,1,2,3,4,5,6）
     *
     * @return
     */
    public static int getOffsetTimeSecsToNextMondayZero() {
        long mondayZero = getMillisOfNextMondayZero();
        long curTime = System.currentTimeMillis();
        return (int) ((mondayZero - curTime) / 1000);
    }

    public static int getOffsetTimeSecsTo(int hours, int minutes, int seconds) {
        Calendar c = Calendar.getInstance();
        // 要判断跨天的情况
        int subSecs = (hours - c.get(Calendar.HOUR_OF_DAY)) * 3600 + (minutes - c.get(Calendar.MINUTE)) * 60
                + (seconds - c.get(Calendar.SECOND));
        subSecs = subSecs <= 0 ? subSecs + 24 * 3600 : subSecs;
        return subSecs;
    }

    public static long getTimeInDay00(Date curDate) {
        return getTimeInDayAtHour(curDate, 0);
    }

    public static long getTimeInDayAtHour(Date curDate, int hour) {
        return getTimeInDayAtHourMinute(curDate, hour, 0);
    }

    public static long getTimeInDayAtHourMinute(Date curDate, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static int getOffsetTimeSecsFromTimeRange(String startTime, String endTime, String currTime) {
        long currSecs = DateUtils.getFragmentInSeconds(DataUtils.parseDateFromText(currTime, "HH:mm:ss"),
                Calendar.DAY_OF_YEAR);
        long startSecs = DateUtils.getFragmentInSeconds(DataUtils.parseDateFromText(startTime, "HH:mm:ss"),
                Calendar.DAY_OF_YEAR);
        long endSecs = DateUtils.getFragmentInSeconds(DataUtils.parseDateFromText(endTime, "HH:mm:ss"),
                Calendar.DAY_OF_YEAR);

        if (currSecs < startSecs) {
            return (int) (startSecs - currSecs);
        }
        if (currSecs >= endSecs) {
            // 下一个周期的起始时间
            return (int) (startSecs + 24 * 3600 - currSecs);
        }
        // 在事件间隔内
        return 0;
    }

    public static boolean isInOffsetTimeSecsFromTimeRange(String startTime, String endTime, String currTime) {
        long currSecs = DateUtils.getFragmentInSeconds(DataUtils.parseDateFromText(currTime, "HH:mm:ss"),
                Calendar.DAY_OF_YEAR);
        long startSecs = DateUtils.getFragmentInSeconds(DataUtils.parseDateFromText(startTime, "HH:mm:ss"),
                Calendar.DAY_OF_YEAR);
        long endSecs = DateUtils.getFragmentInSeconds(DataUtils.parseDateFromText(endTime, "HH:mm:ss"),
                Calendar.DAY_OF_YEAR);
        if (currSecs >= startSecs && currSecs <= endSecs) {
            return true;
        }
        return false;
    }

    public static int calcSubDay(long startTime, long endTime) {
        long cd1 = getTimeInDay00(new Date(startTime));
        long cd2 = getTimeInDay00(new Date(endTime));
        int ret = (int) ((cd2 - cd1) / DateUtils.MILLIS_PER_DAY);
        return ret;
    }

    public static String getAvatarEntityBalanceId(long dbId) {
        return "AvatarEntity" + "_" + dbId;
    }


    public static <T> void addUpValuesToMap(Map<T, Integer> vars, T key, int incrV) {
        if (!vars.containsKey(key)) {
            vars.put(key, 0);
        }
        vars.put(key, vars.get(key) + incrV);
    }

    public static <T> void addUpLongValuesToMap(Map<T, Long> vars, T key, long incrV) {
        if (!vars.containsKey(key)) {
            vars.put(key, 0l);
        }
        vars.put(key, vars.get(key) + incrV);
    }

    public static <T> void addUpValuesToMap1(Map<T, Number> vars, T key, Number incrV) {
        if (!vars.containsKey(key)) {
            vars.put(key, 0);
        }
        Number v = vars.get(key);
        if (v instanceof Long) {

        }
        // vars.put(key, vars.get(key) + incrV);
    }


    public static String decompOpr(String name) {
        return StringUtils.substringAfterLast(name, "@");
    }

    /**
     * 去掉字符串中的特殊的Unicode字符
     */
    public static String trimUnicode(String text) {
        if (StringUtils.isEmpty(text)) {
            return StringUtils.EMPTY;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!Character.isHighSurrogate(c) && !Character.isLowSurrogate(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 脏字屏蔽
     *
     * @param name
     * @return
     */
    public static boolean checkTaboo(String name) {
        if (StringUtils.isBlank(name)) {
            return false;
        }
        for (String tabooName : tabooNames) {
            if (name.toLowerCase().contains(tabooName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkRoleName(String roleName) {
        if (roleName.length() < SysConstData.data.getInt("ROLE_NAME_MIN_LEN")
                || roleName.length() > SysConstData.data.getInt("ROLE_NAME_MAX_LEN") || GongUtils.checkTaboo(roleName)
                || !GongUtils.isChinese(roleName)) {
            return false;
        }

        return true;
    }

    /**
     * 计算服务器等级和其他限制条件限制的等级
     */
    public static Triple<Integer, Integer, Integer> calcServerLevelOtherLv(long currTime, String strType) {
        int offsetDay = GongUtils.calcSubDay(GongConstants.getOpenServerTime(), currTime);

        int serverLevel = 0;
        int serverTypeLv = 0;
        LList lstTimeLimit = TimelimitData.data.valuesToList();
        for (Object object : lstTimeLimit) {
            LMap o = (LMap) object;
            if (offsetDay >= o.getInt("time", 0)) {
                serverLevel = o.getInt("lv", 0);
                serverTypeLv = o.getInt(strType, 0);
            } else {
                break;
            }
        }

        return Triple.newInstance(offsetDay, serverLevel, serverTypeLv);
    }

    public static long getExpAfterPunish(int lv, long val) {
        Triple<Integer, Integer, Integer> params = GongUtils.calcServerLevelOtherLv(System.currentTimeMillis(), "lv");
        int serverLevel = params.getSecond();
        // 玩家当前等级和服务器等级差
        int differValue = lv - serverLevel;
        if (differValue > 0) {

            LMap expPunishData = GongUtils.getExpPunishData(differValue);
            if (expPunishData != null) {
                float punishExp = expPunishData.getFloat("punishExp", 0.0f);
                val = (long) Math.floor(val * (1 + punishExp));
            }

        }
        return val;
    }

    // 经验惩罚表
    private static Map<Integer, LMap> expPunishData;

    public static LMap getExpPunishData(int lv) {
        if (expPunishData == null) {
            expPunishData = new HashMap<Integer, LMap>();
            for (Object key : ExpPunishData.data.keySet()) {
                LMap m = ExpPunishData.data.getMap(key);
                LList kl = (LList) key;
                for (int i = kl.getInt(0); i <= kl.getInt(1); ++i) {
                    expPunishData.put(i, m);
                }
            }
        }
        LMap ret = expPunishData.get(lv);
        if (ret == null) {
            return expPunishData.get(-1);
        }
        return ret;
    }

    private static Map<Integer, List<Integer>> sceneToGuankaMap;

    @SuppressWarnings("unchecked")
    public static List<Integer> getGkListByScene(int sceneId) {
        if (sceneToGuankaMap == null) {
            sceneToGuankaMap = new HashMap<Integer, List<Integer>>();
            for (Object val : GuankaBaseData.data.values()) {
                LMap lval = (LMap) val;
                int asId = lval.getInt("scene", 0);
                if (!sceneToGuankaMap.containsKey(asId)) {
                    sceneToGuankaMap.put(asId, new ArrayList<Integer>());
                }

                sceneToGuankaMap.get(asId).add(lval.getInt("id", 0));
            }
        }
        List<Integer> ret = sceneToGuankaMap.get(sceneId);
        return (List<Integer>) (ret != null ? ret : Collections.emptyList());
    }

    public static boolean isSpecialChar(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    private static List<String> inFightPropList;

    public static List<String> getInFightPropList() {
        if (inFightPropList == null) {
            inFightPropList = new ArrayList<String>();
            for (Object o : PropData.data.values()) {
                LMap lm = (LMap) o;
                if (lm.getInt("inFight", 0) == 1) {
                    inFightPropList.add(lm.getString("funName"));
                }
            }
        }
        return inFightPropList;
    }

    // 1 4
    // 2 5
    // 3 6
    private static List<List<Integer>> rowConstants = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
    private static List<List<Integer>> colConstants = Arrays.asList(Arrays.asList(1, 4), Arrays.asList(2, 5),
            Arrays.asList(3, 6));

    public static int getRow(int pos) {
        for (int i = 0; i < rowConstants.size(); i++) {
            List<Integer> r = rowConstants.get(i);
            if (r.contains(pos)) {
                return i + 1;
            }
        }
        return -1;
    }

    public static int getCol(int pos) {
        for (int i = 0; i < colConstants.size(); i++) {
            List<Integer> r = colConstants.get(i);
            if (r.contains(pos)) {
                return i + 1;
            }
        }
        return -1;
    }


    private static Map<Integer, List<Integer>> giftPdAwardMap;

    public static List<Integer> getGiftPdAwardList(int giftId) {
        if (giftPdAwardMap == null) {
            giftPdAwardMap = new HashMap<Integer, List<Integer>>();
            for (Object m : GiftData.data.values()) {
                LMap lm = (LMap) m;
                LList bdpdAward = lm.getList("bdpdAward");
                if (CollectionUtils.isNotEmpty(bdpdAward)) {
                    List<Integer> bdpdAwards = new ArrayList<Integer>();
                    for (int i = 0; i < bdpdAward.size(); i++) {
                        LList bdpdGrps = ItemGroupData.data.getMap(bdpdAward.getInt(i, 0)).getList("item");
                        for (int j = 0; j < bdpdGrps.size(); j++) {
                            bdpdAwards.add(bdpdGrps.getInt(j, 0));
                        }
                    }
                    giftPdAwardMap.put(lm.getInt("id", 0), bdpdAwards);
                }
            }
        }
        return giftPdAwardMap.get(giftId);
    }

    public static MessageService getMessageService() {
        return messageService;
    }

    public static GongHuiService getGongHuiService() {
        return gongHuiService;
    }

    public static SharedDataService getSharedDataService() {
        return sharedDataService;
    }

    /**
     * @param a
     * @param b
     * @return 判断两个数组是否不为空并且size 相同
     */
    public static boolean twoListNotEmptyAndHaveEqualSize(LList a, LList b) {
        if (a != null && b != null && a.size() == b.size()) {
            return true;
        }
        GuardianLogger.error("twoListNotEmptyAndHaveEqualSize error");
        return false;
    }


    @SuppressWarnings("unchecked")
    public static <T> T getVipValue(int vipLv, String prop, T defaultValue) {
        LMap vipData = VipData.data.getMap(vipLv);
        if (vipData == null)
            return defaultValue;
        T vipValue = (T) vipData.get(prop);
        if (vipValue == null)
            return (T) getVipValue(vipLv - 1, prop, defaultValue);
        else
            return vipValue;
    }

    /**
     * @return 返回掉落库需要的数据格式
     */
    public static Object[] getDropStoreNeedDataFormat() {
        Object[] ret = new Object[]{new Object(), new Object()};
        Map<Integer, Integer> dropItemMap = new HashMap<Integer, Integer>();
        List<Integer> servantTempIdList = new ArrayList<Integer>();
        ret[0] = dropItemMap;
        ret[1] = servantTempIdList;
        return ret;
    }

}