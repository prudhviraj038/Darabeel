package in.yellowsoft.darabeel;

/**
 * Created by Chinni on 11-05-2016.
 */
public class Area {
    private String id;
    private String area;
    private String area_ar;
    private Boolean isHeading;

    public Area(String id, String area, String area_ar, Boolean isHeading) {
        this.id = id;
        this.area = area;
        this.area_ar = area_ar;
        this.isHeading = isHeading;
    }

    public String getId() {
        return id;
    }

    public String getArea() {
        return area;
    }
    public String getArea_ar() {
        return area_ar;
    }

    public Boolean isHeading() {
        return isHeading;
    }
}

