package shixia.stickyheaderrecyclerview.bean;

/**
 * Created by ShiXiuwen on 2017/4/6.
 *
 * Description:车型
 */

public class CarBrandInfo {

    /**
     * id : 3
     * brand_index : A
     * brand_name : 奥迪
     * brand_id : 33
     * brand_logo : .\Logo\33.jpg
     */

    private String id;
    private String brand_index;
    private String brand_name;
    private String brand_id;
    private String brand_logo;

    public boolean isFirstInGroup;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand_index() {
        return brand_index;
    }

    public void setBrand_index(String brand_index) {
        this.brand_index = brand_index;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }
}
