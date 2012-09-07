
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sean
 */
public class Name {

    private String Name;
    private int Id;

    public Name(String name, int id) {
        Name = name;
        Id = id;
    }
    
    public String getName(){
        return Name;
    }
    
    public int getId(){
        return Id;
    }
}
