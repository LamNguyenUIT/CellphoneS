package nguyenngoclam.cellphones;

/**
 * Created by GIO on 6/3/2015.
 */
public class ListItem {

    private String price;
    private String url;
    private String name;
    private String ava;
    private String links;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public void setLinks(String links) {
        this.links = links;
    }
    public String getUrl() {
        return url;
    }
    public  String getName()
    {
        return name;
    }
    public  String getPrice(){
        return price;
    }

    public String getAva() {
        return ava;
    }

    public String getLinks() {
        return links;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public  void setName(String name){this.name = name;}
    public  void setPrice(String price){this.price = price;}

}