package org.arkhamnetwork.example.objects;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.redmancometh.redcore.Defaultable;

/**
 *
 * @author Redmancometh
 *
 *  @entity Specifies that this object can be persisted into a database.
 *  ALL ENTITIES must have mappings in hibernate.cfg.xml. 
 *  It looks like this:
 *  <mapping class="org.arkhamnetwork.example.objects.ExampleObject"></mapping> 
 *  Just put the full path to the object, and Hibernate/RedCore will handle the rest
 */
@Entity(name = "rm_example")
public class ExampleObject implements Defaultable<UUID> // The generic passed here corresponds to the key the object is stored by!
{

    /**
     * @Id will designate this item as the primary key
     * You always need to specify @Column(unique = true) at a minimum
     * For the UUID type it will map it to the wrong type of object if (type = "uuid-char") 
     * is not specified
     * 
     */
    @Column(name = "uuid", unique = true)
    @Id
    @Type(type = "uuid-char")
    private UUID exampleOwner;

    /**
     * You don't need to set a column name, but it's a good idea.
     * 
     */
    @Column(name = "example_value", unique = true)
    private int exampleValue;

    /**
     *  This will be mapped into a child table (rm_example_example_list) using 
     *  the foreign key constraint from the parent table (rm_example)
     *  It looks super fucked, but it's properly unique. MUL keys have
     *  weird behavior.
     */
    @Column(unique = true)
    @ElementCollection(targetClass = String.class)
    private List<String> exampleList;

    /**
     * You need getters and setters for all this shit. 
     * Just do right click->source->generate getters/setters and check everything
     */

    @Override
    public void setDefaults(UUID uuid)
    {
        this.exampleValue = 0;
    }

    public int getExampleValue()
    {
        return exampleValue;
    }

    public void setExampleValue(int exampleValue)
    {
        this.exampleValue = exampleValue;
    }

}
