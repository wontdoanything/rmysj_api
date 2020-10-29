package org.rmysj.api.commons.util;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * CsvUtil
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/8/27
 */
public class CsvUtil {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUtil.class);

    /**
     * 解析csv文件并转成bean
     * @param file csv文件
     * @param clazz 类
     * @param <T> 泛型
     * @return 泛型bean集合
     */
    public static <T> List<T> getCsvData(File file, Class<T> clazz) {
        InputStreamReader reader = null;
        BOMInputStream bomInputStream = null;
        try {
            bomInputStream = new BOMInputStream(new FileInputStream(file));

            reader = new InputStreamReader(bomInputStream,"gbk");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            LOGGER.error("文件不存在",e.getCause());
        }
        //列位置映射策略，他没有header的概念，所以会输出取所有行。在columnMapping数组中指定bean的属性，第一个值对应csv的第一列，第二个值对应csv的第二列……
        //可以使用SkipLines方法
        MappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        //基于列名的映射策略，读取csv文件的第一行作为header，比如header1，header2，header3然后调用bean的setHeader1方法，setHeader2方法，setHeader3方法分别设置值。**所以这种策略要求，列名与bean中的属性名完全一致，如果不一致，则值为空，不会出错。使用注解时，注解名字必须与csv中列名一致。
        //默认跳过表头，无法使用SkipLines，使用会丢失字段映射
        MappingStrategy<T> strategy2 = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withSeparator(',')
                .withQuoteChar('\"')
                .build();

        return csvToBean.parse();

    }


    public static void main(String[] args) throws IOException {
        test1();
//        test2();

    }

    private static void test1 (){
        File file = new File("/Users/fangyechao/Downloads/njxhsd20200602的副本.csv");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile =new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        List<Test> testList = CsvUtil.getCsvData(file,Test.class);
        for(Test t:testList) {
            System.out.println(t);
        }
    }


    /**
     * Test
     *
     * @author bxgms
     * @email fyc8729@163.com
     * @date 2020/8/27
     */
    public static class Test implements Serializable {

        @CsvDate(value = "yyyy/MM/dd")
        @CsvBindByName(column = "清算日期")
        @CsvBindByPosition(position=0)
        private Date rq;

        @CsvDate(value = "HH:mm:ss")
        @CsvBindByName(column = "交易时间")
        @CsvBindByPosition(position=1)
        private Date sj;


        @CsvCustomBindByName(column = "交易类型",converter = FieldConverter.class)
        @CsvCustomBindByPosition(position=3,converter = FieldConverter.class)
        private String type;

        @CsvCustomBindByName(column = "支付方式",converter = FieldConverter.class)
        @CsvCustomBindByPosition(position=4,converter = FieldConverter.class)
        private String payType;

        @CsvBindByName(column = "实际支付金额")
        @CsvBindByPosition(position=5)
        private double my;

        @CsvBindByName(column = "支付宝微信优惠金额")
        @CsvBindByPosition(position=6)
        private double je;

        @CsvBindByName(column = "银行订单号")
        @CsvBindByPosition(position=7)
        private String dh;

        public Date getRq() {
            return rq;
        }

//        @Transient
//        public void setRq(String rq) {
//            Date d = DateUtil.fmtString2Date(rq,"yyyy/MM/dd");
//            setRq(d);
//        }

        public void setRq(Date rq) {
            this.rq = rq;
        }



        public Date getSj() {
            return sj;
        }

        public void setSj(Date sj) {
            String d = DateUtils.fmtDate(this.rq,"yyyy-MM-dd") + " " + DateUtils.fmtDate(sj,"HH:mm:ss");
            this.sj = DateUtils.parseDate(d);
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getMy() {
            return my;
        }

        public void setMy(double my) {
            this.my = my;
        }

        public double getJe() {
            return je;
        }

        public void setJe(double je) {
            this.je = je;
        }

        public String getDh() {
            return dh;
        }

        public void setDh(String dh) {
            this.dh = StringUtils.trim(dh);
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "rq=" + rq +
                    ", sj=" + sj +
                    ", type='" + type + '\'' +
                    ", payType='" + payType + '\'' +
                    ", my=" + my +
                    ", je=" + je +
                    ", dh='" + dh + '\'' +
                    '}';
        }
    }

    public static class FieldConverter extends AbstractBeanField<Test,String> {

        @Override
        protected Object convert(String value) throws CsvDataTypeMismatchException,
                CsvConstraintViolationException {
            Field f = getField();
            return EmuReceipt.getKeyByVal(f.getName(),StringUtils.trimToNull(value));
        }
    }


    /**
     * Recpeit
     *
     * @author bxgms
     * @email fyc8729@163.com
     * @date 2020/8/28
     */
    public enum EmuReceipt {

        /**
         *
         */
        type_pt("type","1","消费"),
        payType_yl("payType","2","银联");

        // 普通方法
        public static String getValByKey(String type,String key) {
            for (EmuReceipt c : EmuReceipt.values()) {
                if (c.getKey().equals(key) && c.getType().equals(type)) {
                    return c.value;
                }
            }
            return null;
        }

        // 普通方法
        public static String getKeyByVal(String type,String value) {
            for (EmuReceipt c : EmuReceipt.values()) {
                if (c.getValue().equals(value) && c.getType().equals(type)) {
                    return c.key;
                }
            }
            return null;
        }

        // 成员变量
        private String type;
        private String key;
        private String value;

        private EmuReceipt(String type,String key, String value) {
            this.key = key;
            this.value = value;
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }






}
