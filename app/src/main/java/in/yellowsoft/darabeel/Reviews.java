package in.yellowsoft.darabeel;

import java.io.Serializable;

/**
 * Created by Chinni on 12-05-2016.
 */
public class Reviews implements Serializable {
    String m_id,m_name,m_rating,descrption;
    Reviews(String m_id, String m_name, String m_rating,String descrption){
        this.m_id=m_id;
        this.m_name=m_name;
        this.m_rating=m_rating;
        this.descrption=descrption;

    }

}
