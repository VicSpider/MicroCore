package Utils;

import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期相关的工具类
 * Created by Victor on 2018/10/29.
 */
public class DateCompute {  //Todo 什么情况下class可以用private修饰
    private static Calendar cal = Calendar.getInstance();

    /**
     * 获取两个日期之间的间隔天数
     */
    public static int getIntervalDays(Date startDate, Date endDate) {

        if (null == startDate || null == endDate) {

            return -1;

        }

        long intervalMilli = endDate.getTime() - startDate.getTime();

        return (int) (intervalMilli / (24 * 60 * 60 * 1000));

    }

    /**
     * 多种形式输入时间，返回Date
     * 合法的形式有
     * 2018-1-2
     * 2018/1/2
     * 2018-1
     * 1/2018
     * 1/2/49
     *
     * @param s
     * @return
     */
    @Nullable
    public static Date dateForm(String s){
        s = s.split(" ")[0];
        String splitsym = "";
        if(s.contains("/")){
            splitsym = "/";
        }
        else if(s.contains("-")){
            splitsym = "-";
        }
        int type;
        if(splitsym.length() > 0)   type = s.split(splitsym).length;
        else if(s.length() > 6)     type = 3;
        else type = 2;
        try {
            //return new SimpleDateFormat("yyyy"+splitsym+"MM"+splitsym+"dd").parse(s);
            if(splitsym.equals("/")){
                String[] dmy = s.split(splitsym);
                if(type == 3){
                    if(dmy[2].length()==2){
                        dmy[2] = "20"+dmy[2];
                    }
                    return DateCompute.getDate(Integer.parseInt(dmy[2]),Integer.parseInt(dmy[0]),Integer.parseInt(dmy[1]));
                }
                else{
                    if(dmy[1].length()==2){
                        dmy[1] = "20"+dmy[1];
                    }
                    return DateCompute.getDate(Integer.parseInt(dmy[1]),Integer.parseInt(dmy[0]),1);
                }
            }
            else {
                if (s.split(splitsym)[0].length() == 2) s = "20" + s;
                if (type == 3) return new SimpleDateFormat("yyyy" + splitsym + "MM" + splitsym + "dd").parse(s);
                if (type == 2) return new SimpleDateFormat("yyyy" + splitsym + "MM").parse(s);
            }
        }catch (ParseException e){
            e.getErrorOffset();
        }
        return null;
    }
    /**
     * 输入Date，按xxxx-xx-xx形式输出时间
     * @param d
     * @return
     */
    public static String reDateForm(Date d){
        //String[] str = d.toString()
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(d);

    }

    /**
     * 按年月日获取Date
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(int year, int month, int day){
        cal.set(year, month-1, day);
        return cal.getTime();
    }


    /**
     * 获取这个date中的“日”
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date){
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取这个date中的“月”
     * @param date
     * @return
     */
    public static int getMonth(Date date){
        cal.setTime(date);
        return cal.get(Calendar.MONTH)+1;
    }

    /**
     * 获取这个date中的“年”
     * @param date
     * @return
     */
    public static int getYear(Date date){
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }


    /**
     * 使该date增加天数
     */
    public static Date addDate(Date date, int days){
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    /**
     * 使该date增加月份
     */
    public static Date addMonth(Date date, int months){
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * Todo 未考虑改cycle的情况
     * 判断原交易日在哪个账单周期
     * @param cycleDay  账单日cycle
     * @param transDate 原交易日
     * @param recordDate 入账日
     * @return
     * -1: 原交易日大于入账日
     * 0：原交易日在当期
     * 1：原交易日在上期
     * 2：原交易日在两周期前
     */

    public static int judgeCycle(int cycleDay, Date transDate, Date recordDate){
        if(DateCompute.getIntervalDays(transDate, recordDate) < 0)  return -1;
        Date lastCycleDay = DateCompute.getDate(DateCompute.getYear(recordDate),DateCompute.getMonth(recordDate),cycleDay);
        if(DateCompute.getIntervalDays(lastCycleDay, recordDate) <= 0){
            lastCycleDay = DateCompute.addMonth(lastCycleDay, -1);
        }
        if(DateCompute.getIntervalDays(lastCycleDay, transDate) > 0 )   return 0;
        Date theCycleBeforeLastCycleDay = DateCompute.addMonth(lastCycleDay, -1);
        if(DateCompute.getIntervalDays(theCycleBeforeLastCycleDay, transDate) > 0 ) return 1;
        else return 2;
    }
}
