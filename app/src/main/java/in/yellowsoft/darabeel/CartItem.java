package in.yellowsoft.darabeel;

import java.io.Serializable;

/**
 * Created by Chinni on 12-05-2016.
 */
public class CartItem implements Serializable {
    Products products;
    String quantity,spl_request;
    CartItem(String quantity, Products products, String spl_request){
        this.quantity=quantity;
        this.products=products;
        this.spl_request=spl_request;

    }

}
