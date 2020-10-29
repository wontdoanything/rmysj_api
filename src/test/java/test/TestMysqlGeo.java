package test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.io.GeohashUtils;
import org.locationtech.spatial4j.shape.Rectangle;
import org.rmysj.api.Application;
import org.rmysj.api.commons.util.FastJSONUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * TestMysqlGeo
 *
 * @author bxgms
 * @email fyc8729@163.com
 * @date 2020/10/27
 * geo_code长度和距离的对照表：
 *
 *
 * geohash length  width  height
 * 1  5,009.4km   4,992.6km
 * 2  1,252.3km   624.1km
 * 3  156.5km   156km
 * 4  39.1km   19.5km
 * 5  4.9km   4.9km
 * 6  1.2km   609.4m
 * 7  152.9m   152.4m
 * 8  38.2m   19m
 * 9  4.8m   4.8m
 * 10  1.2m   59.5cm
 * 11  14.9cm   14.9cm
 * 12  3.7cm   1.9cm
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestMysqlGeo {

    @Test
    public void test(){
        // 方法1 用经纬度 between and 的方法 范围过滤 此方法适合拓展，效率不如方法2高，
        // 适合先使用方法2初步过滤一部分，然后再用方法1精确过滤
        double lon = 116.312528, lat = 39.983733;// 移动设备经纬度
        int radius = 1;// 千米

        SpatialContext geo = SpatialContext.GEO;
        Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(
                geo.makePoint(lon, lat), radius * DistanceUtils.KM_TO_DEG, geo, null);
        System.out.println(rectangle.getMinX() + "-" + rectangle.getMaxX());// 经度范围
        System.out.println(rectangle.getMinY() + "-" + rectangle.getMaxY());// 纬度范围
    }

    @Test
    public void test2(){
        //方法2 使用geo_hash 范围过滤 此方法效率更高，占用数据库空间也更大
        String geo_code = GeohashUtils.encodeLatLon(34.2588125935, 108.9498710632);
        System.out.println(geo_code);
    }

    @Test
    public void test3(){
        //使用java精确过滤
        // 移动设备经纬度
        double lon1 = 116.3125333347639, lat1 = 39.98355521792821;
        // 商户2经纬度
        double lon2 = 116.312528, lat2 = 39.983733;
        // 商户3经纬度
        double lon3 = 116.312535, lat3 = 39.983746;

        Gis orgGis = new Gis(lon1,lat1,null);
        List<Gis> gisList = Lists.newArrayList(new Gis(lon3,lat3,orgGis),new Gis(lon2,lat2,orgGis));
        System.out.println(FastJSONUtils.listarr2JSONArr(gisList));
        //排序
        Collections.sort(gisList);
        System.out.println(FastJSONUtils.listarr2JSONArr(gisList));
        //分页
        PageInfo<Gis> page = initPage(gisList,3,1);
        System.out.println(FastJSONUtils.obj2JSONObj(page));
    }

    public PageInfo<Gis> initPage(List<Gis> gisList,int pageNum, int pageSize){
        //创建Page类
        Page page = new Page(pageNum, pageSize);
        //为Page类中的total属性赋值
        int total = gisList.size();
        int totalPage = 0;
        if (total > 0) {
            totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        }
        page.setTotal(total);
        if (pageNum > totalPage || pageNum == 0) {
            return new PageInfo<Gis>(Lists.newArrayList());
        }
        //计算当前需要显示的数据下标起始值
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize,total);
        //从链表中截取需要显示的子链表，并加入到Page
        page.addAll(gisList.subList(startIndex,endIndex));
        //以Page创建PageInfo
        PageInfo<Gis> pageInfo = new PageInfo<Gis>(page);
        //将数据传回前端
        return pageInfo;
    }




    public static void main(String[] args) {
        TestMysqlGeo t = new TestMysqlGeo();
//        t.test();
//        t.test2();
        t.test3();
    }

    public static class Gis implements Serializable, Comparable<Gis> {

        private double lng;

        private double lat;

        private String geoCode;

        private Gis orgGIs;

//        private double distance;

        public Gis(){

        }

        public Gis(double lng,double lat,Gis orgGIs) {
            this.orgGIs = orgGIs;
            this.lng = lng;
            this.lat = lat;
        }

        public Gis getOrgGIs() {
            return orgGIs;
        }

        public void setOrgGIs(Gis orgGIs) {
            this.orgGIs = orgGIs;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getGeoCode() {
            return geoCode;
        }

        public void setGeoCode(String geoCode) {
            this.geoCode = geoCode;
        }

        public double getDistance(){
            if(orgGIs == null) {
                return 0D;
            }
            SpatialContext geo = SpatialContext.GEO;
            double thisDistance = geo.calcDistance(geo.makePoint(this.lng, this.lat), geo.makePoint(orgGIs.getLng(), orgGIs.getLat())) * DistanceUtils.DEG_TO_KM;
            return thisDistance;
        }

        @Override
        public int compareTo(Gis o) {
            return this.getDistance() - o.getDistance() > 0 ? 1:(this.getDistance() - o.getDistance() < 0?-1:0);
        }
    }
}
